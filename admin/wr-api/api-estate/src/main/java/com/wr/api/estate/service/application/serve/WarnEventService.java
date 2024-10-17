package com.wr.api.estate.service.application.serve;

import com.wr.remote.aiot.WarnEvent;
import com.wr.common.customize.util.CommunityHik;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 09:39:39
 * @Desc: 预警事件数据处理
 */
public interface WarnEventService {

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return {@link CommunityHik}
     * @ver v1.0.0
     */
    CommunityHik selectCommunityHikById(Long communityId);


    /**
     * 查询预警事件
     * @param communityId
     * @param srcName
     * @return
     */
    List<LinkedHashMap<String,Object>> selectWarnEventList(Long communityId,String srcName);

    /**
     * 根据id查询数据
     * @param warnId 预警id
     * @return
     */
    WarnEvent selectWarnEventById(Long warnId);

    /**
     * 查询门禁预警扩展事件
     * @param warnId 预警id
     * @return java.util.List<java.util.LinkedHashMap>
     */
    List<LinkedHashMap<String,Object>> selectAcsDataList(Long warnId, boolean getImg, String eventTyp);

    /**
     * 查询可视对讲预警事件详情
     * @param warnId 预警id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectVisDataList(Long warnId, boolean getImg, String eventTyp);

    /**
     * 查询视频预警事件扩展信息
     * @param warnId 预警id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectVideoDataList(Long warnId);

    /**
     * 查询视频预警分析结果
     * @param dataId 扩展信息id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectVideoResultList(Long dataId, boolean getImg, String eventTyp);

    /**
     * 查询烟雾/火点检测分析结果
     * @param dataId 扩展信息id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectSmokeFireList(Long dataId, boolean getImg, String eventTyp);

}
