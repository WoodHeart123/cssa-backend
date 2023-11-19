package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.PalFind;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface PalFindService {

  Response<Object> postPalFindInfo(PalFind palInfo, Boolean save);
  Response<Object> updatePalFindInfo(String userID, PalFind palInfo);
  Response<Object> deletePalFindInfo(String userID, PalFind palInfo);
  Response<List<PalFind>> getPalList(Integer offset, Integer limit);
  Response<List<PalFind>> getPalListByLabel(Integer offset, Integer limit, String label);
  Response<List<PalFind>> getPalListByTime(Integer offset, Integer limit,Timestamp startTime, Timestamp endTime);
  

}
