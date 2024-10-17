package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Roadway;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 道闸数据层
 */
public interface BarrierMapper {
    /**
     * 查询道闸数据
     *
     * @param barrier 道闸信息
     * @return java.util.List<com.wr.remote.aiot.Barrier>
     */
    List<Barrier> selectBarrierList(Barrier barrier);


    /**
     * 同步道闸设备
     *
     * @param dataList     道闸数据
     * @param manufacturer 厂商
     * @param renewalBy    数据库同步人
     * @param communityId  小区id
     * @return java.lang.Integer
     */
    Integer insertOrUpdate(@Param("dataList") JSONArray dataList, @Param("manufacturer") String manufacturer,
                           @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

    /**
     * 根据id查询数据
     *
     * @param barrierId 道闸id
     * @return com.wr.remote.aiot.barrier
     */
    Barrier selectBarrierById(Long barrierId);

    /**
     * 根据ids查询道闸信息
     *
     * @param barrierIds 道闸ids
     * @return java.util.List<com.wr.remote.aiot.Barrier>
     * @author yx
     * @date 2022/11/17 16:59
     */
    List<Barrier> selectBarrierByIds(List<Long> barrierIds);

    /**
     * 更新道闸
     *
     * @param barrier 道闸信息
     * @return java.lang.Integer
     */
    Integer updateBarrier(Barrier barrier);

    /**
     * 查询未设置设备二维码的设备id集
     *
     * @return java.lang.Long[]
     */
    List<Barrier> selectIdsOfNotQrCode();

    /**
     * 批量设置道闸二维码
     *
     * @param barrierList 道闸信息
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(@Param("barrierList") List<Barrier> barrierList);

    /**
     * 查询所有未绑定的车道数据
     *
     * @param barrier 道闸
     * @return java.util.List<com.wr.remote.aiot.Entrance>
     */
    List<Roadway> selectUnbidRoadway(Barrier barrier);

    /**
     * 删除同步时间不是当天的数据
     * @return
     */
    int delRenewalTimeNotToday();

    @MapKey("indexCode")
    Map<String,Roadway> getIndexCode();

    /**
     * 根据道闸唯一标识更新数据
     * @param jsonObject
     * @param manufacturer
     * @param renewalBy
     * @param communityId
     * @return
     */
    int updateByIndexCode(@Param("jsonObject") JSONObject jsonObject, @Param("manufacturer") String manufacturer,
                          @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

    int insert(@Param("jsonObject") JSONObject jsonObject, @Param("manufacturer") String manufacturer,
               @Param("renewalBy") String renewalBy, @Param("communityId") Long communityId);

   List<Barrier> selectBarrierByRoadway(String roadwayIndexCode);
}
