package com.tencent.wxcloudrun;

import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages = {"com.tencent.wxcloudrun.dao"})
public class WxCloudRunApplication {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    JedisPooled jedisPooled;

    public static void main(String[] args) {
        SpringApplication.run(WxCloudRunApplication.class, args);
    }


    @Bean
    CommandLineRunner loadCourseData() {
        return args -> {
            try {
                jedisPooled.ftDropIndex("course-index");
            }catch(Exception ignored){} finally {
                Schema sc = new Schema().addNumericField("courseID").addTextField("courseName", 1.0);
                IndexDefinition def = new IndexDefinition().setPrefixes("course");
                jedisPooled.ftCreate("course-index", IndexOptions.defaultOptions().setDefinition(def), sc);
            }
            ArrayList<Course> courseArrayList = (ArrayList<Course>) courseMapper.getAllCourseList(0, 10000, "courseNum", "ASC");
            for (Course course : courseArrayList) {
                Map<String, Object> fields = new HashMap<>();
                fields.put("courseID", course.getCourseID());
                if(course.getDepartmentAbrev().equals("COMP SCI")){
                    fields.put("courseName", "CS CS" + course.getCourseNum().toString());
                }else {
                    fields.put("courseName", course.getDepartmentAbrev().replace(" ", "") + " " + course.getDepartmentAbrev().replace(" ", "")  + course.getCourseNum().toString());
                }
                jedisPooled.hset("course:" + course.getCourseID().toString(), RediSearchUtil.toStringMap(fields));
            }
        };
    }
}
