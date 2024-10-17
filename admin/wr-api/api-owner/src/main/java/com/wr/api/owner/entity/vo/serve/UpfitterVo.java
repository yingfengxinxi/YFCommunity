package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 15:31:35
 * @Desc: 装修工
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpfitterVo {
    /**
     * 主键
     */
    private Long workerId;
    /**
     * 装修id
     */
    private Long decorateId;

    /**
     * 数据类型：0=首次，1=非首次
     */
    private String workerType;
    /**
     * 审核状态：0=审核中，1=驳回，2=通过
     */
    private String auditStatus;
    /**
     * 装修工姓名
     */
    private String workerName;
    /**
     * 手机号
     */
    private String workerPhone;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 审批人
     */
    private String auditBy;
    /**
     * 装修工审批人
     */
    private String workerAuditBy;
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 装修工审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date workerAuditTime;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 装修工驳回理由
     */
    private String workerRejectReason;

    private Long accountId;

    private  String staffName;

    private String staffPhone;

    /**
     * 装修工内外部:0=内部，1=外部
     */
    private String intOut;

    private String Inside;
}
