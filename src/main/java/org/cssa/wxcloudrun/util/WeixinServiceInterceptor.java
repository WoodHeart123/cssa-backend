package org.cssa.wxcloudrun.util;

import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;


@Component
public class WeixinServiceInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(WeixinServiceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        PrintWriter writer = null;
        try {

            if (request.getHeader("x-wx-openid") == null) {
                logger.info("filter user request to " + request.getRequestURI() + " because of no wx-openid");
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset:utf-8");
                writer = response.getWriter();
                writer.print(JSON.toJSON(new Response<>(ReturnCode.INVALID_USER_TOKEN)));
                return false;
            }

        } catch (IOException ignored) {
            logger.warn("fail to write to response");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return true;
    }

}
