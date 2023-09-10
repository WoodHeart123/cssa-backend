package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    /**
     * 日志记录
     * @param optLog 日志信息
     */
    void createLog(OperationLog optLog);
}
