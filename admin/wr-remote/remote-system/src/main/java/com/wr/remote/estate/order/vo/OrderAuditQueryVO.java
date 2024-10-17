package com.wr.remote.estate.order.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-04 15:10:43
 * @Desc:
 */
@Data
@ToString
public class OrderAuditQueryVO {

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 申请人名称
     */
    private String applyName;

    /**
     * 申请类型
     */
    private String auditType;

    /**
     * 申请状态
     */
    private String auditStatus;

    /**
     * 工单类型
     */
    private String orderType;

    /**
     * 审核id
     */
    private Long auditId;
}
