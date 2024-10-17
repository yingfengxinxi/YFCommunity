package com.wr.remote.aiot;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 13:30:23
 * @Desc: 设备门禁信息
 */
@Data
public class Access extends BaseDevice {
    private static final long serialVersionUID = 5310392102492059811L;

    /** 主键id */
    private Long accessId;

    /** 资源名称 */
    private String accessName;

    /** 门禁设备类型编码 */
    private String devTypeCode;

    /** 门禁设备类型型号 */
    private String devTypeDesc;

    /** 门禁设备IP */
    private String accessIp;

    /** 门禁设备端口 */
    private String accessPort;

    /** 接入协议 */
    private String treatyType;

    /** 设备能力集 */
    private String capability;

    /** 设备卡容量 */
    private Integer cardCapacity;

    /** 指纹容量 */
    private Integer fingerCapacity;

    /** 人脸容量 */
    private Integer faceCapacity;

    /** 门容量 */
    private Integer doorCapacity;

    /** 版本号 */
    private String dataVersion;

    /** 设备序列号 */
    private String devSerialNum;

    /** 门禁类型：0=进 ，1=出 */
    private String inoutType;

    /** 设备二维码 */
    private String deviceQr;

    /** 关联的门禁点id */
    private Long doorId;
    /** 门禁点名称 */
    private String doorName;
    @TableField(exist = false)
    private String doorIndexCode;

}
