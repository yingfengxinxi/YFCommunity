package com.wr.estate.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 18:09:16
 * @Desc:
 */
@Data
@ToString
public class CleanVo {

    private Long cleanId;

    private Long communityId;

    private String cleanNo;

    private String cleanName;

    private String dataSource;

    private Long planId;

    private Long staffId;

    private String orderNo;

    private Long venueId;

    private String address;

    private String allotWay;

    private Integer patrolNum;

    private BigDecimal gapTime;

    private Date startTime;

    private Date endTime;

    private String staffName;

    private Integer assistNum;

    private String transferStatus;

    private String allotStatus;

    private String acceptStatus;

    private String executeStatus;

    private Long executeId;

    private Date executeTime;

    private Date finishTime;

    private String imageUrl;

    private String remark;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String delFlag;
}
