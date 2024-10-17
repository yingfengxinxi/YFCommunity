package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/25 19:13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("缴费账单记录")
@TableName(value = "xccj_community.bus_pay_billing_record")
public class PayBillingRecord extends BaseEntity {

    //	主键
    private String id;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;
    //所属楼栋
    private Long buildingId;
    //所属单元
    private Long unitId;
    //房间号
    private Long roomId;
    //物业id
    private Long estateId;
    //	业主id
    private Long ownerId;
    //缴费渠道0=线下1=线上
    private String type;
    //缴费金额
    private BigDecimal payAmount;
    //实缴金额
    private BigDecimal paidAmount;
    //优惠金额
    private BigDecimal discountAmount;
    //缴费周期(开始时间)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    //缴费周期(结束时间)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    //缴费类型数据字典 pay_type
    @Excel(name = "缴费类型", readConverterExp = "0=物业费,1=车位管理费,2=装修垃圾清运费,3=墙体拆除费")
    private String payType;
    //缴费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    //缴费人
    private String payBy;
    //物业人员资产id(线下缴费使用字段)
    private String staffPropertyId;
    //车位编号
    private String vehicleNo;
    //0=租/1=出售
    @TableField(exist = false)
    private String leasePurchase;
    //套餐
    @TableField(exist = false)
    private String purchasePackage;
    //车牌id
    private Long plateNo;
    //天数
    @TableField(value = "`year_month`")
    private Integer yearMonth;
    //支付订单
    private String orderNo;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;
    //0=物业人员缴费1=业主人员缴费(车位费使用)
    private String flag;
    //物业费0=业主缴纳1=物业缴纳
    private String isUnpaidPropertyFee;

}
