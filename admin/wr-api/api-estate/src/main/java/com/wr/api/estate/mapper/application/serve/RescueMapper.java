package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.application.serve.RescueUser;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:49:36
 * @Desc: 社会援助库管层
 */
@Mapper
public interface RescueMapper {

    /**
     * 获取社会救助列表
     * @param rescueVO
     * @return
     */
    List<RescueVO> selectRescueList(RescueVO rescueVO);

    /**
     * 获取社会详情
     * @param rescueId
     * @return
     */
    RescueVO getRescueDetail(Long rescueId);

    /**
     * 获取志愿者信息
     * @param rescueId
     * @return
     */
    List<RescueUser> selectRescueUserList(Long rescueId);

    /**
     * 获取发布人信息
     * @param releaseId
     * @param releaseType
     * @return
     */
    RescueVO getReleaseInfo(@Param("releaseId") Long releaseId,@Param("releaseType")String releaseType);

}
