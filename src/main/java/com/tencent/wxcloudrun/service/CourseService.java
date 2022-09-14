package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    Response get_zan(String openid, Integer commentID, short zan);

    Response get_post(Integer commentID, String report);
}
