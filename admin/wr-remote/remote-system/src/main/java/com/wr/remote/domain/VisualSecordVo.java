package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/4/15 10:25
 */
@Data
public class VisualSecordVo {

    private String id;
    //设备状态
    private Integer online;
    //小区id
    private Long communityId;
    //小区名称
    private String communityName;
    //楼栋id
    private Long buildingId;
    //楼栋名称
    private String buildingName;
    //单元楼id
    private Long unitId;
    //单元楼
    private String unitName;
    //事件类型
    private String eventType;
    //事件名称
    private String eventName;
    //事件时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    //人员姓名
    private String personName;
    //性别
    private String gender;
    //证件类型
    private String certificateType;
    //证件号
    private String certificateNo;
    //手机号
    private String phoneNo;
    //设备名称
    private String deviceName;
    //事件开始时间
    private String startTime;
    //事件结束时间
    private String endTime;
}
