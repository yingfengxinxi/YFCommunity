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
 * @Date 2024/4/10 16:39
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("来访记录")
@TableName(value = "xccj_device.bus_visiting_record")
public class VisitingRecord extends BaseEntity {

    private String id;
    /**
     * 社区id，表xccj_community.bus_community
     */
    private Long communityId;
    //	访客姓名
    @JSONField(name = "visitorName")
    private String visitorName;
    //访客性别1男2女
    @JSONField(name = "gender")
    private String gender;
    //访客证件类型111=身份证414=护照113=户口簿335=驾驶证131=工作证133=学生证990=其他
    @JSONField(name = "certificateType")
    private String certificateType;
    //访客证件号码
    @JSONField(name = "certificateNo")
    private String certificateNo;
    //访客联系电话
    @JSONField(name = "phoneNo")
    private String phoneNo;
    //访客车牌号
    @JSONField(name = "plateNo")
    private String plateNo;
    //访客头像
    @JSONField(name = "picUri")
    private String picUri;
    //与picUri配对输出的字段信息，用于获取访客记录中的图片接口的输入参数
    @JSONField(name = "svrIndexCode")
    private String svrIndexCode;
    //访客状态5：超期自动签离、6：已签离7：超期未签离8：已到达
    @JSONField(name = "visitorStatus")
    private String visitorStatus;
    //可用于访客在访客机登记的凭证， 或后期作为查询条件使用
    @JSONField(name = "verificationCode")
    private String verificationCode;
    //访客名单分组ID
    @JSONField(name = "visiorGroupId")
    private String visiorGroupId;
    //访客名单分组名称
    @JSONField(name = "visiorGroupName")
    private String visiorGroupName;
    //被访人ID
    @JSONField(name = "receptionistId")
    private String receptionistId;
    //被访人姓名
    @JSONField(name = "receptionistName")
    private String receptionistName;
    //被访人手机号
    private String receptionistPhoneNo;
    //被访人性别
    private String receptionistGender;
    //被访人证件类型
    private String receptionistCertificateType;
    //被访人证件号
    private String receptionistCertificateNo;
    //被访人组织编码，数字+字母字符串
    @JSONField(name = "receptionistCode")
    private String receptionistCode;
    //来访事由;
    @JSONField(name = "visitPurpose")
    private String visitPurpose;
    //来访时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitStartTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitStartTime;

    //离开时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "visitEndTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date visitEndTime;
    //动态二维码，将访客二维码内容转成二维码图片即可使用
    @JSONField(name = "qRCode")
    private String qRCode;
    //证件地址
    @JSONField(name = "certAddr")
    private String certAddr;
    //发证机关
    @JSONField(name = "certIssuer")
    private String certIssuer;
    //种族
    @JSONField(name = "nation")
    private String nation;
    //籍贯
    @JSONField(name = "birthplace")
    private String birthplace;
    //来访单位
    @JSONField(name = "visitorWorkUnit")
    private String visitorWorkUnit;
    //访客登记时的登记id，可以用来做访客签离。此id如访客登记返回的id一致
    @JSONField(name = "orderId")
    private String orderId;
    //访客地址
    @JSONField(name = "visitorAddress")
    private String visitorAddress;
    //来访人数
    @JSONField(name = "personNum")
    private Integer personNum;
    //预计来访时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "plannedStartTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date plannedStartTime;
    //预计离开时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "plannedEndTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date plannedEndTime;
    //卡号
    @JSONField(name = "cardNo")
    private String cardNo;
    @TableLogic
    private String delFlag;
    @TableField(exist = false)
    private String visitStartStartDate;
    @TableField(exist = false)
    private String visitStartEndDate;

    @TableField(exist = false)
    private String visitEndStartDate;
    @TableField(exist = false)
    private String visitEndEndDate;

}
