package com.wr.remote.aiot;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 14:00:19
 * @Desc: 云+门禁控制器
 */
@Data
public class CloudAccess extends BaseEntity {
    private static final long serialVersionUID = 7384049104255467858L;

    /** 主键 */
    private Long accessId;

    /** 设备名称 */
    private String accessName;

    /** 控制器序列号/SN */
    private String accessSerial;

    /** 设备IP */
    private String accessIp;

    /** 设备的MAC地址 12个16进制的字符串 */
    private String accessMac;

    /** 固件详细版本 */
    private String accessVer;

    /** 读头A所接门禁点id */
    private Long readerIn;
    /** 读头A所接门禁点名称 */
    private String doorInName;

    /** 读头B所接门禁点id */
    private Long readerOut;
    /** 读头B所接门禁点名称 */
    private String doorOutName;

    /** 所属小区 */
    private Long communityId;
    /**小区名称*/
    private String communityName;

    /**安装位置*/
    private String installLocation;

    /** 设备二维码 */
    private String deviceQr;

    /** 厂商==> Cloud+：云+ */
    private String manufacturer;

    /** 删除状态 0 未删除 1 已删除*/
    private String delFlag;

    private Long[] ids;
}
