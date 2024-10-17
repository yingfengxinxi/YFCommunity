package com.wr.remote.estate.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.work.patrol.vo.AssistStaffListVO;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-04 17:21:53
 * @Desc:
 */
@Data
@ToString
public class OrderAuditDetailVO {

    // id
    private Long auditId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 工单类型
     */
    private String orderType;

    /**
     * 申请类别
     */
    private String auditType;

    /**
     * 接收人
     */
    private String receiveName;

    /**
     * 接收手机号
     */
    private String receivePhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 协助人员了你报
     */
    private List<AssistStaffListVO> staffList;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 申请人
     */
    private String applyName;

    /**
     * 审核人
     */
    private String userName;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 驳回理由
     */
    private String rejectReason;
}
