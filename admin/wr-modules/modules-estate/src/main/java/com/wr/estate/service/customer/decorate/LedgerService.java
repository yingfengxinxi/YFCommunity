package com.wr.estate.service.customer.decorate;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 13:31:54
 * @Desc: 装修台账
 */
public interface LedgerService {

    /**
     * 查询装修台账列表
     *
     * @param decorateVo
     * @return
     */
    List<DecorateVo> getDecorateLedgerList(DecorateVo decorateVo);

    /**
     * 查询装修台账详情
     *
     * @param decorateId
     * @return
     */
    AjaxResult getDecorateLedgerDetail(Long decorateId);
}
