package com.smart.home.config.config;

import com.smart.home.config.filter.AccessTokenFilter;
import com.smart.home.config.xss.Xss;
import com.smart.home.config.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

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

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean(Xss propertiesXxs) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(new XssFilter());
        registration.setOrder(1);
        registration.setEnabled(propertiesXxs.isEnabled());
        registration.addUrlPatterns(propertiesXxs.getUrlPatterns().split(","));
        Map<String, String> initParameters = new HashMap<>(16);
        initParameters.put("excludes", propertiesXxs.getExcludes());
        registration.setInitParameters(initParameters);
        return registration;
    }

}
