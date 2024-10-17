package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:49
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("报事报修")
@TableName(value = "xccj_community.bus_reporting_warranty")
public class ReportingWarranty extends BaseEntity {

    //id
    private String id;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;
    //工单编号
    private String no;
    //发布人登录账号id
    private String accountId;
    //发布人姓名
    private String name;
    //联系方式
    private String phone;
    //报事名称
    private String reportName;
    //	报事类型数据字典report_repair_type
    private String reportType;
    //报事状态数据字典report_repair_status
    private String reportStatus;
    //报事地址
    private String reportAddress;
    //	报事备注
    private String reportRemark;
    //	报事图片(多张图片使用逗号隔开)
    private String reportImage;
    @TableField(exist = false)
    private List<String> reportImages;
    //	工单反馈
    private String orderFeedback;
    //	工单图片(多张图片使用逗号隔开)
    private String orderImage;
    @TableField(exist = false)
    private List<String> orderImages;
    //物业执行人登录账号
    private String executorBy;
    //物业执行人联系方式
    @TableField(exist = false)
    private String executorPhone;
    //	工单评价
    private BigDecimal evaluate;
    //巡查工单表id
    private String inspectionId;
    //清洁工单表id
    private String cleaningWorkOrderId;
    //巡更工单表id
    private String patrolWorkOrderId;
    //工单完成时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderEndTime;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
