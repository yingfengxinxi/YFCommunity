package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:28:12
 * @Desc: 可视对讲设备
 */
@Data
public class Visual extends BaseDevice{
    private static final long serialVersionUID = -857437841790670313L;

    /** 主键id */
    private Long visualId;

    /** 资源名称 */
    @JSONField(name = "name")
    private String visualName;

    /** ip */
    private String visualIp;

    /** 端口 */
    private String visualPort;

    /** 资源类型，
     * visDeviceInDoor：可视对讲室内机，
     * visDeviceOutDoor：可视对讲门口机，
     * visDeviceWallDoor：可视对讲围墙机，
     * visDeviceManager：可视对讲管理机
     */
    @JSONField(name = "resourceType")
    private String resourceType;

    /** 设备长号 */
    @JSONField(name = "longNum")
    private String longNum;

    /** 设备驱动 */
    private String deviceKey;

    /** 设备系列 */
    @JSONField(name = "deviceType")
    private String deviceType;

    /** 设备型号 */
    @JSONField(name = "deviceModel")
    private String deviceModel;

    /** 接入协议 */
    @JSONField(name = "treatyType")
    private String treatyType;

    /** 安装位置 */
    @JSONField(name = "installLocation")
    private String installLocation;

    /** 所属网域 */
    private String netZoneId;

    /** 版本号 */
    @JSONField(name = "dataVersion")
    private String dataVersion;

    /** 设备子类型（7小类） */
    private Integer subModel;

    /** 设备卡容量 */
    private Integer cardCapacity;

    /** 指纹容量 */
    private Integer fingerCapacity;

    /** 人脸容量 */
    private Integer faceCapacity;

    /** 设备支持的能力 */
    private String capability;

    /** 设备的序列号 */
    @JSONField(name = "devSerialNum")
    private String devSerialNum;

    /** 设备验证码 */
    private String visIdentifyingCode;

    /** 设备二维码 */
    private String deviceQr;

    //主动设备编号
    @JSONField(name = "deviceCode")
    private String deviceCode;

    //所属区域
    @JSONField(name = "regionIndexCode")
    private String regionIndexCode;

    //所属区域路径,以@符号分割，包含本节点
    @JSONField(name = "regionPath")
    private String regionPath;

    //0非主管理机
    @JSONField(name = "visMainManager")
    private String visMainManager;

    //描述
    @JSONField(name = "description")
    private String description;

}
