package com.wr.api.owner.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-11 09:49:11
 * @Desc:
 */
@Data
public class MyinfoDTO {
    /** 主键*/
    private Long ownerId;

    /** 小区id，表bus_community*/
    private Long communityId;
    /** 小区名称*/
    private String communityName;

    /** 楼栋id，表bus_building*/
    private Long buildingId;
    /** 楼栋名称*/
    private String buildingName;

    /** 单元id，表bus_unit*/
    private Long unitId;
    /** 单元名称*/
    private String unitName;

    /** 当前所住房间id，表bus_room*/
    private Long roomId;

    /** 房产id，表bus_house*/
    private Long houseId;

    /** 房间名称*/
    private String roomName;
    /** 业主默认房产 */
    private Long defaultRoom;

    /** 房间名称*/
    private String roomStatus;

    /** 户主id*/
    private Long parentId;

    /** 账号id，表bus_account*/
    private Long accountId;

    /** 产权证明书*/
    private String licenseUrl;

    /** 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客*/
    private String ownerRelation;

    /** 业主姓名*/
    private String ownerName;

    /** 手机号*/
    private String ownerPhone;

    /** 性别：0=男，1=女，2=未知*/
    private String gender;

    /** 证件类型：见数据字典 sys_certificate*/
    private String cardType;

    /** 证件号码*/
    private String cardNo;

    /** 证件照（正面）*/
    private String cardFont;

    /** 证件照（反面）*/
    private String cardBack;

    /** 人脸URL*/
    private String faceUrl;

    /** 户籍地址*/
    private String domicileAddress;

    /** 政治面貌：0=党员，1=团员，2=其他*/
    private String politicsStatus;

    /** 民族*/
    private String nation;

    /** 籍贯*/
    private String nativePlace;

    /** 宗教信仰*/
    private String religion;

    /** 文化程度*/
    private String eduLevel;

    /** 兵役状况*/
    private String military;

    /** 婚姻状况：0=未婚，1=已婚*/
    private String marriageStatus;

    /** 国籍*/
    private String nationality;

    /** 人口类型：0=户籍人口，1=常驻人口，2=暂住人口*/
    private String population;

    /** 职业*/
    private String career;

    /** QQ号*/
    private String qqNumber;

    /** 邮箱*/
    private String email;

    /** 数据来源：0=物业注册，1=手机注册*/
    private String dataSource;

    /** 个人介绍*/
    private String selfIntro;

    /** 审核状态：0=审核中，1=通过，2=驳回*/
    private String auditStatus;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 驳回理由*/
    private String rejectReason;

    /** 创建人*/
    private String createBy;

    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改人*/
    private String updateBy;

    /** 修改人*/
    private String volunteer;
    private String dictLabel;
    private String dictValue;
    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 头像
     */
    private String avatar;

}
