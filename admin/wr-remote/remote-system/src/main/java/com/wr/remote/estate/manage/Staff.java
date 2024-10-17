package com.wr.remote.estate.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 14:29:40
 * @Desc:员工列表实体类
 */
@Data
@ApiModel("物业人员管理")
public class Staff extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8791029777113813284L;
    /**
     * 主键
     */
    private Long staffId;

    /**
     * 账号id
     */
    private Long userId;

    /**
     * 小区id
     */
    private Long communityId;
    private String communityName;

    /**
     * 物业id
     */
    private Long estateId;


    private List<Long> staffIds;

    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名")
    private String staffName;

    /**
     * 账号/手机号
     */
    @Excel(name = "账号/手机号")
    private String staffPhone;

    /**
     * 性别：0=男,1=女 2=未知
     */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码")
    private String cardNo;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String avatar;

    /**
     * 类型：0=内部人员,1=入驻人员
     */
    private String staffType;

    /**
     * 岗位：0=安防人员,1=维修人员,2=装修人员,3=场馆人员,4=保洁人员,5=志愿者
     */
    @Excel(name = "岗位", readConverterExp = "0=安防人员,1=维修人员,2=装修人员,3=场馆人员,4=保洁人员,5=志愿者", separator = ",")
    private String workPost;

    /**
     * 个人健康：0=健康,1=不健康
     */
    @Excel(name = "个人健康", readConverterExp = "0=健康,1=不健康")
    private String healthy;

    /**
     * 学历
     */
    @Excel(name = "学历", readConverterExp = "0=小学,1=初中,2=高中,3=专科,4=本科,5=研究生,6=其他")
    private String education;

    //绑定栋楼
    private String buildingId;
    //
    private String buildingName;
    //员工二维码
    private String qrCodeBase64;

    /**
     * 职业证书
     */
    @Excel(name = "职业证书")
    private String certUrl;

    /**
     * 工作资历(年)
     */
    @Excel(name = "工作资历(年)")
    private Integer workYear;

    /**
     * 审核状态：0=审核中,1=通过,2=驳回
     */
    @Excel(name = "审核状态", readConverterExp = "0=审核中,1=通过,2=驳回")
    private String auditStatus;

    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryTime;

    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dimissionTime;

    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String address;

    /**
     * 个人介绍
     */
    @Excel(name = "个人介绍")
    private String staffIntro;

    /**
     * 删除标记：0=未删除；1=已删除
     */
    private String delFlag;

    private Long loginId;

    /**
     * 密码
     */
    private String password;

    /**
     * 在职状态
     */
    //@Excel(name = "在职状态",readConverterExp = "0=在职,1=离职")
    private String jobStatus;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;

}
