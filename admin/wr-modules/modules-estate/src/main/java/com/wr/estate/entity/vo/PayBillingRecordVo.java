package com.wr.estate.entity.vo;

import com.wr.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/26 17:37
 */
@Data
@ApiModel("缴费账单记录Vo")
public class PayBillingRecordVo {

    private String id;
    @Excel(name = "所属小区")
    private String communityName;
    //小区id
    private Long communityId;
    private Long estateId;

    @Excel(name = "楼栋")
    private String buildingName;
    //楼栋Id
    private Long buildingId;
    @Excel(name = "单元号")
    private String unitName;
    private Long unitId;
    @Excel(name = "房间号")
    private String roomName;
    private Long roomId;
    @Excel(name = "缴费渠道",readConverterExp = "0=线下,1=线上")
    private String type;

    @Excel(name = "业主姓名")
    private String ownerName;
    @Excel(name = "房屋面积")
    private String totalArea;

    @Excel(name = "缴费金额")
    private BigDecimal payAmount;
    @Excel(name = "优惠金额")
    private BigDecimal discountAmount;
    @Excel(name = "实缴金额")
    private BigDecimal paidAmount;

    @Excel(name = "缴费类型")
    private String payTypeName;
    private String payType;
    private String isUnpaidPropertyFee;


    @Excel(name = "缴费周期(开始时间)", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @Excel(name = "缴费周期(结束时间)", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @Excel(name = "缴费人")
    private String payBy;

    @Excel(name = "缴费时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    private String payStatTime;
    private String payEndTime;

}
