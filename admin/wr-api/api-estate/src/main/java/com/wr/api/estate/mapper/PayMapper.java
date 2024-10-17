package com.wr.api.estate.mapper;

import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 14:58:20
 * @Desc:
 */
public interface PayMapper {


    /**
     * 查询微信账号
     *
     * @param wechatId    主键id
     * @param communityId 小区id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    List<CommunityWechat> selectWechatById(@Param("communityId") Long communityId, @Param("wechatId") Long wechatId);

    /**
     * 查询支付宝账号
     *
     * @param alipayId 支付宝id
     * @return com.wr.remote.domain.CommunityAli
     */
    CommunityAli selectAliById(Long alipayId);

}
