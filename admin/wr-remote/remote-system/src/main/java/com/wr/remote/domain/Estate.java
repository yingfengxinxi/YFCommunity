package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  物业信息管理实体类
 * @Date 2022/9/20 9:00
 * @Author DIANWEI
 **/
@Data
public class Estate extends BaseEntity {
    /**
     * 物业信息ID
     */
    @PrimaryKey
    private Long estateId;
    /**
     * 父级物业ID
     */
    @ParentKey
    private Long parentId;
    /**
     * 祖籍列表
     */
    private String ancestors;
    /**
     * 物业名称
     */
    private String estateName;
    /**
     * 关联部门id，表xccj_estate_sys.sys_dept,dept_type=10
     */
    private Long deptId;
    /**
     * 机构编码
     */
    private String estateCode;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 服务热线
     */
    private String hotline;
    /**
     * 法人代表
     */
    private String legal;
    /**
     * 登记状态:0=存续（在营、开业、在册）、1=在业、2=吊销、3=注销、4=迁入、5=迁出、6=停业、7=清算
     */
    private String registerStatus;
    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundDate;
    /**
     * 注册资本（万）
     */
    private BigDecimal registerCapital;
    /**
     * 实缴资本
     */
    private BigDecimal actualCapital;
    /**
     * 核准日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approveDate;
    /**
     * 工商注册号
     */
    private String businessCode;
    /**
     * 纳税人识别号
     */
    private String taxpayerCode;
    /**
     * 企业类型:0=有限责任公司、1=无限责任公司、2=两合公司、3=股份有限公司、4=股份两合公司
     */
    private String firmType;
    /**
     * 营业期限（开始）
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodBegin;
    /**
     * 营业期限（结束），为空则无固定期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodEnd;
    /**
     * 纳税人资质
     */
    private String taxpayerQualify;
    /**
     * 所属地区
     */
    private String areaName;
    /**
     * 登记机关
     */
    private String registerOffice;
    /**
     * 人员规模
     */
    private String staffSize;
    /**
     * 参保人数
     */
    private Integer insuredNum;
    /**
     * 曾用名
     */
    private String formerName;
    /**
     * 注册地址
     */
    private String registerAddress;
    /**
     * 历史服务
     */
    private String historyServer;
    /**
     * 简介
     */
    private String brief;
    /**
     * 营业范围
     */
    private String businessScope;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
}
