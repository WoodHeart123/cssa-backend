package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CourseMapper{

    void save(Comment comment);

}
