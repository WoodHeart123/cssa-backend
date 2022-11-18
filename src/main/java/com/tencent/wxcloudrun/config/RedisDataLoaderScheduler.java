package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
public class RedisDataLoaderScheduler {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    JedisPooled jedisPooled;

    @Scheduled(cron="0 */5 * ? * *")
    public void loadCourseDate(){
        try {
            Schema sc = new Schema().addNumericField("courseID").addTextField("courseName", 1.0);
            IndexDefinition def = new IndexDefinition().setPrefixes("course:");
            jedisPooled.ftCreate("course-index", IndexOptions.defaultOptions().setDefinition(def), sc);
        }catch(Exception ignored){}
        ArrayList<Course> courseArrayList = (ArrayList<Course>) courseMapper.getAllCourseList(0, 10000, "courseNum", "ASC", 0);
        for (Course course : courseArrayList) {
            Map<String, Object> fields = new HashMap<>();
            fields.put("courseID", course.getCourseID());
            if(course.getDepartmentAbrev().equals("COMP SCI")){
                fields.put("courseName", "CS CS" + course.getCourseNum().toString());
            }else {
                fields.put("courseName",course.getDepartmentAbrev().replace(" ", "") + " " + course.getDepartmentAbrev().replace(" ", "")  + course.getCourseNum().toString());
            }
            jedisPooled.hset("course:" + course.getCourseID().toString(), RediSearchUtil.toStringMap(fields));
        }
    }

}
