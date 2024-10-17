package com.wr.api.estate.entity.application.serve;

import com.wr.common.core.business.annotation.SensitiveWord;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 场馆活动表(BusVenueAppoint)实体类
 *
 * @author lvzy
 * @since 2022-11-15 16:30:40
 */
@Data
public class VenueAppoint{
    /**
     * 主键
     */
    private Long appointId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 预约人姓名
     */
    @SensitiveWord
    private String userName;
    /**
     * 预约人手机号
     */
    private String userPhone;
    /**
     * 预约人身份证号
     */
    private String cardNo;
    /**
     * 活动id，表xccj_community.bus_venue_act
     */
    private Long activityId;
    /**
     * 活动名称
     */
    @SensitiveWord
    private String activityName;
    /**
     * 场馆id，表xccj_community.bus_venue
     */
    private Long venueId;
    /**
     * 计划开始时间
     */
    private String planStart;
    /**
     * 计划结束时间
     */
    private String planEnd;
    /**
     * 所付押金
     */
    private Double deposit;
    /**
     * 实际结束时间
     */
    private String currentEnd;
    /**
     * 状态：0=未支付，1=已支付，2=已退款，3=无需退款
     */
    private String appointStatus;
    /**
     * 支付方式：0=微信，1=支付宝，2=现金
     */
    private String payWay;
    /**
     * 支付金额
     */
    private Double payAmount;
    /**
     * 退款方式：0=微信，1=支付宝，2=现金
     */
    private String refundWay;
    /**
     * 退款金额
     */
    private Double refundAmount;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

}

