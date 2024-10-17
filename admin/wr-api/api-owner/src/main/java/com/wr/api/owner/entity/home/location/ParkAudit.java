package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 园区报事申请表(BusParkAudit)实体类
 *
 * @author lvzy
 * @since 2022-12-01 13:14:27
 */
@Data
public class ParkAudit{

    /**
     * 主键
     */
    private Long auditId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 申请名称
     */
    private String auditName;
    /**
     * 业主id，表xccj_community.bus_owner
     */
    private Long ownerId;
    /**
     * 园区报事类型id，表xccj_work.bus_park_repair
     */
    private Long repairId;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;

    /**
     * 工单图片集
     */
    private String[] images;
}

