package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.application.serve.VisitLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: lvzy
 * @Date: 2021-11-14 10:42:25
 * @Desc: 访客通行记录数据层
 */
@Mapper
public interface VisitLogMapper {

    /**
     * 增加访客通信记录
     *
     * @param visitLog
     * @return
     */
    Integer insertVisitLog(@Param("params")VisitLog visitLog, @Param("tableName") String tableMame);

}
