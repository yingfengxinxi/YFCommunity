package com.wr.remote.estate.customer.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 11:54:44
 * @Desc: 装修工类
 */
@Data
public class Worker extends BaseEntity {

    private static final long serialVersionUID = 8735258436794092011L;

    /**
     * 主键
     */
    private Long workerId;
    /**
     * 当前装修id，表bus_decorate
     */
    private Long decorateId;
    /**
     * 装修工姓名
     */
    private String workerName;
    //装修公司名称
    private String companyName;
    //装修公司负责人
    private String personName;
    //装修公司负责人联系方式
    private String personPhone;
    /**
     * 手机号
     */
    private String workerPhone;
    /**
     * 身份证号码
     */
    private String cardNo;

    //装修工内外部:0=内部，1=外部
    private String intOut;
    /**
     * 账号id，表bus_account(account_type=3)
     */
    private Long accountId;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date auditTime;
    /**
     * 数据类型：0=首次，1=非首次
     */
    private String workerType;
    /**
     * 审核状态：0=审核中，1=驳回，2=通过
     */
    private String auditStatus;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 审批人
     */
    private String auditBy;
    /**
     * 多个装修工id
     */
    private Long[] workerIds;

}
