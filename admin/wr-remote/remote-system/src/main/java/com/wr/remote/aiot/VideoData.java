package com.wr.remote.aiot;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:17:56
 * @Desc:  视频预警事件扩展信息
 */
@Data
public class VideoData implements Serializable {
    private static final long serialVersionUID = 6758582091991130496L;

    /** 主键 */
    private Long dataId;

    /** 预警事件主键，表xccj_community.bus_warn_event */
    private Long warnId;

    /**小区id*/
    private Long communityId;
    /**小区名称*/
    private String communityName;

    /** 数据模型标识 */
    private String dataType;

    /** 数据接收时间 */
    private String recvTime;

    /** 数据发送时间 */
    private String sendTime;

    /** 数据触发时间 */
    private String dateTime;

    /** 设备的IP地址 */
    private String ipAddress;

    /** 设备端口号 */
    private String portNo;

    /** 设备通道号  */
    private String channelId;

    /** 事件类型 */
    private String eventType;

    /** 事件类型名称 */
    private String eventDescription;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    /** 视频预警分析结果 */
    List<VideoResult> videoResultList = Lists.newArrayList();
    /** 烟雾/火点检测分析结果 */
    List<SmokeFire> smokeFireList = Lists.newArrayList();
}
