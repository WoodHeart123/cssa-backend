package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.PalFindMapper;
import com.tencent.wxcloudrun.model.PalFind;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.PalFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PalFindServiceImpl implements PalFindService {

  @Autowired
  PalFindMapper palMapper;

  @Override
  public Response<Object> postPalFindInfo(PalFind palInfo, Boolean save) {
    palInfo.setImagesJSON(JSON.toJSONString(palInfo.getImages()));
    palInfo.setCreatedAt(new Timestamp(new Date().getTime()));
    palInfo.setUpdatedAt(palInfo.getCreatedAt());
    palMapper.postPalFindInfo(palInfo);
    if(save) palMapper.saveContact(palInfo.getUserID(), palInfo.getContent());
    return Response.builder().message("成功").status(100).build();
  }

  @Override
  public Response<Object> updatePalFindInfo(String userID, PalFind palInfo) {
    palInfo.setUpdatedAt(new Timestamp(new Date().getTime()));
    palMapper.updatePalFind(userID,palInfo);
    return new Response<>();
  }
  
  @Override
  public Response<Object> deletePalFindInfo(String userID, PalFind palInfo) {
    palInfo.setDeletedAt(new Timestamp((new Date().getTime())));
    palMapper.deletePalFind(userID,palInfo);
    return new Response<>();
  }
  
  @Override
  public Response<List<PalFind>> getPalList(Integer offset, Integer limit) {
    ArrayList<PalFind> palList;
    palList = palMapper.getPal(offset, limit);
    for(PalFind palFind:palList){
      palFind.setImages( (ArrayList<String>) JSON.parseArray(palFind.getImagesJSON(),String.class));
    }
    return new Response<>(palList);
  }
  
  @Override
  public Response<List<PalFind>> getPalListByLabel(Integer offset, Integer limit, String label) {
    ArrayList<PalFind> palList;
    palList = palMapper.getPalByLabel(offset, limit,label);
    for(PalFind palFind:palList){
      palFind.setImages( (ArrayList<String>) JSON.parseArray(palFind.getImagesJSON(),String.class));
    }
    return new Response<>(palList);
  }
  
  @Override
  public Response<List<PalFind>> getPalListByTime(Integer offset, Integer limit, Timestamp startTime, Timestamp endTime) {
    ArrayList<PalFind> palList;
    palList = palMapper.getPalByTime(offset, limit,startTime,endTime);
    for(PalFind palFind:palList){
      palFind.setImages( (ArrayList<String>) JSON.parseArray(palFind.getImagesJSON(),String.class));
    }
    return new Response<>(palList);
  }
  
}
