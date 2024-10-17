package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:11:59
 * @Desc: 烟雾/火点检测分析结果
 */
@Data
public class SmokeFire implements Serializable {
    private static final long serialVersionUID = -3011054274699810348L;

    /** 主键 */
    private Long resultId;

    /** 预警事件主键 表xccj_community.bus_warn_event */
    private Long warnId;

    /** 视频预警id 表xccj_community.bus_video_data */
    private Long dataId;

    /**小区id*/
    private Long communityId;
    /**小区名称*/
    private String communityName;

    /** 报警抓拍图片URL */
    private String imageUrl;

    /** 可见光图片URL */
    private String visiblePicUrl;

    /** 图片服务编号 */
    private String imageServerCode;

    /** 设备编号，平台关联的编码 */
    private String deviceIndexCode;

    /** 监控点编码，平台关联的编码 */
    private String cameraIndexCode;

    /** 监控点安装地址 */
    private String cameraAddress;

    /** 火点检测信息-火点框宽度 */
    private String fireRegionWidth;

    /** 火点检测信息-火点框高度 */
    private String fireRegionHeight;

    /** 火点检测信息-最高温度点坐标x */
    private String fireHighestX;

    /** 火点检测信息-最高温度点坐标y */
    private String fireHighestY;

    /** 火点检测信息-绝对高度-海拔 */
    private String fireElevation;

    /** 火点检测信息-绝对高度-方位 */
    private String fireAzimuth;

    /** 火点检测信息-绝对高度-绝对缩放 */
    private String fireZoom;

    /** 火点检测信息-温度单位:celsius-摄氏度,fahrenheit-华氏度,kelvin-开尔文 */
    private String temperatureUnit;

    /** 火点检测信息-最大温度 */
    private String fireMaxTemperature;

    /** 火点检测信息-目标距离[100m ~ 10000m] */
    private String targetDistance;

    /** 火点检测信息-火点扫描等待模式 */
    private String fireScanWaitMode;

    /** 烟雾检测信息-烟雾框宽度 */
    private String smokeRegionWidth;

    /** 烟雾检测信息-烟雾框高度 */
    private String smokeRegionHeight;

    /** 烟雾检测信息-绝对高度-海拔 */
    private String smokeElevation;

    /** 烟雾检测信息-绝对高度-方位 */
    private String smokeAzimuth;

    /** 烟雾检测信息-绝对高度-绝对缩放 */
    private String smokeZoom;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;
}
