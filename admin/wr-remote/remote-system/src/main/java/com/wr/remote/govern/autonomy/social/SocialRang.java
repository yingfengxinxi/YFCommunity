package com.wr.remote.govern.autonomy.social;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:07:54
 * @Desc: 社交-小区关联表
 */
@Data
public class SocialRang extends BaseEntity {

    private static final long serialVersionUID = 4747939105472097271L;
    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

}
