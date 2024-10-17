package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 农行配置表 实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:19
 */
@Data
public class CommunityAbc extends BaseEntity {
    /**
     * 社区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 农行商户号
     */
    private String merchantId;
    /**
     * 商户私钥存储地址
     */
    private String privateUrl;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
}
