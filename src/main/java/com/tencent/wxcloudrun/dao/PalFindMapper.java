package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.PalFind;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface PalFindMapper {

  ArrayList<PalFind> getPal(Integer offset, Integer limit);
  ArrayList<PalFind> getPalByLabel(Integer offset, Integer limit, String label);
  ArrayList<PalFind> getPalByTime(Integer offset, Integer limit,
                                  Timestamp startTime, Timestamp endTime);

  void postPalFindInfo(PalFind palFind);
  void saveContact(String userID, String contact);
  void updatePalFind(String userID, PalFind palFind);
  void deletePalFind(String userID,PalFind palFind);


}


