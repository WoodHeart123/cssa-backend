package com.tencent.wxcloudrun.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CachingRequestBodyFilter> filterRegistration() {
        FilterRegistrationBean<CachingRequestBodyFilter> registration = new FilterRegistrationBean<>(new CachingRequestBodyFilter());
        registration.setOrder(2);
        return registration;
    }
}