package com.wr.payment.service;

import com.wr.payment.domain.CommunityAli;
import com.wr.payment.domain.CommunityWechat;

/**
 * 小区业务服务
 * @Author RainCity
 * @Date 2021-11-17 09:58:23
 */
public interface CommunityService {

    /**
     * 查询微信配置
     * @param wechatId 主键id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    CommunityWechat selectWechatById(Long wechatId,Long communityId);

    /**
     * 查询支付宝账号
     * @param alipayId 支付宝id
     * @return com.wr.remote.domain.CommunityAli
     */
    CommunityAli selectAliById(Long alipayId);


}
