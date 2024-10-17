package com.wr.payment.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付宝配置表 实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:20
 */
@Data
public class CommunityAli implements Serializable {
    private static final long serialVersionUID = 1208133546372442560L;
    private Long alipayId;

    /**
     * 支付宝商户appid
     */
    private String appId;
    /**
     * 支付宝公钥
     */
    private String publicKey;
    /**
     * 商户私钥
     */
    private String privateKey;

}
