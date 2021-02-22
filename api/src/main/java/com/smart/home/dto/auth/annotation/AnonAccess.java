package com.smart.home.dto.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author jason
 * @date 2021/2/17
 * 可以匿名访问
 **/
@Retention(RUNTIME)
@Target(METHOD)
public @interface AnonAccess {
}
