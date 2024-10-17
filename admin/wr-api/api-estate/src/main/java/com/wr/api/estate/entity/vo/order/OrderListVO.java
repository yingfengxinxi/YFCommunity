package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 巡查工单对象 bus_patrol
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
@Data
public class OrderListVO implements Serializable {
    private static final long serialVersionUID = 650284525847279067L;
    /**
     * 主键
     */
    private Long orderId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 地址
     */
    private String address;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 是否可以抢单，1可以，0不可以
     */
    private Integer hasGrab;

    /**
     * 订单状态，1待分配，2待接单，3待执行，4转交中，5执行中，6已完成，7超时未完成，8超时完成
     */
    private Integer orderStatus;

    /**
     * 工单类型名称，详见OrderConstants
     */
    private String orderTypeName;

    /**
     * 工单类型
     */
    private Integer orderType;

    /**
     * 是否是协助人，1是，0否
     */
    private Integer hasAssist;
}
