package com.wr.api.owner.entity.vo.pay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/21 11:48
 */
@Data
public class ParkOrderVo {

    //车牌号
    private String plateNo;
    //车位编号
    private String vehicleNo;
    //租赁购买0=租1=买
    private String leasePurchase;
    //购买套餐0=包年1=包月
    private String purchasePackage;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //年月
    private Integer yearMonth;
    //费用
    private BigDecimal fee;
    //订单号
    private String orderNo;
    //状态0=未支付1=已支付2=取消支付
    private String status;
}
