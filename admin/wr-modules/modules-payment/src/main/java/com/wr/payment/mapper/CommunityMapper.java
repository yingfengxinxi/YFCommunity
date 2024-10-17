package com.wr.payment.mapper;

import com.wr.payment.domain.CommunityAli;
import com.wr.payment.domain.CommunityWechat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 小区数据层
 * @Author RainCity
 * @Date 2022-11-11 18:13:12
 */
@Mapper
public interface CommunityMapper {

    /**
     * 查询支付宝账号
     * @param alipayId 支付宝id
     * @return com.wr.remote.domain.CommunityAli
     */
    CommunityAli selectAliById(Long alipayId);

    /**
     * 查询微信账号
     * @param wechatId 主键id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    CommunityWechat selectWechatById(@Param("wechatId") Long wechatId, @Param("communityId")Long communityId);

}
