package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.CourseFile;
import com.tencent.wxcloudrun.model.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/test"})
@Api(tags = "测试")
public class TestController {

    @RequestMapping(value = {"/postFile"}, method = {RequestMethod.POST})
    public Response<Object> postFile(@Parameter(description = "文件相关信息") @RequestBody @Valid CourseFile courseFile,
                                     @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return Response.builder().build();
    }
}
