package com.wr.remote.estate.center.vo;

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
@ApiModel("物业人员")
public class StaffVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8791029777113813284L;

    /**
     * 员工姓名
     */
    @Excel(name = "小区名称",prompt = "必填")
    private String communityName;

    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名",prompt = "必填")
    private String staffName;

    /**
     * 账号/手机号
     */
    @Excel(name = "手机号",prompt = "必填")
    private String staffPhone;



    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码",prompt = "必填")
    private String cardNo;
    /**
     * 个人健康：0=健康,1=不健康
     */
    @Excel(name = "个人健康", readConverterExp = "0=健康,1=不健康",combo = "健康,不健康",prompt = "必填")
    private String healthy;

    /**
     * 职业证书
     */
    @Excel(name = "职业证书")
    private String certUrl;
    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd",prompt = "必填(yyyy-MM-dd)")
    private Date entryTime;

    /**
     * 岗位：
     * 0=安防人员
     * 1=维修人员
     * 2=装修人员
     * 4=保洁人员
     * 6=巡查人员
     * 7=巡检人员
     * 8=抄表
     */
    @Excel(name = "岗位", readConverterExp = "0=安防人员,1=维修人员,2=装修人员,4=保洁人员,6=巡查人员,7=巡检人员,8=抄表人员",combo = "安防人员,维修人员,装修人员,保洁人员,巡查人员,巡检人员,抄表人员",separator = ",",prompt = "必填")
    private String workPost;


    /**
     * 学历
     */
    @Excel(name = "学历", readConverterExp = "0=小学,1=初中,2=高中,3=专科,4=本科,5=研究生,6=其他",combo = "小学,初中,高中,专科,本科,研究生,其他",prompt = "必填")
    private String education;



    /**
     * 工作资历(年)
     */
    @Excel(name = "工作资历(年)",prompt = "必填")
    private Integer workYear;


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

}
