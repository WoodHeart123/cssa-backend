package org.cssa.wxcloudrun.util;

import jakarta.annotation.Resource;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    UserMapper userMapper;

    public UserArgumentResolver(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(InjectUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader("x-wx-openid");
        if (token != null) {
            System.out.println("token: " + token);
            return userMapper.getUserByOpenID(token);
        }
        return null;
    }

}