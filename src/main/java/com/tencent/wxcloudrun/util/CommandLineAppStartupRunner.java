package com.tencent.wxcloudrun.util;

import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.dao.SecondHandMapper;
import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;
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
    SecondHandMapper secondHandMapper;

    @Autowired
    JedisPooled jedisPooled;


    @Override
    public void run(String...args) {
        try{
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

            try {
                Schema sc = new Schema().addNumericField("productID").addTextField("productName", 1.0);
                IndexDefinition def = new IndexDefinition().setPrefixes("product:");
                jedisPooled.ftCreate("product-index", IndexOptions.defaultOptions().setDefinition(def), sc);
            }catch(Exception ignored){}
            ArrayList<Product> productArrayList = secondHandMapper.getAllProductList(0, 5000);
            for (Product product : productArrayList) {
                Map<String, Object> fields = new HashMap<>();
                fields.put("productID", product.getProductID());
                fields.put("productName", product.getProductTitle());
                jedisPooled.hset("product:" + product.getProductID().toString(), RediSearchUtil.toStringMap(fields));
                jedisPooled.ftSugAdd("productName", product.getProductTitle(), 1.0);
            }
        }catch (JedisConnectionException ignored){}




    }
}