package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.vo.application.serve.RescueVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:48:21
 * @Desc: 社会援助接口层
 */
public interface RescueService {

    /**
     * 获取社会救助列表
     * @param rescueVO
     * @return
     */
    List<RescueVO> selectRescueList(RescueVO rescueVO);

    /**
     * 获取社会救助详情
     * @param rescueId
     * @return
     */
    RescueVO getRescueDetail(Long rescueId);
}
