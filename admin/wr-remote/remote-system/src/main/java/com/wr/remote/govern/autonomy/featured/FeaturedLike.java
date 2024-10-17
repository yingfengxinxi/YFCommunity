package com.wr.remote.govern.autonomy.featured;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:09:58
 * @Desc:  特色板块点赞记录表
 */
@Data
public class FeaturedLike extends BaseEntity {


    private static final long serialVersionUID = -6948891729416188406L;
    /**
     * 特色板块id，表xccj_govern.bus_featured_section
     */
    private Long featuredId;

    /**
     * 人员类型：0=系统，1=业主，2=租客
     */
    private String userType;

    /**
     * 人员id，user_type对应表id
     */
    private Long userId;

}
