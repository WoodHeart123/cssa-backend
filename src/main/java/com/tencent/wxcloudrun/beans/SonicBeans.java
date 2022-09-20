package com.tencent.wxcloudrun.beans;


import com.github.twohou.sonic.IngestChannel;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SonicBeans implements InitializingBean {

    private static IngestChannel channel;

    @Autowired
    CourseMapper courseMapper;

    static {
        try {
            channel = new IngestChannel("47.97.183.103",1491,"SecretPassword",2000,2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Course> courseList = courseMapper.getSearchCourseList();
        try {
            channel.flushc("course");
            for (Course course : courseList) {
                channel.push("course", "default", course.getCourseID().toString(), course.getDepartmentAbrev() + course.getCourseNum().toString() + " " + course.getCourseName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
