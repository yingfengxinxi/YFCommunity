package com.wr.remote.govern.autonomy.social;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:09:58
 * @Desc:  社交点赞记录表
 */
@Data
public class SocialLike extends BaseEntity {

    private static final long serialVersionUID = -6948891729416188406L;
    /**
     * 社交id，表xccj_govern.bus_social
     */
    private Long socialId;

    /**
     * 人员类型：0=系统，1=业主，2=租客
     */
    private String userType;

    /**
     * 人员id，user_type对应表id
     */
    private Long userId;


}
