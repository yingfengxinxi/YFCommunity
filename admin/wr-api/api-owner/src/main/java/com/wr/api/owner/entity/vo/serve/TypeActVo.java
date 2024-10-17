package com.wr.api.owner.entity.vo.serve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuShu
 * @Date: 2022-12-08 13:06:36
 * @Desc: 活动类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeActVo {
    /**
     * 主键
     */
    private Long typeId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动类型名称
     */
    private String activityName;


}
