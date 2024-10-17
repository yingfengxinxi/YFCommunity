package com.wr.estate.service.customer.decorate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.decorate.BusDecorate;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:35:17
 * @Desc: 装修列表
 */
public interface DecorationService extends IService<BusDecorate> {

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
    AjaxResult getDecorationDetail(String id);


}
