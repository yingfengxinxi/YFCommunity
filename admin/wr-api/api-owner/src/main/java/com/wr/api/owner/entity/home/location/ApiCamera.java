package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.io.Serializable;

/**
 * 监控预览查询条件
 * @Author RainCity
 * @Date 2023-05-17 16:00:24
 */
@Data
public class ApiCamera implements Serializable {
    private static final long serialVersionUID = -6661592910240194767L;

    /** 小区id */
    private Long communityId;

    /** 楼栋id */
    private Long buildingId;

    /** 单元id */
    private Long unitId;

    /** 主键 */
    private Long cameraId;

    /**设备唯一编码*/
    private String indexCode;

    /** 图片视频流取流协议 */
    private String protocol;
}
