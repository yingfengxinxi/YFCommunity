package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:20:51
 * @Desc: 视频预警分析结果
 */
@Data
public class VideoResult implements Serializable {
    private static final long serialVersionUID = -358461973153319683L;

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

    /** 设备编号，平台关联的编码 */
    private String deviceIndexCode;

    /** 监控点编码，平台关联的编码 */
    private String cameraIndexCode;

    /** 通道名称 */
    private String channelName;

    /** 监控点安装地址 */
    private String cameraAddress;

    /** 监控点所在经度 */
    private String longitude;

    /** 监控点所在纬度 */
    private String latitude;

    /** 图片服务编号 */
    private String imageServerCode;

    /** 背景图URL */
    private String imageUrl;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;
}
