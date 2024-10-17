package com.wr.api.owner.entity.vo.location;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 园区报事
 *
 * @author lvzy
 * @since 2022-12-01 13:14:27
 */
@Data
public class ParkAuditVO {

    /**
     * 工单主键
     */
    private Long orderId;

    /**
     * 申请名称
     */
    private String auditName;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;

    /**
     * 发布人
     */
    private String ownerName;


    /**
     * 发布人
     */
    private String ownerPhone;

    /**
     * 工单类型
     */
    private String repairName;

    /**
     * 工单图片集
     */
    private List<String> images;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;

    /**
     * 主要负责人
     */
    private String masterName;

    /**
     * 主要负责人电话
     */
    private String masterPhone;

    /**
     * 执行人
     */
    private String executeName;

    /**
     * 执行人电话
     */
    private String executePhone;
}

