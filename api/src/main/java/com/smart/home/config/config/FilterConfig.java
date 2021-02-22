package com.smart.home.config.config;

import com.smart.home.config.filter.AccessTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean accessTokenFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new AccessTokenFilter());
        filterBean.setName("accessTokenFilter");
        filterBean.addUrlPatterns("/api/*");
        return filterBean;
    }

}
