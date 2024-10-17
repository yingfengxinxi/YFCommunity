package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("门禁点出入行事件信息")
@TableName(value = "xccj_device.bus_door_event")
public class DoorEvent extends BaseEntity {

    private String id;

    //事件ID，唯一标识这个事件
    @JSONField(name = "eventId")
    private String eventId;
    //事件名称
    @JSONField(name = "eventName")
    private String eventName;

    //	事件时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "eventTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    //人员唯一编码
    @JSONField(name = "personId")
    private String personId;
    //卡号
    @JSONField(name = "cardNo")
    private String cardNo;
    //人员名称
    @JSONField(name = "personName")
    private String personName;
    //人员所属组织编码
    @JSONField(name = "orgIndexCode")
    private String orgIndexCode;
    //人员所属组织名称
    @JSONField(name = "orgName")
    private String orgName;
    //门禁点名称
    @JSONField(name = "doorName")
    private String doorName;
    //	门禁点编码
    @JSONField(name = "doorIndexCode")
    private String doorIndexCode;
    //门禁点所在区域编码
    @JSONField(name = "doorRegionIndexCode")
    private String doorRegionIndexCode;
    //抓拍图片地址，通过接口获取门禁事件的图片接口获取门禁事件的图片数据
    @JSONField(name = "picUri")
    private String picUri;
    //图片存储服务的唯一标识
    @JSONField(name = "svrIndexCode")
    private String svrIndexCode;
    //	事件类型，参考附录D.2.1.1 门禁事件类型
    @JSONField(name = "eventType")
    private String eventType;
    //	进出门类型1=进0=出-1=未知要求：进门读卡器拨码设置为出门读卡器拨码设置为2
    @JSONField(name = "inAndOutType")
    private String inAndOutType;
    //	读卡器IndexCode
    @JSONField(name = "readerDevIndexCode")
    private String readerDevIndexCode;
    //	读卡器名称
    @JSONField(name = "readerDevName")
    private String readerDevName;
    //	控制器设备IndexCode
    @JSONField(name = "devIndexCode")
    private String devIndexCode;
    //	控制器名称
    @JSONField(name = "devName")
    private String devName;
    //	身份证图片uri，它是一个相对地址，可以通过获取门禁事件的图片接口，获取到图片的数据
    @JSONField(name = "identityCardUri")
    private String identityCardUri;
    //	事件入库时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "receiveTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    //工号
    @JSONField(name = "jobNo")
    private String jobNo;
    //学号
    @JSONField(name = "studentId")
    private String studentId;
    //证件号码
    @JSONField(name = "certNo")
    private String certNo;
    @TableLogic
    private String delFlag;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

}
