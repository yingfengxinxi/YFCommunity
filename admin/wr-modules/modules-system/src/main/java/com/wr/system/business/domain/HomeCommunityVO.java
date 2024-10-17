package com.wr.system.business.domain;

import lombok.Data;

/**
 * 首页评分
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 14:38:08
 * @Desc:
 */
@Data
public class HomeCommunityVO {

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 小区名称
     */
    private String communityName;
}
