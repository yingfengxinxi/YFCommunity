package com.wr.api.owner.entity.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:42:49
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitmentDTO {
    /**
     * 主键
     */
    private Long decorateId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 业主id，表bus_owner
     */
    private Long ownerId;

    /**
     * 装修地址
     */
    private String decorateName;

    /**
     * 小区名
     */
    private String communityName;

    /**
     * 单元名
     */
    private String unitName;

    /**
     * 楼栋名
     */
    private String buildingName;

    /**
     * 房间号
     */
    private String roomName;

    /**
     * 装修人数
     */
    private Integer countPeople;

    /**
     * 计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;

    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;

    /**
     * 审核状态：0=审核中，1=驳回，2=通过（进行中），3=延期待确认,4=已延期，5=已完成
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date auditTime;

}
