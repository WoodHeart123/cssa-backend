package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.PalFind;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.PalFindService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping({"/palFind"})
@Api(tags = "找搭子")
public class PalFindController {

  @Autowired
  PalFindService palFindService;


  @RequestMapping(value = {"/getPalList"},method = {RequestMethod.GET}, produces = "application/json")
  @Operation(summary = "获取帖子列表",description = "")
  public Response<List<PalFind>> getPalList(@Parameter @RequestParam Integer offset,
                                            @Parameter @RequestParam Integer limit,
                                            @Parameter @RequestParam String label,
                                            @Parameter @RequestParam Timestamp startTime,
                                            @Parameter @RequestParam Timestamp endTime){
  if(offset<=20) offset=0;
  return palFindService.getPalList(offset,limit);
  }
  
  @RequestMapping(value = {"/getPalListByLabel"},method = {RequestMethod.GET}, produces = "application/json")
  @Operation(summary = "获取帖子列表",description = "")
  public Response<List<PalFind>> getPalList(@Parameter @RequestParam Integer offset,
                                            @Parameter @RequestParam Integer limit,
                                            @Parameter @RequestParam String label){
    if(offset<=20) offset=0;
    return palFindService.getPalListByLabel(offset,limit,label);
  }
  
  @RequestMapping(value = {"/getPalListByTime"},method = {RequestMethod.GET}, produces = "application/json")
  @Operation(summary = "获取帖子列表",description = "")
  public Response<List<PalFind>> getPalList(@Parameter @RequestParam Integer offset,
                                            @Parameter @RequestParam Integer limit,
                                            @Parameter @RequestParam Timestamp startTime,
                                            @Parameter @RequestParam Timestamp endTime){
    if(offset<=20) offset=0;
    return palFindService.getPalListByTime(offset,limit,startTime,endTime);
  }

  @RequestMapping(value = {"/postPalFindInfo"},method = {RequestMethod.POST})
  @Operation(summary = "",description = "")
  public Response<Object> postPalFindInfo(@RequestBody PalFind palFind,
                                          @RequestParam Boolean save,
                                          @RequestHeader("x-wx-openid") String openid){
    palFind.setUserID(openid);
    return palFindService.postPalFindInfo(palFind,save);
  }

  @RequestMapping(value = {"/updatePalFindInfo"}, method = {RequestMethod.POST})
  @Operation(summary = "更新", description = "更新")
  public Response<Object> updatePalFindInfo(@Parameter(description = "信息") @RequestBody PalFind palFind,
                                       @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
    return palFindService.updatePalFindInfo(openid,palFind);
  }
  
  


}
