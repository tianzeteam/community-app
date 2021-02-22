package com.smart.home.config.advice;

import com.smart.home.dto.APIResponse;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.util.ExceptionUtil;
import com.smart.home.common.exception.AuthorizationException;
import com.smart.home.common.exception.RestfulRequestException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.config.interceptor.TraceLogInterceptor;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jason
 */
@RestControllerAdvice(basePackages = {"com.smart.home.controller.admin", "com.smart.home.controller.app"
        , "com.smart.home.controller.common"})
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public APIResponse jsonErrorHandler(HttpServletRequest req, Exception e) {
        String traceId = ThreadContext.get(TraceLogInterceptor.TRACE_ID);
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException)e).getBindingResult();
            String errorMsg = "";
            if (bindingResult != null && bindingResult.getFieldError() != null) {
                errorMsg = bindingResult.getFieldError().getDefaultMessage();
            }
            return APIResponse.ERROR(APIResponseCodeEnum.ERROR_INVALID_DATA.getCode(), errorMsg).withTraceId(traceId);
        }
        if (e instanceof MethodArgumentNotValidException) {
            // 如果Controller的接收对象上有@Valid注解
            ObjectError objectError = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0);
            return APIResponse.ERROR(APIResponseCodeEnum.ERROR_INVALID_DATA.getCode(), objectError.getDefaultMessage()).withTraceId(traceId);
        }
        if (e instanceof AuthorizationException) {
            return APIResponse.ERROR(APIResponseCodeEnum.NO_AUTH.getCode(), ExceptionUtil.getMessage(e)).withTraceId(traceId);
        }
        if (e instanceof RestfulRequestException) {
            return APIResponse.ERROR(((RestfulRequestException) e).getCode(), ExceptionUtil.getMessage(e)).withTraceId(traceId);
        }
        if (e instanceof ServiceException) {
            return APIResponse.ERROR(((ServiceException) e).getCode(), ExceptionUtil.getMessage(e)).withTraceId(traceId);
        }
        logger.error("exception:", e);
        return APIResponse.ERROR(APIResponseCodeEnum.SYSTEM_ERROR.getCode(), ExceptionUtil.getMessage(e)).withTraceId(traceId);
    }

    @ExceptionHandler(Throwable.class)
    public APIResponse globalExceptionHandler(HttpServletRequest request, Throwable e) {
        String traceId = ThreadContext.get(TraceLogInterceptor.TRACE_ID);
        logger.error("exception:", e);
        return APIResponse.ERROR(APIResponseCodeEnum.SYSTEM_ERROR.getCode(), ExceptionUtil.getMessage(e)).withTraceId(traceId);
    }
}
