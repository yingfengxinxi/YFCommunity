package com.wr.remote.aiot;

import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 14:10:15
 * @Desc: 门禁点信息
 */
@Data
public class Door extends BaseDevice{
    private static final long serialVersionUID = 9204019188790833019L;

    /** 主键 */
    private Long doorId;

    /** 资源名称 */
    private String doorName;

    /** 门禁点编号 */
    private String doorNo;

    /** 门序号 */
    private Integer doorSerial;

    /** 通道类型 */
    private String channelType;

    /** 通道号 */
    private String channelNo;

    /** 接入协议 */
    private String treatyType;

    /** 一级控制器id */
    private String controlOneId;

    /** 二级控制器id */
    private String controlTwoId;

    /** 读卡器1 */
    private String readerInId;

    /** 读卡器2 */
    private String readerOutId;

    /** 门禁类型：0=进 ，1=出 */
    private String inoutType;

}
