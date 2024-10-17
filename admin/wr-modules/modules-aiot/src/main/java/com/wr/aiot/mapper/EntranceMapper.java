package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Entrance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-26 17:42:04
 * @Desc: 停车场出入口数据层
 */
public interface EntranceMapper {
    /**
     * 同步出入口数据
     * @param dataList 出入口数据
     * @return java.lang.Integer
     */
    Integer insertOrUpdate(@Param("dataList") JSONArray dataList);

    /**
     * 根据停车场唯一标识查询停车场出入口
     * @param parkIndexCode 停车场唯一标识
     * @return java.util.List<com.wr.remote.aiot.Entrance>
     */
    List<Entrance> selectEntranceByPark(String parkIndexCode);

}
