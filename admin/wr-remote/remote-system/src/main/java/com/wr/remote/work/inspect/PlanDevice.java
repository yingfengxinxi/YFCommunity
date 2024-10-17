package com.wr.remote.work.inspect;

import lombok.Data;

import java.io.Serializable;

/**
 * 巡检计划-设备表
 * @Author: lvzy
 * @Date: 2022-12-07 10:26:06
 * @Desc:巡检计划-设备表
 */
@Data
public class PlanDevice implements Serializable {
    /**
    * 巡检计划id，表xccj_work.bus_inspect_plan
    */
    private Long planId;

    /**
    * 设备类型：见类 DeviceConstants
    */
    private String deviceType;

    /**
    * 设备唯一标识，device_type对应的表
    */
    private String indexCode;

    private static final long serialVersionUID = 1L;
}