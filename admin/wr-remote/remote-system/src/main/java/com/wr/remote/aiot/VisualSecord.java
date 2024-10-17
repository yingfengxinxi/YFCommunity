package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
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
 * @Date 2024/4/14 23:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("单元楼人员出行记录")
@TableName(value = "xccj_device.bus_visual_secord")
public class VisualSecord extends BaseEntity {

    //	主键id
    private String id;
    //小区id
    private Long communityId;
    //事件唯一编号
    @JSONField(name = "eventId")
    private String eventId;
    //事件类型 983297=业主密码开锁 —密码开锁（87）	;983298=劫持密码开锁 —劫持开锁（87）;983299=刷卡开锁;983300=室内机开锁 —户主开锁（87）;983301=中心平台开锁;983307=蓝牙开锁;983308=二维码开锁;983309=人脸开锁;983310=指纹开锁;
    @JSONField(name = "eventType")
    private String eventType;
    //事件名称
    @JSONField(name = "eventName")
    private String eventName;
    //事件产生时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "eventTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    //人员id
    @JSONField(name = "personId")
    private String personId;
    //卡号
    @JSONField(name = "cardNo")
    private String cardNo;
    //人员姓名
    @JSONField(name = "personName")
    private String personName;
    //性别1=男2=女
    private String gender;
    //手机号
    private String phoneNo;
    //证件类型
    private String certificateType;
    //证件号码
    private String certificateNo;
    //组织编码
    @JSONField(name = "orgIndexCode")
    private String orgIndexCode;
    //组织名称
    @JSONField(name = "orgName")
    private String orgName;
    //设备名称
    @JSONField(name = "deviceName")
    private String deviceName;
    //设备编号
    @JSONField(name = "deviceIndexCode")
    private String deviceIndexCode;
    //图片存储服务的唯一标识, 可以通过获取出入事件的图片接口获取图片数据
    @JSONField(name = "svrIndexCode")
    private String svrIndexCode;
    //抓拍图片地址，获取出入事件的图片接口的必要参数
    @JSONField(name = "picUrl")
    private String picUrl;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
