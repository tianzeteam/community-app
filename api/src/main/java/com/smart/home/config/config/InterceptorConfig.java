package com.smart.home.config.config;

import com.smart.home.config.interceptor.TraceLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {

    @Bean
    public TraceLogInterceptor getTraceLogInterceptor() {
        return new TraceLogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求跟踪
        registry.addInterceptor(getTraceLogInterceptor()).addPathPatterns("/api/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
