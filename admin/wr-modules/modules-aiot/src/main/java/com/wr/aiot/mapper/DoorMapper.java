package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Door;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 门禁点数据层
 */
public interface DoorMapper {
    /**
     * 查询门禁点数据
     *
     * @param door 门禁点信息
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectDoorList(Door door);

    /**
     * 修改门禁点状态
     *
     * @param doorList 门禁点数据
     * @return java.lang.Integer
     */
    Integer batchUpdateOnline(@Param("doorList") List<Door> doorList);

    void insert(Door door);

    /**
     * @param door
     */
    void update(Door door);

    /**
     *
     * @param indexCode
     * @return
     */
    Door getByIndexCodeInfo(@Param("indexCode") String indexCode);

    /**
     *
     */
    void delete();

    /**
     * 同步门禁点设备
     *
     * @param dataList     门禁点数据
     * @param manufacturer 厂商
     * @param renewalBy    数据库同步人
     * @param communityId  小区id
     * @return java.lang.Integer
     */
    Integer insertOrUpdateDoor(@Param("dataList") JSONArray dataList, @Param("manufacturer") String manufacturer,
                               @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

    /**
     * 根据id查询数据
     *
     * @param doorId 门禁点id
     * @return com.wr.remote.aiot.Door
     */
    Door selectDoorById(Long doorId);

    /**
     * 更新门禁点
     *
     * @param door 门禁点信息
     * @return java.lang.Integer
     */
    Integer updateDoor(Door door);

}
