package com.tencent.wxcloudrun.config;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.model.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeadAuthConfig {
    public class HeadAuthFilter implements Filter {
        @Autowired
        Jwtutil jwtutil;
        @Value("${jwt.header}")
        private String tokenHeader;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse response=(HttpServletResponse)servletResponse;
            String uri=httpServletRequest.getRequestURI();
            if (uri.contains("/login") || uri.contains("/collect")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String token = httpServletRequest.getHeader(tokenHeader);
                if (StringUtils.isEmpty(token)) {
                    setResponse(response);
                } else {
                    if(jwtutil.isTokenValid(token))
                        filterChain.doFilter(servletRequest, servletResponse);
                    else {
                        setResponse(response);
                    }
                }
            }
        }

        @Override
        public void destroy() {

        }
    }
    @Bean
    public Filter headAuthFilter(){
        return new HeadAuthFilter();
    }

    @Bean
    public FilterRegistrationBean MyFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("headAuthFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("HeadAuthFilter");
        registration.setOrder(1);
        return registration;
    }

    void setResponse(HttpServletResponse response){
        response.setStatus(200);
        ServletOutputStream out ;
        JSONObject responseData=new JSONObject();
        responseData.put("status","503");
        responseData.put("message","无用户信息");
        try {
            response.setContentType("application/json; charset=utf-8");
            out = response.getOutputStream();
            out.write(responseData.toJSONString().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
