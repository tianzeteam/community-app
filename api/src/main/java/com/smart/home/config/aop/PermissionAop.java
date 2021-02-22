package com.smart.home.config.aop;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.UserContext;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.common.exception.AuthorizationException;
import com.smart.home.modules.user.entity.UserAccount;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Log4j2
@Aspect
@Order(3)
@Component
public class PermissionAop {

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.smart.home.controller..*(..))")
    public void invokeMethod() {

    }

    @Around("invokeMethod()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        UserAccount user = UserContext.getCurrentUser();
        if (null != user && user.getRoleCodeList().contains(RoleConsts.SUPER_ADMIN)) {
            return proceedingJoinPoint.proceed();
        }
        RoleAccess roleAccess = method.getAnnotation(RoleAccess.class);
        if (!Objects.isNull(roleAccess)) {
            if (Objects.isNull(user)) {
                // 没有登陆信息，报错
                throw new AuthorizationException("Permission Denied");
            }
            String[] roles = roleAccess.value();
            if (Objects.isNull(roles) && roles.length == 0) {
                // 如果没有定义所需角色，说明只要登陆就能访问
                return proceedingJoinPoint.proceed();
            }
            List<String> roleCodeList = user.getRoleCodeList();
            if (CollectionUtils.isEmpty(roleCodeList)) {
                // 没有授权任何角色，则不能访问
                throw new AuthorizationException("Permission Denied");
            }
            List<String> needRoleList = Arrays.asList(roles);
            boolean validAccess = false;
            for (String roleCode : roleCodeList) {
                if (needRoleList.contains(roleCode)) {
                    validAccess = true;
                    break;
                }
            }
            if (validAccess) {
                return proceedingJoinPoint.proceed();
            }
        }
        AnonAccess anon = method.getAnnotation(AnonAccess.class);
        if (Objects.isNull(anon)) {
            // 说明必须要登陆才能访问的, 检查有没有登陆信息
            if (Objects.isNull(user)) {
                // 没有登陆信息，报错
                throw new AuthorizationException("Permission Denied");
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
