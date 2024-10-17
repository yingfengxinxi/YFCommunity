package com.wr.remote.govern.autonomy.featured;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:07:54
 * @Desc: 特色板块-小区关联表
 */
@Data
public class  FeaturedRang extends BaseEntity {


    private static final long serialVersionUID = 4747939105472097271L;
    /**
     * 特色板块id，表xccj_govern.bus_featured_section
     */
    private Long featuredId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
}
