package com.wr.remote.work;

import lombok.Data;

import java.io.Serializable;

/**
 * 巡X类型对象 bus_tour
 *
 * @author 奔波儿灞
 * @date 2022-10-28
 */
@Data
public class Abnormal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long abnormalId;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 工单类型
     */
    private String orderType;

    /**
     * 异常描述
     */
    private String abnormalInfo;

}
