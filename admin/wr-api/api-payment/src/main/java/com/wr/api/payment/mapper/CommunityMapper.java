package com.wr.api.payment.mapper;


import com.wr.remote.domain.CommunityAli;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.CommunityWechat;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-11 18:13:12
 * @Desc: 小区数据层
 */
public interface CommunityMapper {

    /**
     * 根据小区id查询数据
     * @param communityId 小区id
     * @return com.wr.remote.domain.Community
     */
    CommunityHik selectCommunityHikById(Long communityId);

    /**
     * 查询小区的微信账号
     * @param communityId 小区id
     * @return java.util.List<com.wr.common.payment.domain.CommunityWechat>
     */
    List<CommunityWechat> selectWechatByCommunityId(Long communityId);

    /**
     * 查询微信账号
     * @param wechatId 主键id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    CommunityWechat selectWechatById(Long wechatId);

    /**
     * 查询小区的支付宝账号
     * @param communityId 小区id
     * @return java.util.List<com.wr.common.payment.domain.CommunityWechat>
     */
    List<CommunityAli> selectAliByCommunityId(Long communityId);

    /**
     * 查询支付宝账号
     * @param alipayId 支付宝id
     * @return com.wr.remote.domain.CommunityAli
     */
    CommunityAli selectAliById(Long alipayId);

}
