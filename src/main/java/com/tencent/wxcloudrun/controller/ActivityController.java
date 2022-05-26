package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Jwtutil;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping({"/activity"})
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    Jwtutil jwtutil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET})
    public Response Detail(HttpServletRequest request) {
        return Response.builder().data("success").message("success").build();
    }
}
