package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 微信配置表实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:23
 */
@Data
public class CommunityWechat extends BaseEntity {
    private static final long serialVersionUID = -9198609203639604212L;

    private Long wechatId;
    /**
     * 社区id，表xccj_community.bus_community
     */
    private Long communityId;
    private String communityName;

    private String wechatName;
    /**
     * 微信支付appId
     */
    private String appId;
    /**
     * AppSecret(小程序密钥)
     */
    private String appSecret;
    /**
     * 微信公众号appid
     */
    private String publicAppId;
    /**
     * 微信公众号密钥
     */
    private String publicSecret;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 证书序列号
     */
    private String serialNumber;
    /**
     * APIv3密钥
     */
    private String apiKey;
    /**
     * 商户私钥存储地址
     */
    private String privateUrl;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;

    private String wechatStatus;

    private Long estateId;
    /**
     * 业主端微信支付appId
     */
    private String ownerAppId;
    /**
     * 业主端AppSecret(小程序密钥)
     */
    private String ownerAppSecret;
}
