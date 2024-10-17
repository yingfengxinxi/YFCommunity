package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Park;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 停车场数据层
 */
public interface ParkMapper {
    /**
     * 查询停车场数据
     * @param park 停车场信息
     * @return java.util.List<com.wr.remote.aiot.Park>
     */
    List<Park> selectParkList(Park park);

   /**
    * 同步停车场设备
    * @param dataList 停车场数据
    * @param manufacturer 厂商
    * @param renewalBy 数据库同步人
    * @param communityId 小区id
    * @return java.lang.Integer
    */
    Integer insertOrUpdate(@Param("dataList") JSONArray dataList, @Param("manufacturer") String manufacturer,
                               @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

    /**
     * 根据id查询数据
     * @param parkId 停车场id
     * @return com.wr.remote.aiot.Park
     */
    Park selectParkById(Long parkId);

    /**
     * 更新停车场
     * @param park 停车场信息
     * @return java.lang.Integer
     */
    Integer updatePark(Park park);

    /**
     * 查询未设置z支付二维码的id集
     * @return java.lang.Long[]
     */
    List<Long> selectIdsOfNotQrCode();

    /**
     * 批量设置停车场二维码
     * @param parkList 停车场信息
     * @return java.lang.Integer
     */
    Integer batchUpdatePayQr(List<Park> parkList);
}
