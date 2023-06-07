package com.tencent.wxcloudrun.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AdminServiceInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AdminServiceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        PrintWriter writer = null;
        /*
        try{
            System.out.println(request.getHeader("Authorization"));

            if(request.getHeader("Authorization") == null) {
                logger.info("filter admin request to " + request.getRequestURI() + "because of no token");
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset:utf-8");
                writer = response.getWriter();
                writer.print(JSON.toJSON(new Response(ReturnCode.INVALID_ADMIN_TOKEN)));
                return false;
            }
        }catch (IOException ignored){
            logger.warn("fail to write to response");
        }finally {
            if(writer != null){
                writer.close();
            }
        }
         */
        return true;
    }

}
