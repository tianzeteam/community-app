package com.smart.home.config.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Log4j2
@Aspect
@Order(2)
@Component
public class ValidatorAop {

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.smart.home.controller..*(..))")
    public void validateMethod() {

    }

    @Around("validateMethod()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取所有的请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        if (null != args && args.length > 0) {
            for (Object obj : args) {
                if (obj instanceof BindingResult) {
                    // 参数验证
                    BindingResult bindingResult = (BindingResult) obj;
                    if (bindingResult.hasErrors()) {
                        StringBuilder errorMessage = new StringBuilder();
                        List<ObjectError> list = bindingResult.getAllErrors();
                        for (ObjectError objectError : list) {
                            errorMessage.append(objectError.getDefaultMessage()).append(";");
                        }
                        throw new RuntimeException(errorMessage.toString());
                    }
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
