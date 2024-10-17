package com.wr.api.estate.mapper.application.serve;

import com.wr.remote.aiot.*;
import com.wr.common.customize.util.CommunityHik;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 09:18:20
 * @Desc: 预警事件
 */
public interface WarnEventMapper {

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);

    /**
     * 查询预警事件
     * @param
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectWarnEventList(@Param("communityId") Long communityId, @Param("srcName") String srcName);

    /**
     * 根据id查询数据
     * @param warnId 预警id
     * @return
     */
    WarnEvent selectWarnEventById(Long warnId);

    /**
     * 查询门禁预警扩展事件
     * @param warnId 预警id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectAcsDataList(Long warnId);

    /**
     * 查询可视对讲预警事件详情
     * @param warnId 预警id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectVisDataList(Long warnId);

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
    List<LinkedHashMap<String,Object>> selectVideoResultList(Long dataId);

    /**
     * 查询烟雾/火点检测分析结果
     * @param dataId 扩展信息id
     * @return java.util.LinkedHashMap
     */
    List<LinkedHashMap<String,Object>> selectSmokeFireList(Long dataId);
}
