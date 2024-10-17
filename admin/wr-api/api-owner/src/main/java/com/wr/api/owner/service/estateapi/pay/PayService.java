package com.wr.api.owner.service.estateapi.pay;

import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 15:01:15
 * @Desc:
 */
public interface PayService {

    /**
     * 查询微信账号
     * @param wechatId 主键id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    List<CommunityWechat> selectWechatById(Long communityId, Long wechatId);

    /**
     * 查询支付宝账号
     * @param alipayId 支付宝id
     * @return com.wr.remote.domain.CommunityAli
     */
    CommunityAli selectAliById(Long alipayId);
}
