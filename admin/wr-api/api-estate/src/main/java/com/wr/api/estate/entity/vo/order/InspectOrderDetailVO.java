package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 20:53:57
 * @Desc: 报事报修工单详情
 */
@Data
public class InspectOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 巡检类型
     */
    private String inspectType;

    /**
     * 订单来源
     */
    private String orderSource;

    /**
     * 来源工单
     */
    private String otherOrderNo;

    /**
     * 设备详情
     */
    private List<DeviceDetailVO> devices;

    /**
     * 巡检异常图片
     */
    private List<String> abnormalImages;
}
