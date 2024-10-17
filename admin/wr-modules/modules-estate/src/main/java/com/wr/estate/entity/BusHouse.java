package com.wr.estate.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusHouse implements Serializable {
    private Long houseId;

    private Long communityId;

    private Long buildingId;

    private Long unitId;

    private Long roomId;

    private Long ownerId;

    private String auditStatus;

    private Date auditTime;

    private String rejectReason;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String delFlag;
}