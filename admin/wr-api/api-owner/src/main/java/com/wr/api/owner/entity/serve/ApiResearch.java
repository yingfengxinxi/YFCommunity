package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 党建调研表
 * @Author: lvzy
 * @Date: 2022-11-15 17:55:40
 * @Desc:党建调研表
 */
@Data
public class ApiResearch extends BaseEntity implements Serializable {

    /**
     * 党建调研表ID
     */
    private Long researchId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 名称
     */
    private String researchName;

    /**
     * 简介
     */
    private String researchIntro;

    /**
     * 状态：0=启用，1=关闭
     */
    private String researchStatus;

    /**
     * 党建调研状态 0 未发布 1 进行中 2 已提交 3 已结束
     */
    private String status;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deadlineTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}