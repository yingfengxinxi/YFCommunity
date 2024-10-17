package com.wr.api.estate.service.application.serve;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.decorate.BusDecorate;
import com.wr.remote.estate.customer.vo.decorate.DecorateInfoVo;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:35:17
 * @Desc: 装修列表
 */
public interface DecorationService {

    /**
     * 查询装修列表
     *
     * @param decorateVo
     * @return
     */
    List<DecorateVo> getDecorateVoList(DecorateVo decorateVo);

    /**
     * 查询装修详情
     *
     * @param id
     * @return
     */
    DecorateInfoVo getDecorationDetail(String id);


    void updateByRenovationAuditStatus(BusDecorate busDecorate);
}
