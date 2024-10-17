package com.wr.api.estate.entity.vo.application.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:32:10
 * @Desc:
 */
@Data
public class GoodsVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1639367666246126841L;

    /** 主键*/
    private Long goodsId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 物品编号*/
    private String goodsNo;

    /** 物品名称*/
    private String goodsName;

    /** 分类id，表bus_classify*/
    private Long classifyId;

    /** 物品价格*/
    private BigDecimal goodsPrice;

    /** 图片路径*/
    private String goodsUrl;

    /** 借用状态：0=空闲中，1=借用中*/
    private String borrowStatus;

    /** 存放地址*/
    private String address;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /** 分类信息*/
    private String classifyName;

    /** 借用次数*/
    private Integer borrowCount;

    /** 借用天数*/
    private Integer borrowDays;

    /** 借用人名字*/
    private String ownerName;
    private String tenantName;

    /** 借用人手机号*/
    private String ownerPhone;
    private String tenantPhone;

    private String communityName;

    private String buildingName;

    private String unitName;

    private String roomName;

    private Long accountId;


    /** 审核状态：0=审核中，1=驳回驳回，2=通过*/
    private String auditStatus;

    /** 驳回理由*/
    private String rejectReason;

    /** 借用申请时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 预计归还时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    private List<BorrowVo> borrowVos;

    /** 审批人*/
    private  String auditBy;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 赔偿方式：0=现金，1=物品*/
    private String compensateWay;

    /** 赔偿时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date compensateTime;

    /*借用次数*/
    private int numbers;

    /*物业id*/
    private Long estateId;

}
