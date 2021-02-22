package com.smart.home.config.interceptor;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jason
 * @date 2021/2/16
 * 在调用前通过ThreadContext加入traceId，调用完成后移除
 **/
public class TraceLogInterceptor implements HandlerInterceptor {

    public final static String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        ThreadContext.put(TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ThreadContext.remove(TRACE_ID);
    }

}
