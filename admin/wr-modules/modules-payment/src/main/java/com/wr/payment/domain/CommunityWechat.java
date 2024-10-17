package com.wr.payment.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信配置表实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:23
 */
@Data
public class CommunityWechat implements Serializable {

    private static final long serialVersionUID = -6542832437557351534L;

    private Long wechatId;

    /**
     * 微信支付appId
     */
    private String appId;
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

}
