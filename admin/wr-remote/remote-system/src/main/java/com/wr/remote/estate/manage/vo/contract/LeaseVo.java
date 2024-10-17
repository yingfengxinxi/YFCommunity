package com.wr.remote.estate.manage.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.estate.manage.contract.RentContract;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-10-12 17:19:03
 * @Desc: 租房合同VO类
 */
@Data
public class LeaseVo extends RentContract {

    private static final long serialVersionUID = 5081336028106665805L;
    /**
     * 合同编号
     */
    private Long contractId;
    /**
     * 租户信息
     */
    private String tenantName;
    /**
     * 租户手机号
     */
    private String tenantPhone;
    /**
     * 业主信息
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String ownerPhone;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 所属楼栋
     */
    private String buildingName;
    /**
     * 所属单元
     */
    private String unitName;
    /**
     * 所属房间
     */
    private String roomName;
    /**
     * 租金（元/月）
     */
    private BigDecimal monthRent;
    //押金
    private BigDecimal deposit;
    //押金付款时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date depositPayTime;
    //租赁总价
    private BigDecimal totalMoney;
    /**
     * 合同生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStart;
    /**
     * 合同截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractEnd;
    /**
     * 租赁方式
     */
    private Long ownerId;
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 小区id
     */
    private Long communityId;

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
     * 宗教信仰
     */
    private String religion;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 民族
     */
    private String nation;
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
     * 个人介绍
     */
    private String selfIntro;

    /**
     * 合同有效天数(库中无该字段)
     */
    private Long contractDay;

    /**
     * 租赁月数(库中无该字段)
     */
    private Long contractMoth;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 楼宇主键
     */
    private Long buildingId;
    /**
     * 单元主键
     */
    private Long unitId;
    /**
     * 房屋主键
     */
    private Long roomId;

    /**
     * 租客主键
     */
    private Long tenantId;

    /**
     * 房屋状态：
     */
    private Long roomStatus;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同有效天数
     */
    private Long leaseDay;

    /**
     * 合同有效月数
     */
    private Long leaseMonth;


    /**
     * 租赁方式
     */
    private String rentType;

    private Long estateId;

}
