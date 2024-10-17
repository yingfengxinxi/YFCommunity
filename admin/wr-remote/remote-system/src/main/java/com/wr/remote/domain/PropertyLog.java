package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/3/10 16:03
 */
@Data
public class PropertyLog extends BaseEntity {
    //	主键
    private Long logId;
    //	物业缴费设置表id
    private String propertyFeeId;
    //订单号(生成规则,是有物业缴费设置表订单号+时间戳组成)
    private String orderNo;
    //小区id，表xccj_community.bus_community
    private Long communityId;
    //	楼栋id，表xccj_community.bus_building
    private Long buildingId;
    private String buildingName;
    //单元id，表xccj_community.bus_unit
    private Long unitId;
    private String unitName;
    //	当前所住房间id，表xccj_community.bus_room
    private Long roomId;
    private String roomName;
    //业主姓名
    private String ownerName;
    //业主手机号
    private String ownerPhone;
    //住宅面积
    private BigDecimal roomArea;
    //	缴费状态0=账单待发送1=待支付2=已付款
    private String payStatus;
    //缴费方式:0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
    private String payMethod;
    //通知类型0=站内消息1=短信消息
    private String sendType;
    //发送状态0=未发送1=成功2=失败
    private String sendStatus;
    //短信失败原因
    private String sendErrorMsg;
    //	收费类型：0=按住宅面积收费，1=统一收费
    private String feeType;
    //	计费标准
    private BigDecimal feeAmount;
    //应交费用
    private BigDecimal yjAmount;
    //优惠金额
    private BigDecimal discountAmount;
    //实际缴费金额
    private BigDecimal paidAmount;
    //已缴费用
    private BigDecimal payAmount;
    //是否是未交房物业费(0=否1=是)
    private String isUnpaidPropertyFee;
    //	缴费适用年限
    private Integer payYear;
    //	缴费方式：0=微信，1=支付宝，2=其他
    private String payWay;
    //缴费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    //	删除标记：0=未删除，1=已删除
    private String delFlag;
    //适用开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    //适用结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
