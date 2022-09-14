package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CourseMapper{

    @Update("INSERT INTO 'comment' ('courseID','professor','courseTime','difficulty','perfer','comment') VALUES (#{courseID},#{professor},#{courseTime},#{difficulty},#{prefer},#{comment})")
    static void save(Comment comment) {
        ;
    }

}
