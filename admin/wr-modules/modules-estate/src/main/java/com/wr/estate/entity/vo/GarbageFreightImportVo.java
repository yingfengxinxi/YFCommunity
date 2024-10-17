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
public class GarbageFreightImportVo {

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

    //缴费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "缴费时间")
    private Date payTime;

    //缴费类型数据字典 pay_type
    @Excel(name = "缴费类型", readConverterExp = "2=装修垃圾清运费,3=墙体拆除费", combo = "装修垃圾清运费,墙体拆除费")
    private String payType;

}
