package com.wr.remote.estate.center.vo;

import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/6 10:19
 */
@Data
public class OwnerVo {

    //业主id
    private Long id;
    //小区id
    private Long communityId;
    //业主名称
    private String ownerName;
    //业主手机号
    private String ownerPhone;
    //证件类型
    private String cardType;
    //证件号码
    private String cardNo;
    //身份证正面
    private String cardFont;
    //身份证反面
    private String cardBack;
    //性别0=男1=女2=未知
    private String gender;
    //与户主关系
    private String ownerRelation;
    //政治面貌
    private String politicsStatus;
    //紧急联系人
    private String emergencyContact;
    //紧急联系人电话
    private String emergencyContactPhone;
    //介绍
    private String selfIntro;
    //房产证明
    private String licenseUrl;
    //宗教信仰
    private String religion;
    //业主账号
    private Long accountId;
    //房间id
    private Long roomId;
    //业主id
    private Long ownerId;
}
