package com.wr.remote.estate.center;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 19:55:38
 * @Desc: 楼栋
 */
@Data
public class Building extends BaseEntity {

    /**
     * 主键
     */
    private Long buildingId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋名
     */
    private String buildingName;

    private String flag;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

}
