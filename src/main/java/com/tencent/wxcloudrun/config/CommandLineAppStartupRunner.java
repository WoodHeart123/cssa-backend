package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    JedisPooled jedisPooled;


    @Override
    public void run(String...args) {
        try {
            try {
                Schema sc = new Schema().addNumericField("courseID").addTextField("courseName", 1.0);
                IndexDefinition def = new IndexDefinition().setPrefixes("course:");
                jedisPooled.ftCreate("course-index", IndexOptions.defaultOptions().setDefinition(def), sc);
            } catch (Exception ignored) {
            }
            ArrayList<Course> courseArrayList = (ArrayList<Course>) courseMapper.getAllCourseList(0, 10000, "courseNum", "ASC");
            for (Course course : courseArrayList) {
                Map<String, Object> fields = new HashMap<>();
                fields.put("courseID", course.getCourseID());
                if (course.getDepartmentAbrev().equals("COMP SCI")) {
                    fields.put("courseName", "CS CS" + course.getCourseNum().toString());
                } else {
                    fields.put("courseName", course.getDepartmentAbrev().replace(" ", "") + " " + course.getDepartmentAbrev().replace(" ", "") + course.getCourseNum().toString());
                }
                jedisPooled.hset("course:" + course.getCourseID().toString(), RediSearchUtil.toStringMap(fields));
            }
        }catch (Exception ignored){}
    }
}