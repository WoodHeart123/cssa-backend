package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Jwtutil;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping({ "/activity" })
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = { "/createActivity" }, method = { RequestMethod.POST })
    public Response createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    @RequestMapping(value = { "/detail" }, method = { RequestMethod.GET })
    public Response Detail(HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        return Response.builder().data(openid).message("success").build();
    }

    @RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
    public Response register(@RequestParam("actID") String actID, @RequestParam("notes") String[] notes) { // TODO:
                                                                                                           // Should we
                                                                                                           // use
                                                                                                           // @RequestBody
                                                                                                           // or
                                                                                                           // @RequestParam?
        return activityService.regsiterActivity(actID, notes);
    }

}
