package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.util.AdminServiceInterceptor;
import com.tencent.wxcloudrun.util.WeixinServiceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new WeixinServiceInterceptor()).excludePathPatterns("/admin/*");
        registry.addInterceptor(new AdminServiceInterceptor()).addPathPatterns("/admin/*").excludePathPatterns("/admin/login").excludePathPatterns("/admin/register");
    }
}