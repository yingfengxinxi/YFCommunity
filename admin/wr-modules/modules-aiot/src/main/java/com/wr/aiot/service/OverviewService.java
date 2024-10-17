package com.wr.aiot.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.common.customize.model.GarageNodeSearch;

/**
 * @Author: RainCity
 * @Date: 2022-11-01 16:13:30
 * @Desc: 停车场数据总览
 */
public interface OverviewService {

    /**
     * 月度停车收费
     * @return com.alibaba.fastjson2.JSONObject
     */
    JSONObject selectMonthParkData();

    /**
     * 日车辆进出数据
     * @param inoutTime 月份
     * @param monthType 0：上半月； 1：下半月
     * @return com.alibaba.fastjson2.JSONObject
     */
    JSONObject selectVehicleLogData(String inoutTime, String monthType);

    /**
     * 车位占用统计
     * @return com.alibaba.fastjson2.JSONObject
     */
    JSONObject selectCarportUseData();

    /**
     * 获取停车库列表
     * @param communityId
     * @param parkIndexCodes
     * @return
     */
    JSONArray parkList(Long communityId, String parkIndexCodes);

    /**
     *
     * @param communityId
     * @param parkSyscode
     * @param command
     * @return
     */
    Boolean deviceControlBatch(Long communityId, String parkSyscode, Integer command);

    /**
     *根据停车场编码反控道闸
     * @param communityId
     * @param parkSyscode
     * @param command
     * @return
     */
    Boolean deviceControl(Long communityId, String parkSyscode, Integer command);

    /**
     * 查询停车库节点信息
     * @param garageNodeSearch
     * @return
     */
    JSONObject getGarageNode(GarageNodeSearch garageNodeSearch);

    JSONObject deviceRelation(Long communityId,String syscode, Integer nodeType ,String subNode,String deviceType);

    JSONObject resourcesByParams(Long communityId,String name,String resourceType, Integer pageNo ,Integer pageSize);

}
