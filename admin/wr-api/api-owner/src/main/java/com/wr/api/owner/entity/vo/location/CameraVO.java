package com.wr.api.owner.entity.vo.location;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author RainCity
 * @Date 2023-05-17 16:08:59
 */
@Data
public class CameraVO implements Serializable {
    private static final long serialVersionUID = 671689476928935884L;

    /** 主键 */
    private Long cameraId;

    /**设备唯一编码*/
    private String indexCode;

    /**资源名称*/
    private String cameraName;

    /**监控点类型 枪机0 半球1 快球2 带云台枪机3*/
    private Integer cameraType;

    /**设备类型：见字典 sys_camera_type*/
    private String deviceType;

    /** 设备二维码 */
    private String deviceQr;

    /**小区id*/
    private Long communityId;

    /** 楼栋id */
    private Long buildingId;

    /** 单元id */
    private Long unitId;

    /** 设备地址 */
    private String address;
}
