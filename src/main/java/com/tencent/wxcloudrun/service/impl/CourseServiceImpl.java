package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Info;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public Response get_zan(String openid, Integer commentID, short zan) {
        User user = courseMapper.get_user(openid);
        if(user.getLikedComment() == null){
            try{
                courseMapper.create_zan(openid,commentID, zan);
                return Response.builder().status(100).message("成功").build();
            }catch (Exception exception){
                return Response.builder().status(101).message(exception.getMessage()).build();
            }
        }
        try{
            courseMapper.get_zan(openid,commentID, zan);
            return Response.builder().status(100).message("成功").build();
        }catch (Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response get_post(Integer commentID, String report) {
        Comment comment = courseMapper.get_comment(commentID);
        if(comment.getReportList() == null){
            try{
                courseMapper.create_post(commentID, report);
                return Response.builder().status(100).message("成功").build();
            }catch (Exception exception){
                return Response.builder().status(101).message(exception.getMessage()).build();
            }
        }
        comment.setReportList(JSON.parseArray(comment.getReportListJSON(), String.class));
        try{
            courseMapper.get_post(commentID, report);
            if(comment.getReportList().size() >= 9){
                courseMapper.disable_comment(commentID);
            }
            return Response.builder().status(100).message("成功").build();
        }catch (Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }
}
