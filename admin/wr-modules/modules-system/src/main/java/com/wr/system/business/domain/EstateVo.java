package com.wr.system.business.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.business.annotation.ChildrenKey;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.SysDept;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 物业信息管理VO
 * @Date 2022/9/20 9:56
 * @Author DIANWEI
 **/
@Data
@TableName("xccj_community.bus_estate")
public class EstateVo extends BaseEntity {

    private static final long serialVersionUID = -5212194246893099173L;
    /**
     * 物业信息对象
     */
    @TableField(exist = false)
    private SysDept sysDept;

    /** 上一级物业名称 */
   @Excel(name = "上级公司")
    private String parentName;

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
   @Excel(name = "集团/公司名称")
    private String estateName;
    /**
     * 关联部门id，表xccj_estate_sys.sys_dept,dept_type=10
     */

    private Long deptId;
    /**
     * 机构编码
     */
   @Excel(name = "机构编码")
    private String estateCode;
    /**
     * 统一社会信用代码
     */
   @Excel(name = "统一社会信用代码")
    private String creditCode;
    /**
     * 服务热线
     */
   @Excel(name = "服务热线")
    private String hotline;
    /**
     * 法人代表
     */
   @Excel(name = "法人代表")
    private String legal;
    /**
     * 登记状态:0=存续（在营、开业、在册）、1=在业、2=吊销、3=注销、4=迁入、5=迁出、6=停业、7=清算
     */
   @Excel(name = "登记状态")
    private String registerStatus;
    /**
     * 成立日期
     */
   @Excel(name = "成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date foundDate;
    /**
     * 注册资本（万）
     */
   @Excel(name = "注册资本（万）")
    private BigDecimal registerCapital;
    /**
     * 实缴资本
     */
   @Excel(name = "实缴资本（万）")
    private BigDecimal actualCapital;
    /**
     * 核准日期
     */
   @Excel(name = "核准日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approveDate;
    /**
     * 工商注册号
     */
   @Excel(name = "工商注册号")
    private String businessCode;
    /**
     * 纳税人识别号
     */
   @Excel(name = "纳税人识别号")
    private String taxpayerCode;
    /**
     * 企业类型:0=有限责任公司、1=无限责任公司、2=两合公司、3=股份有限公司、4=股份两合公司
     */
   @Excel(name = "企业类型")
    private String firmType;
    /**
     * 营业期限（开始）
     */
   @Excel(name = "营业期限（开始）")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodBegin;
    /**
     * 营业期限（结束），为空则无固定期限
     */
   @Excel(name = "营业期限（结束）")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodEnd;
    /**
     * 纳税人资质
     */
   @Excel(name = "纳税人资质")
    private String taxpayerQualify;
    /**
     * 所属地区
     */
   @Excel(name = "所属地区")
    private String areaName;
    /**
     * 登记机关
     */
   @Excel(name = "登记机关")
    private String registerOffice;
    /**
     * 人员规模
     */
   @Excel(name = "人员规模")
    private String staffSize;
    /**
     * 参保人数
     */
   @Excel(name = "参保人数")
    private Integer insuredNum;
    /**
     * 曾用名
     */
   @Excel(name = "曾用名")
    private String formerName;
    /**
     * 注册地址
     */
   @Excel(name = "注册地址")
    private String registerAddress;
    /**
     * 历史服务
     */
   @Excel(name = "历史服务")
    private String historyServer;
    /**
     * 简介
     */
   @Excel(name = "简介")
    private String brief;
    /**
     * 营业范围
     */
   @Excel(name = "经营范围")
    private String businessScope;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;

    /** 子物业 */
    @ChildrenKey
    @TableField(exist = false)
    private List<EstateVo> children = new ArrayList<EstateVo>();

   @Excel(name = "显示排序")
    @TableField(exist = false)
    private Integer orderNum;

   @Excel(name = "物业公司状态")
    @TableField(exist = false)
    private String status;
}
