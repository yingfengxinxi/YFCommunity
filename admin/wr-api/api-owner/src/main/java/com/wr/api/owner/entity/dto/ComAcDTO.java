package com.wr.api.owner.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:58:12
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComAcDTO {
    /**
     * 主键
     */
    private Long activityId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 0是已签到 1已报名
     */
    private String ApplySign;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format ="yyyy-MM-dd")
    private Date startTime;

    /**
     * 图片
     */
    private String actUrl;
}
