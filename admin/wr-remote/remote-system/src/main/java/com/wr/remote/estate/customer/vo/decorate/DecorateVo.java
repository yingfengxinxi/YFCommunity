package com.wr.remote.estate.customer.vo.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.customer.decorate.Worker;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:40:57
 * @Desc: 装修列表
 */
@Data
@ApiModel("装修工单Vo")
public class DecorateVo{

    private static final long serialVersionUID = -1875527112323569432L;
    /**
     * 主键
     */
    private String id;
    //工单编号
    @Excel(name = "工单编号")
    private String no;
    //装修公司
    private String companyName;
    //房屋坐落
    @Excel(name = "房屋地址")
    private String houseAddress;
    /**
     * 计划装修开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "装修开始时间",dateFormat = "yyyy-MM-dd")
    private Date planStart;


    /**
     * 计划装修结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "装修结束时间",dateFormat = "yyyy-MM-dd")
    private Date planEnd;
    @Excel(name = "最近执行人")
    private String updateBy;
    //装修进度
    @Excel(name = "装修进度",readConverterExp = "0=办理装修,1=水电阶段,2=瓦工阶段,3=木工阶段,4=油漆工阶段,5=装修完成")
    private String renovationAuditStatus;
    //名称
    private String renovationAuditStatusName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次巡查时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    //工单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //当前登录用户账号类型
    @TableField(exist = false)
    private String userType;
    /**
     * 业主姓名
     */
    private String ownerName;
    /**
     * 联系方式
     */
    private String ownerPhone;
    private String startTime;
    private String endTime;

    //身份证
    private String cardNo;
    /**
     * 所属楼栋
     */
    private String buildingName;
    /**
     * 单元号
     */
    private String unitName;
    /**
     * 门牌号
     */
    private String roomName;
    //装修图片
    private String decorateImage;
    private List<String> decorateImages;
    /**
     * 装修人数
     */
    private Integer decorationNumber;

    /**
     * 装修押金
     */
    private BigDecimal deposit;

    @TableField(exist = false)
    private List<String> renovationAuditStatusList;
    //查询数据状态0=查询全部1=节点上传数据2=已完成数据
    private String status;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 装修工集合
     */
    private List<Worker> workerList;
    /**
     * 申请类型：0=装修申请，1=延期申请
     */
    private String decorateType;
    /**
     * 审批人
     */
    private String auditBy;

    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 延期天数（天）
     */
    private Integer delayDay;

    //装修工内外部:0=内部，1=外部
    private String intOut;
}
