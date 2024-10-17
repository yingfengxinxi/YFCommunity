package com.wr.estate.service.financial;

import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/1
 * @Description:
 * @FileName: AccountManagementService
 * @History:
 */
public interface AccountManagementService {

    /**
     * 查询支付宝账号列表
     * @param communityAli 支付宝账号实体
     * @return {@link List< CommunityAli>}
     * @Author RainCity
     * @Date 2023-04-27 11:53:26
     * @ver v1.0.0
     */
    List<CommunityAli> selectAliList(CommunityAli communityAli);

    /**
     * 添加支付宝账号
     * @param communityAli 支付宝账号信息
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:33:45
     * @ver v1.0.0
     */
    Integer insertAli(CommunityAli communityAli);

    /**
     * 修改支付宝账号
     * @param communityAli 支付宝账号信息
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:34:26
     * @ver v1.0.0
     */
    Integer updateAli(CommunityAli communityAli);

    /**
     * 删除支付宝账号
     * @param alipayId id
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:38:05
     * @ver v1.0.0
     */
    Integer removeAli(Long alipayId);


    /**
     * 查询微信账号列表
     * @param communityWechat 微信账号实体
     * @return {@link List< CommunityWechat >}
     * @Author RainCity
     * @Date 2023-04-27 11:53:26
     * @ver v1.0.0
     */
    List<CommunityWechat> selectWechatList(CommunityWechat communityWechat);

    /**
     * 添加微信账号
     * @param communityWechat 微信账号信息
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:33:45
     * @ver v1.0.0
     */
    Integer insertWechat(CommunityWechat communityWechat);

    /**
     * 修改微信账号
     * @param communityWechat 微信账号信息
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:34:26
     * @ver v1.0.0
     */
    Integer updateWechat(CommunityWechat communityWechat);

    /**
     * 删除微信账号
     * @param wechatId id
     * @return {@link Integer}
     * @Author RainCity
     * @Date 2023-04-27 12:38:05
     * @ver v1.0.0
     */
    Integer removeWechat(Long wechatId);


}
