package com.wr.api.owner.entity.vo.home;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 10:47:42
 * @Desc: 物业缴费记录
 */
@Data
public class PropertyLogVO implements Serializable {
    private static final long serialVersionUID = -8869150287544910646L;

    private Long logId;

    private Long roomId;
    //已缴费用
    private BigDecimal payAmount;
    //计费标准
    private BigDecimal feeAmount;
    //应交费用
    private BigDecimal yjAmount;
    //优惠金额
    private BigDecimal discountAmount;
    //实缴金额
    private BigDecimal paidAmount;

    //弃用
    private Integer payYear;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;
    //订单号
    private String orderNo;
    //缴费状态0=账单待发送1=待支付2=已付款
    private String payStatus;
    //收费类型：0=按住宅面积收费，1=统一收费
    private String feeType;
    //缴费方式:0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
    private String payMethod;


}
