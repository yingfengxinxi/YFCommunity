package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Visual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 可视对讲数据层
 */
public interface VisualMapper {
    /**
     * 查询可视对讲数据
     *
     * @param visual 可视对讲信息
     * @return java.util.List<com.wr.remote.aiot.Visual>
     */
    List<Visual> selectVisualList(Visual visual);

    /**
     * 修改可视对讲点状态
     *
     * @param visualList 可视对讲数据
     * @return java.lang.Integer
     */
    Integer updateVisualOnline(@Param("visualList") List<Visual> visualList);

    /**
     * 同步可视对讲点设备
     *
     * @param dataList     可视对讲数据
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
     * @param visualId 可视对讲id
     * @return com.wr.remote.aiot.Visual
     */
    Visual selectVisualById(Long visualId);

    /**
     * 根据ids查询设备信息
     *
     * @param visualIds 设备id
     * @return java.util.List<com.wr.remote.aiot.Visual>
     * @author yx
     * @date 2022/11/17 16:48
     */
    List<Visual> selectVisualByIds(List<Long> visualIds);

    /**
     * 更新可视对讲
     *
     * @param visual 可视对讲信息
     * @return java.lang.Integer
     */
    Integer updateVisual(Visual visual);

    /**
     * 查询未设置设备二维码的设备id集
     *
     * @return java.lang.Long[]
     */
    List<Visual> selectIdsOfNotQrCode();

    /**
     * 批量设置可视对讲二维码
     *
     * @param visualList 可视对讲信息
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(@Param("visualList") List<Visual> visualList);

    /**
     *
     * @param visual
     */
    void insert(Visual visual);

    /**
     *
     * @param visual
     */
    void update(Visual visual);

    /**
     *
     * @param indexCode
     * @return
     */
    Visual getByIndexCodeInfo(@Param("indexCode") String indexCode);

    void delete();
}
