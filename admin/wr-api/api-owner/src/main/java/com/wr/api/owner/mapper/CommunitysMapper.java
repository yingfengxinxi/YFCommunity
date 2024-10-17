package com.wr.api.owner.mapper;

import com.wr.remote.domain.Community;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.domain.Estate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 15:02:37
 * @Desc: 小区mapper
 */
public interface CommunitysMapper {

    /**
     * 获取小区详情
     * @param communityId
     * @return: com.wr.remote.domain.Community
     */
    Community getCommunityById(@Param("communityId") Long communityId);

    /**
     * 获取物业详情
     * @param estateId
     * @return: com.wr.remote.domain.Estate
     */
    Estate getEstate(@Param("estateId") Long estateId);

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

    /**
     * 获取所有小区
     * @return: com.wr.remote.domain.Community
     */
    List<Community> getCommunityAll();
}
