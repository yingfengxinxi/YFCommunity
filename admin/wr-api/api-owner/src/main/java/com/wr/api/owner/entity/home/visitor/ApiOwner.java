package com.wr.api.owner.entity.home.visitor;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Author: lusu
 * @Date: 2022-11-16 20:34:35
 * @Desc: 业主档案表
 */

@Data
public class ApiOwner implements Serializable {
    private static final long serialVersionUID = 6104098472318475244L;
    /**
     * 主键
     */
    private Long ownerId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;

    /**
     * 单元id，表bus_unit
     */
    private Long unitId;

    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;

    /**
     * 户主id
     */
    private Long parentId;

    /**
     * 账号id，表bus_account
     */
    private Long accountId;

    /**
     * 产权证明书
     */
    private String licenseUrl;

    /**
     * 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客
     */
    private String ownerRelation;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 手机号
     */
    private String ownerPhone;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;

    /**
     * 证件类型：见数据字典 sys_certificate
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String cardNo;

    /**
     * 证件照（正面）
     */
    private String cardFont;

    /**
     * 证件照（反面）
     */
    private String cardBack;

    /**
     * 人脸URL
     */
    private String faceUrl;

    /**
     * 户籍地址
     */
    private String domicileAddress;

    /**
     * 政治面貌：0=党员，1=团员，2=其他
     */
    private String politicsStatus;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 宗教信仰
     */
    private String religion;

    /**
     * 文化程度
     */
    private String eduLevel;

    /**
     * 兵役状况
     */
    private String military;

    /**
     * 婚姻状况：0=未婚，1=已婚
     */
    private String marriageStatus;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 人口类型：0=户籍人口，1=常驻人口，2=暂住人口
     */
    private String population;

    /**
     * 职业
     */
    private String career;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 数据来源：0=物业注册，1=手机注册
     */
    private String dataSource;

    /**
     * 个人介绍
     */
    private String selfIntro;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 小区房号
     */
    private String roomName;
    /**
     * 小区楼栋名称
     */
    private String buildingName;
    /**
     * 小区区号
     */
    private String unitName;




}