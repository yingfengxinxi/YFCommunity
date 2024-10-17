package com.wr.api.owner.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:54:38
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueDTO {

    /** 主键 */
    private Long appointId;

    /** 主键 */
    private Long venueId;

    /** 小区id */
    private Long communityId;

    /** 场馆名称 */
    private String venueName;

    /** 场馆地址 */
    private String address;

    private String venueImage;

    /**
     * 状态：0=可用，1=关闭
     */
    private String venueStatus;

    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 状态 0:进行中，1：未开始，2：已结束，3：未知
     */
    private String timeStatus;

    private String appointStatus;

    private String cardNo;
}
