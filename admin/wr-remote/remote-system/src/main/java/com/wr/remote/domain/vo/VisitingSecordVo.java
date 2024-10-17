package com.wr.remote.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.aiot.VisitingSecord;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:06
 */
@Data
public class VisitingSecordVo extends VisitingSecord {

    /**
     * 门序号
     */
    private Integer doorSerial;
    /**
     * 通道类型
     */
    private String channelType;
    //设备状态
    private Integer online;
    //所属小区
    private String communityName;
    //楼栋
    private String buildingName;
    //单元楼
    private String unitName;
    //门禁名称
    private String doorName;
    //门禁点地址
    private String address;

}
