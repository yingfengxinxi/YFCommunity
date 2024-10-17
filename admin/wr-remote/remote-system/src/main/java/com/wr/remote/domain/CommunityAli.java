package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 支付宝配置表 实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:20
 */
@Data
public class CommunityAli extends BaseEntity {
    private static final long serialVersionUID = 2662502466329665612L;

    private Long alipayId;
    /**
     * 社区id，表xccj_community.bus_community
     */
    private Long communityId;
    private String communityName;

    private String alipayName;
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
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;

    private String aliStatus;

    private Long estateId;
}
