package com.wr.api.owner.service.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.owner.entity.dto.SecondHandDTO;
import com.wr.api.owner.entity.vo.serve.SecondHandVo;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.secondhand.SecondHand;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:14:10
 * @Desc:
 */
public interface SecondHandService extends IService<SecondHand> {
    /**
     * 二手交易列表
     *
     * @param secondHandVo
     * @return
     */
    List<SecondHandDTO> getSecondHandList(SecondHandVo secondHandVo);

    /**
     * 添加二手交易
     *
     * @param secondHandVo
     * @return
     */
    AjaxResult addSecondHand(SecondHandVo secondHandVo);

    /**
     * 查询二手交易详情
     *
     * @param handId
     * @return
     */
    SecondHandVo SecondHandInfo(Long handId);

    /**
     * 下架
     *
     * @param handId id
     * @return int
     * @author yx
     * @date 2023/1/13 13:15
     */
    int outStack(Long handId);

    /**
     * 已出售
     *
     * @param handId id
     * @return int
     * @author yx
     * @date 2023/1/13 13:24
     */
    int sell(Long handId);
}
