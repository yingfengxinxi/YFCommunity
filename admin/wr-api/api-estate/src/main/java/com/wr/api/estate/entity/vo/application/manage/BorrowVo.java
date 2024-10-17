package com.wr.api.estate.entity.vo.application.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:34:34
 * @Desc:
 */
@Data
public class BorrowVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1842509830508972852L;
    /** 主键*/
    private Long borrowId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 物品id，表bus_goods*/
    private Long goodsId;

    /** 借用人账号id，表bus_account*/
    private Long accountId;

    /** 借用人类型，表bus_account*/
    private String accountType;

    /** 申请时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 预计归还时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /** 审核状态：0=审核中，1=驳回，2=通过*/
    private String auditStatus;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 驳回理由*/
    private String rejectReason;

    /** 二维码片路径*/
    private String codeUrl;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /** 用户名*/
    private String accountName;

    /** 账号（手机号）*/
    private String loginName;

    /** 物品编号*/
    private String goodsNo;

    /** 物品名称*/
    private String goodsName;

    /** 分类信息*/
    private String classifyName;

    /** 存放地址*/
    private String address;

    /** 分类id，表bus_classify*/
    private Long classifyId;

    /** 审批人*/
    private String auditBy;


    /** 借用天数*/
    private Integer borrowDays;

    /** 借用人名字*/
    private String ownerName;
    private String tenantName;

    private String name;
    /** 借用人手机号*/
    private String ownerPhone;
    private String tenantPhone;

    private String phone;
    private String communityName;

    private String buildingName;

    private String unitName;

    private String roomName;
    /** 物品价格*/
    private BigDecimal goodsPrice;

    /** 图片路径*/
    private String goodsUrl;
}
