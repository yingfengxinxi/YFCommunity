package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-15 15:59:21
 * @Desc:
 */
@Data
public class DeviceDetailVO implements Serializable {

    private static final long serialVersionUID = -8213157787079921867L;
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

    /**
     * 巡检结果
     */
    private String inspectResult;

    /**
     * 结果说明
     */
    private String resultIfo;

}
