package org.cssa.wxcloudrun.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.cssa.wxcloudrun.util.InjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping({"/course"})
@Tag(name = "课程中心")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    WeChatAPI weChatAPI;


    @RequestMapping(value = {"/searchCourse"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "搜索课程", description = "根据输入搜索课程")
    @Timed("api.course.search")
    public Response<List<Course>> search(@RequestParam String value) {
        return courseService.searchCourse(value);
    }

    /**
     * Add a newly posted comment to database
     *
     * @param courseComment comment which is posted
     * @return Add comment content and related information to database
     */
    @PostMapping(value = "/postComment")
    @Operation(summary = "发表评论", description = "发表评论")
    public Response<Object> save(@Parameter(description = "课程评价") @RequestBody CourseComment courseComment,
                                 @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        if (courseComment.getComment().length() > 300) {
            return Response.builder().status(103).message("超过字数限制").build();
        }
        WechatResponse wechatResponse = weChatAPI.MsgCheck(courseComment.getComment(), user.getOpenID(), 3);
        if(wechatResponse != null && wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }
        courseComment.setUserID(user.getId());
        return courseService.postComment(courseComment);

    }

    @RequestMapping(value = {"/getCommentList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取评论列表", description = "获对应课程评论列表")
    public Response<List<CourseComment>> getCommentList(@Parameter(description = "课程ID") @RequestParam Integer courseID,
                                                        @Parameter(description = "从第几位开始") @RequestParam Integer offset,
                                                        @Parameter(description = "返回数量限制") @RequestParam Integer limit,
                                                        @Parameter(description = "排序顺序") @RequestParam SortType order) {
        return courseService.getCommentList(courseID, offset, limit, order);
    }

    @RequestMapping(value = {"/getCourseList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取课程列表", description = "获取课程列表")
    public Response<List<Course>> getCourseList(@Parameter(description = "院系ID") @RequestParam Optional<Integer> departmentID,
                                                @Parameter(description = "从第几位开始") @RequestParam Optional<Integer> offset,
                                                @Parameter(description = "返回数量限制") @RequestParam Optional<Integer> limit,
                                                @Parameter(description = "排序顺序") @RequestParam Optional<SortType> orderType,
                                                @Parameter(description = "是否为研究生课程") @RequestParam Optional<Boolean> isGrad) {
        if (departmentID.isEmpty()) {
            return new Response<>(ReturnCode.LACK_PARAM);
        }
        if (departmentID.get().equals(0) && (offset.isEmpty() || limit.isEmpty())) {
            return new Response<>(ReturnCode.LACK_PARAM);
        }
        return courseService.getCourseList(departmentID.get(), offset.get(), limit.get(), orderType.orElse(SortType.SORT_BY_COURSE_NUM), isGrad.orElse(false));
    }

    @RequestMapping(value = {"/getDepartmentList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取院系列表", description = "获取院系列表")
    public Response<List<Department>> getDepartmentList() {
        return courseService.getDepartmentList();
    }

    /**
     * This controller record which comment users give zan, and increase the number of zan in this comment by one
     * Request path is /zan, and method is GET
     *
     * @param commentID ID of comment
     * @return Response information and data
     */
    @RequestMapping(value = {"/like"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "点赞", description = "对评论点赞")
    public Response<Object> like(@Parameter(description = "评论ID") @RequestParam(name = "commentID") Integer commentID) {
        return courseService.like(commentID);
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST})
    @Operation(summary = "更新评论", description = "更新评论")
    public Response<Object> updateComment(@RequestBody CourseComment courseComment,
                                          @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        if (courseComment.getComment().length() > 300) {
            return new Response<>(ReturnCode.EXCEED_LENGTH_LIMIT);
        }
        return courseService.updateComment(user.getId(), courseComment.getCommentID(), courseComment.getComment());
    }

    @RequestMapping(value = {"/deleteComment"}, method = RequestMethod.DELETE)
    public Response<Object> deleteComment(@RequestParam Integer commentID,
                                          @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {

        return courseService.deleteComment(user.getId(), commentID);
    }

}
