package com.wr.remote.estate.financial;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("车位缴费订单表")
@TableName(value = "xccj_community.bus_park_order")
public class ParkOrder extends BaseEntity {

    private String id;
    //订单号
    private String orderNo;
    //资产id，表xccj_community.bus_staff_property
    private String staffPropertyId;
    //车位管理ID
    private String carportId;
    //实际缴费费用
    private BigDecimal fee;
    //支付状态0 = 待支付1 = 支付成功2 = 取消支付
    @TableField(value = "`status`")
    private String status;
    //支付成功时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feeSuccessTime;
    //租赁购买0=租1=买
    private String leasePurchase;
    //购买套餐0=包年1=包月
    private String purchasePackage;
    //车位到期时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    //租赁几年或几月
    @TableField(value = "`year_month`")
    private Integer yearMonth;
    //是否续费0=否1=是
    private String isRenew;
    //删除0=正常1=删除
    private String delFlag;
}
