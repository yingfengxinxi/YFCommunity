package com.wr.api.estate.entity.vo.application.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 13:20:26
 * @Desc: TenantVo
 */
@Data
public class TenantVo implements Serializable {


    private static final long serialVersionUID = -6743771346702910150L;
    /**
     * 主键
     */
    private Long tenantId;
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
    private Long ownerId;
    /**
     * 账号id，表bus_account
     */
    private Long accountId;

    /**
     * 租客姓名
     */
    private String tenantName;
    /**
     * 手机号
     */
    private String tenantPhone;
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

    //与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客
    private String ownerRelation;
    //交房时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date delivery_time;
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

    //租赁结束时间
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseEndTime;
    //租赁开始时间
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseStartTime;

    //押金付款时间
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date depositPayTime;
    //租赁总金额
    private BigDecimal totalMoney;
    //押金金额
    private BigDecimal deposit;
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

    /*年龄*/
    private Integer age;

    /**
     * 小区，表bus_community
     */
    private String communityName;
    /**
     * 楼栋，表bus_building
     */
    private String buildingName;
    /**
     * 单元，表bus_unit
     */
    private String unitName;
    /**
     * 当前所住房间，表bus_room
     */
    private String roomName;
    /**
     * 车位编号
     */
    private String carportNo;
    /**
     * 车牌编号
     */
    private String plateNo;

    /**
     * 业主姓名
     */
    private String ownerName;


    /**
     * 手机号
     */
    private String ownerPhone;

    /**
     * 房租状态 0=未售，1=已售，2=入住，3=装修，4=已拿房，5=整租，6=合租，7=群租
     */
    private String roomStatus;

    /**
     * 人员类型
     */
    private String personType;
}
