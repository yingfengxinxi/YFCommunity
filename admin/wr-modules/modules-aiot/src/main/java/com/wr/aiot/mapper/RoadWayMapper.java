package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Roadway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-26 17:42:50
 * @Desc: 停车场出入口车道数据层
 */
public interface RoadWayMapper {
    /**
     * 同步车道数据
     * @param dataList 车道数据
     * @return java.lang.Integer
     */
    Integer insertOrUpdate(@Param("dataList") JSONArray dataList);

    /**
     * 根据出入口唯一标识查询车道
     * @param entranceIndexCode 出入口唯一标识
     * @return java.util.List<com.wr.remote.aiot.Roadway>
     */
    List<Roadway> selectRoadwayByEntrance(String entranceIndexCode);
}
