package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.OperationLog;

@Mapper
public interface LogMapper {

    /**
     * 日志记录
     *
     * @param optLog 日志信息
     */
    void createLog(OperationLog optLog);
}
