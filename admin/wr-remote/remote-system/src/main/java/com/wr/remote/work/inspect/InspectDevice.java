package com.wr.remote.work.inspect;

import lombok.Data;

import java.io.Serializable;

/**
 * 巡检工单-设备表
 * @Author: lvzy
 * @Date: 2022-12-06 16:02:17
 * @Desc:巡检工单-设备表
 */
@Data
public class InspectDevice implements Serializable {

    /**
     * 巡检工单id，表xccj_work.bus_inspect
     */
    private Long inspectId;

    private Long planId;

    /**
     * 设备类型：见类 DeviceConstants
     */
    private String deviceType;

    /**
     * 设备唯一标识，device_type对应的表
     */
    private String indexCode;

    /**
     * 巡检结果：0=未检，1=正常，2=异常
     */
    private String inspectResult;

    /**
     * 结果说明
     */
    private String resultInfo;

    private static final long serialVersionUID = 1L;
}