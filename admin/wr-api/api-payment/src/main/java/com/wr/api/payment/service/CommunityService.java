package com.wr.api.payment.service;


import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;

import java.util.List;

/**
 * 小区业务服务
 * @Author RainCity
 * @Date 2021-11-17 09:58:23
 */
public interface CommunityService {

    /**
     * 查询小区的微信账号
     * @param communityId 小区id
     * @return java.util.List<com.wr.common.payment.domain.CommunityWechat>
     */
    List<CommunityWechat> selectWechatByCommunityId(Long communityId);

    /**
     * 查询小区的支付宝账号
     * @param communityId 小区id
     * @return java.util.List<com.wr.common.payment.domain.CommunityWechat>
     */
    List<CommunityAli> selectAliByCommunityId(Long communityId);


}
