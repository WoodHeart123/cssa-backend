package com.tencent.wxcloudrun.util;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.event.LogEvent;
import com.tencent.wxcloudrun.model.Jwtutil;
import com.tencent.wxcloudrun.model.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;


@Aspect
@Component
public class RequestLogAspect {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Jwtutil jwtutil;
    private final Logger logger = LoggerFactory.getLogger(WeixinServiceInterceptor.class);
    private final ThreadLocal<OperationLog> logThreadLocal = new ThreadLocal<>();
    //拦截web下所有方法
    @Pointcut("execution(* com.tencent.wxcloudrun.controller.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws IOException {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 获取请求参数
        HttpServletRequest request = attributes.getRequest();

        //get方法不记录日志
        if ("GET".equals(request.getMethod())) {
            return;
        }

        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURI();
        String userID;
        //获取ID
        if (uri.contains("admin")){
            userID = jwtutil.getUidFromToken(request.getHeader("Authorization"));
        }else{
            userID = request.getHeader("x-wx-openid");
        }
        String requestBody = request.getReader().lines().collect(Collectors.joining());

        //请求参数
        Object[] paramsArray = joinPoint.getArgs();
        logger.info(
                "请求日志拦截：userId={}, uri={}, method={}, request param={}, request body={}",
                userID,
                uri,
                request.getMethod(),
                paramsArray,
                requestBody);

        OperationLog optLog = OperationLog.builder().userID(userID).resource(uri).
                requestMethod(request.getMethod()).beanName(beanName).methodName(methodName).
                requestParams(argsArrayToString(paramsArray))
                .requestData(requestBody).build();
        logThreadLocal.set(optLog);
    }

    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，记录日志
            OperationLog optLog = logThreadLocal.get();
            if (optLog != null) {
                optLog.setResponseData(JSON.toJSONString(result));
                applicationContext.publishEvent(new LogEvent(this, optLog));
            }
        } catch (Exception e) {
            logger.error("无法写入请求 {} {}", e, JSON.toJSONString(logThreadLocal.get()));
        } finally {
            logThreadLocal.remove();
        }
    }


    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                Object jsonObj = JSON.toJSON(o);
                params.append(jsonObj.toString()).append(" ");
            }
        }
        return params.toString().trim();
    }
}