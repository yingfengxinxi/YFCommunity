package com.wr.estate.entity.vo;

import lombok.Data;

/**
 * 统一设备返回 设备名称 设备 编码
 *
 * @Date 2022/11/2 16:52
 * @Author DIANWEI
 **/
@Data
public class DeviceCodeVO {
    /**
     * 设备名称
     **/
    private String deviceName;
    /**
     * 设备唯一编码
     **/
    private String indexCode;

}
