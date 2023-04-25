package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.CourseComment;
import com.tencent.wxcloudrun.model.SortType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.SearchResult;

import java.util.ArrayList;
import java.util.Optional;



@RestController
@CrossOrigin
@RequestMapping({"/course"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private JedisPooled jedisPooled;

    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value) {
        ArrayList<String> courseIDList = new ArrayList<>();
        try {
            SearchResult sr = jedisPooled.ftSearch("course-index", "%" + value.toUpperCase() + "%");
            for(Document document: sr.getDocuments()){
                courseIDList.add(document.getString("courseID"));
            }
            if(courseIDList.size() == 0){
                return Response.builder().message("没有匹配结果").status(124).build();
            }
            return courseService.getCourse(courseIDList);
        }catch(JedisConnectionException e){
            return courseService.searchCourse(value);
        }


    }
    /**
     * Add a newly posted comment to database
     * @param courseComment comment which is posted
     * @param request Header of this Request
     * @return Add comment content and related information to database
     */
    @PostMapping(value = "/postcomment")
    public Response save(@RequestBody CourseComment courseComment, @RequestHeader("x-wx-openid") String openid) {
        if(courseComment.getComment().length() > 300){
            return Response.builder().status(103).message("超过字数限制").build();
        }
        courseComment.setUserID(openid);
        return courseService.postComment(courseComment);

    }

    @RequestMapping(value={"/getCommentList"}, method = {RequestMethod.GET})
    public Response getCommentList(@RequestParam Integer courseID, @CookieValue@RequestParam Integer offset, @RequestParam Integer limit, @RequestParam SortType order){
        return courseService.getCommentList(courseID,offset,limit,order);
    }
    
    @RequestMapping(value={"/courselist"}, method = {RequestMethod.GET})
    public Response getCourseList(@RequestParam Optional<Integer> departmentID, @RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit, @RequestParam Optional<SortType> orderType, @RequestParam Optional<Boolean> isGrad) {
        if (departmentID.isEmpty()) {
            return Response.builder().message("部门ID为空").status(104).build();
        }
        if (departmentID.get().equals(0) && (offset.isEmpty() || limit.isEmpty())){
            return Response.builder().message("缺少参数").status(105).build();
        }
        return courseService.getCourseList(departmentID.get(),offset.get(),limit.get(),orderType.orElse(SortType.SORT_BY_COURSE_NUM), isGrad.orElse(false));
    }
    
    @RequestMapping(value={ "/departmentlist"}, method = {RequestMethod.GET})
    public Response getDepartmentList() {
        return courseService.getDepartmentList();
    }

    /**
     * This controller record which comment users give zan, and increase the number of zan in this comment by one
     * Request path is /zan, and method is GET
     * @param commentID ID of comment
     * @return Response information and data
     */
    @RequestMapping(value = {"/zan"}, method = {RequestMethod.GET})
    public Response Zan(@RequestParam(name="commentID") Integer commentID, @RequestHeader("x-wx-openid") String openid){
        return courseService.zan(openid, commentID);
    }

    /**
     * Report a bad comment, and record the number of reports of this comment
     * @param courseComment comment which is reported
     * @return Response information and data
     */
    @RequestMapping(value = {"/report"}, method = {RequestMethod.POST})
    public Response Post(@RequestBody CourseComment courseComment, @RequestHeader("x-wx-openid") String openid){
        return courseService.report(courseComment.getCommentID(), courseComment.getReportList().get(0));
    }
}
