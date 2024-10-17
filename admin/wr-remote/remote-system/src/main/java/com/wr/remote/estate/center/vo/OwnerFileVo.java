package com.wr.remote.estate.center.vo;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:06:19
 * @Desc: 业主 excel
 */
@Data
public class OwnerFileVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Excel(name = "姓名")
    private String ownerName;

    @Excel(name = "手机号码")
    private String ownerPhone;
    @Excel(name = "身份证号码")
    private String cardNo;
    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    private String gender;

    @Excel(name = "提交方式", readConverterExp = "0=物业注册,1=手机注册")
    private String dataSource;
    @Excel(name = "所属小区")
    private String communityName;
    @Excel(name = "所属楼宇")
    private String buildingName;
    @Excel(name = "所属单元")
    private String unitName;
    @Excel(name = "所属房间")
    private String roomName;
    @Excel(name = "与户主的关系", readConverterExp = "0=本人,1=配偶,2=父女,3=子女,4=其它,5=租客")
    private String ownerRelation;
    @Excel(name = "户籍地址")
    private String domicileAddress;
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
     * 政治面貌：0=党员，1=团员，2=其他
     */
    private String politicsStatus;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
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
     * 个人介绍
     */
    private String selfIntro;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 房屋状态
     */
    private String roomStatus;
    /**
     * 交房时间
     */
    private String deliveryTime;

    private Integer carNum;

    //是否出租
    private Integer space;
    //是否绑定房产
    private Integer roomSpace;
    //是否删除
    private Integer isShowDel;

    private String cardType;
    private String phone;
    private String isCat;
    private String isOutIn;
    private String name;
    private Integer count;
    private Long[] ids;
}
