package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/6/3 14:34
 */
@Data
public class PropertyFeeImportVo {

    @Excel(name = "所属小区")
    private String communityName;

    @Excel(name = "所属楼栋")
    private String buildingName;

    @Excel(name = "所属单元楼")
    private String unitName;

    @Excel(name = "所属房号")
    private String roomName;

    //缴费金额
    @Excel(name = "缴费金额")
    private BigDecimal payAmount;

    //优惠金额
    @Excel(name = "优惠金额")
    private BigDecimal discountAmount;

    //优惠金额
    @Excel(name = "实缴金额")
    private BigDecimal paidAmount;

    //缴费周期(开始时间)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴费周期(开始时间)")
    private Date startTime;
    //缴费周期(结束时间)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴费周期(结束时间)")
    private Date endTime;



}
