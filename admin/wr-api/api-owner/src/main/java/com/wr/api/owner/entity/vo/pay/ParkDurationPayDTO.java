package com.wr.api.owner.entity.vo.pay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/20 20:04
 */
@Data
public class ParkDurationPayDTO {

    private String orderNo;

    //小区id
    private Long communityId;
    //费用
    private BigDecimal fee;
    //车位缴费规则id
    private String parkDurationId;
    //租赁购买0=租1买
    private String leasePurchase;
    //购买套餐0=包年1=包月
    private String purchasePackage;
    //年月份
    private Integer yearMonth;
    //车位管理id
    private String carportId;
    //结束日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;
    //openid
    private String openId;
    //物业缴费id
    private String logId;
}
