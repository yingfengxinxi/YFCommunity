package com.wr.remote.estate.customer.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 11:25:18
 * @Desc: 装修类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("装修办理表")
@TableName(value = "xccj_community.bus_decorate")
public class BusDecorate extends BaseEntity {

    private static final long serialVersionUID = -420508548893332238L;

    /**
     * 主键
     */
    private String id;

    //工单编号
    private String no;
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

    //装修进度状态见字典表 dictType:renovation_audit_status
    private String renovationAuditStatus;
    /**
     * 计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;
    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;

    //装修项目1、改造部分线路2、隔墙改动3、餐厅改动4、阳台包封（按要求制作安装）5、水管改造6、卫生间改造7、吊顶8、空调（挂壁机、柜机、中央空调）9、其他
    private String project;
    private String projectItem;
    //装修项目其他备注
    private String remarks;
    /**
     * 产权附件路径
     */
    private String warrant;
    //身份证正面
    private String cardFont;

    //身份证反面
    private String cardBack;
    //设计方案
    private String designScheme;
    //装修合同
    private String decorationContract;
    //其他附件
    private String other;
    //装修单位
    private String companyName;
    //负责人
    private String personName;
    //负责人联系方式
    private String personPhone;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    @TableLogic
    private String delFlag;

    //违规记录内容
    @TableField(exist = false)
    private String content;
    //图片
    @TableField(exist = false)
    private String image;

}
