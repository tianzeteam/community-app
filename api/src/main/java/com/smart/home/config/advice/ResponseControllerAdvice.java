package com.smart.home.config.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.home.dto.APIResponse;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.exception.RestfulRequestException;
import com.smart.home.config.interceptor.TraceLogInterceptor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author jason
 * @date 2021/2/21
 * 有了这个类的加持，Controller方法中，不一定要全部返回APIResponse类型了
 * 这里会判断返回的是否是APIResponse类型，如果不是的话会自动封装成一个APIResponse类型的数据
 * 但是需要注意的只针对配置的basePackages有效，这里根据自己情况配置
 **/
@RestControllerAdvice(basePackages = {"com.smart.home.controller.admin", "com.smart.home.controller.app"
        , "com.smart.home.controller.common"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果接口返回的类型本身就是APIResponse那就没有必要进行额外的操作，返回false
        boolean needExecuteBeforeBodyWrite = true;
        if (returnType.getParameterType().equals(APIResponse.class)) {
            needExecuteBeforeBodyWrite = false;
        }
        return needExecuteBeforeBodyWrite;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在APIResponse里后，再转换为json字符串响应给前端
                String traceId = ThreadContext.get(TraceLogInterceptor.TRACE_ID);
                return objectMapper.writeValueAsString(APIResponse.OK(body).withTraceId(traceId));
            } catch (JsonProcessingException e) {
                throw new RestfulRequestException(APIResponseCodeEnum.FAILED.getCode(), "返回String类型错误");
            }
        }
        return APIResponse.OK(body);
    }
}