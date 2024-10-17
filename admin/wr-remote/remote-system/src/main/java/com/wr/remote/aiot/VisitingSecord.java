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
 * @Date 2024/4/7 19:10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("访客记录")
@TableName(value = "xccj_device.bus_visiting_secord")
public class VisitingSecord extends BaseEntity {

    private String id;
    //门禁点id
    private String doorIndexCode;
    //小区id
    private Long communityId;
    //访客记录id
    @JSONField(name = "orderId")
    private String orderId;
    //访客姓名
    @JSONField(name = "visitorName")
    private String visitorName;
    //访客性别(1: 男2: 女)
    @JSONField(name = "visitorSex")
    private Integer visitorSex;
    //	访客分组id
    @JSONField(name = "visitorGroupId")
    private String visitorGroupId;
    //	访客人id
    @JSONField(name = "beVisitPersonId")
    private String beVisitPersonId;
    //	被访问姓名
    @JSONField(name = "beVisitPersonName")
    private String beVisitPersonName;
    //访客卡号
    @JSONField(name = "cardNum")
    private String cardNum;
    //卡类型,0：身份证号；1：身份证序列号；2：IC卡；3：二维码：4：车牌号码
    @JSONField(name = "cardType")
    private Integer cardType;
    //卡设备indexCode
    @JSONField(name = "deviceIndexCode")
    private String deviceIndexCode;
    //设备类型,1：门禁设备；2：梯控设备；3：可视对讲设备；4：停车场
    @JSONField(name = "deviceType")
    private Integer deviceType;
    //设备描述
    @JSONField(name = "deviceDesc")
    private String deviceDesc;
    //事件名称
    @JSONField(name = "eventName")
    private String eventName;
    //事件消息唯一标识
    @JSONField(name = "eventId")
    private String eventId;
    //访客手机号
    @JSONField(name = "phoneNum")
    private String phoneNum;
    //访客车牌号
    @JSONField(name = "carNumber")
    private String carNumber;
    //	访客证件类型：111身份证，335驾驶证，131工作证，133学生证，414护照，990其他
    @JSONField(name = "identityId")
    private Integer identityId;
    //	访客证件号
    @JSONField(name = "identityNum")
    private String identityNum;
    //访客抓拍头像照片,图片的相对URL
    @JSONField(name = "visitorPhotoUri")
    private String visitorPhotoUri;
    //	访客证件照片,图片的相对URL
    @JSONField(name = "identityPhotoUri")
    private String identityPhotoUri;
    //	访客头像对应图片服务器code
    @JSONField(name = "visitorSvrCode")
    private String visitorSvrCode;
    //证件照对应图片服务器code
    @JSONField(name = "identitySvrCode")
    private String identitySvrCode;
    //事件时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "eventTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    //	访客预计来访时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitStartTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitStartTime;
    //访客预计离开时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitEndTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitEndTime;
    //	访客登记时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitRegisterTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitRegisterTime;

    //访客签离时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitRealEndTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitRealEndTime;
    @TableLogic
    private String delFlag;

    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

}
