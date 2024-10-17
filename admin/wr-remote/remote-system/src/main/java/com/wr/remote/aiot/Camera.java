package com.wr.remote.aiot;

import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 13:47:36
 * @Desc: 监控点信息
 */
@Data
public class Camera extends BaseDevice {
    private static final long serialVersionUID = 5449228914471116322L;

    /** 主键 */
    private Long cameraId;

    /**资源名称*/
    private String cameraName;

    /**监控点类型 枪机0 半球1 快球2 带云台枪机3*/
    private Integer cameraType;

    /**通道号，为级联监控点时，该字段为空；本级监控点时，该字段非空*/
    private Integer chanNum;

    /**能力集*/
    private String capability;

    /**通道子类型 模拟通道analog 数字通道digital 镜像通道mirror 录播通道record 零通道zero*/
    private String channelType;

    /**解码模式*/
    private String decodeTag;

    /**监控点关联对讲唯一标志*/
    private String cameraRelateTalk;

    /**传输协议 0：UDP 1：TCP*/
    private Integer transType;

    /**接入协议*/
    private String treatyType;

    /**录像存储位置*/
    private String recordLocation;

    /**所属DAC编号*/
    private String dacIndexCode;


    /**设备类型：见字典 sys_camera_type*/
    private String deviceType;

    /** 设备二维码 */
    private String deviceQr;

}
