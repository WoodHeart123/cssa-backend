package com.tencent.wxcloudrun.beans;


import com.alibaba.fastjson.JSON;
import com.github.twohou.sonic.IngestChannel;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

public class SonicBeans {

//    private static IngestChannel channel;
//
//    @Autowired
//    CourseMapper courseMapper;
//
//    static {
//        try {
//            channel = new IngestChannel("localhost",1491,"SecretPassword",10000,10000);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        List<Course> courseList = courseMapper.getSearchCourseList();
//        try {
//            channel.flushc("course");
//            for (Course course : courseList) {
//                if(course.getDepartmentAbrev().contains("COMP")){
//                    channel.push("course", "default", course.getCourseID().toString(), "CS" + course.getCourseNum().toString());
//                }else{
//                    channel.push("course", "default", course.getCourseID().toString(), course.getDepartmentAbrev().replaceAll("\\s", "") + course.getCourseNum().toString());
//                }
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
