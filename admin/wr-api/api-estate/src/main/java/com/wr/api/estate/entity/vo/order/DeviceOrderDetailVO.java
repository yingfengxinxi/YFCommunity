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
public class DeviceOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备地址
     */
    private String deviceAddress;
}
