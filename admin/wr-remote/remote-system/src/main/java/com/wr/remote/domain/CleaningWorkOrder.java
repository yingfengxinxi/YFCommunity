package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:34
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("清洁工单")
@TableName(value = "xccj_community.bus_cleaning_work_order")
public class CleaningWorkOrder extends BaseEntity {
    //id
    private String id;
    //物业id
    private Long estateId;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;//
    //工单编号
    @Excel(name = "工单编号")
    private String no;//
    //清洁人姓名
    @Excel(name = "清洁人姓名")
    private String name;
    @TableField(exist = false)
    @Excel(name = "清洁人联系方式")
    private String phone;
    //清洁点名称
    @Excel(name = "清洁点名称")
    private String cleaningName;
    //清洁点地址
    @Excel(name = "清洁点地址")
    private String cleaningAddress;
    //	清洁点情况
    @Excel(name = "清洁点情况")
    private String cleaningSituation;
    //清洁照片(多张照片使用逗号隔开)
    private String cleaningImage;
    @TableField(exist = false)
    private List<String> cleaningImages;
    //维修状况0=无1=有
    @Excel(name = "维修状况", readConverterExp = "0=无,1=有")
    private String stateRepair;
    //	清洁状态数据字典inspection_status
    @Excel(name = "清洁/执行状态", readConverterExp = "0=待上报,1=待执行,2=执行中,3=已完成,4=已超期")
    private String cleaningStatus;
    //报事名称
    @Excel(name = "报事名称")
    private String reportName;
    //	报事类型数据字典report_repair_type
    @Excel(name = "报事类型", readConverterExp = "0=报事报修,1=保洁,2=工程维修,3=投诉建议,4=其他")
    private String reportType;
    //	报事地址
    @Excel(name = "报事地址")
    private String reportAddress;
    //	报事图片
    private String reportImage;
    @TableField(exist = false)
    private List<String> reportImages;
    //	报事备注
    @Excel(name = "报事备注")
    private String reportRemark;
    //	工单反馈
    @Excel(name = "工单反馈")
    private String orderFeedback;
    //物业执行人登录账号
    @Excel(name = "执行人")
    private String executorBy;
    @TableField(exist = false)
    @Excel(name = "执行人联系方式")
    private String executorPhone;
    //	工单图片(多张图片使用逗号隔开)
    private String orderImage;
    @TableField(exist = false)
    private List<String> orderImages;
    //清洁时间
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "清洁时间")
    private Date time;
    //工单到期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "工单到期时间")
    private Date orderExpirationTime;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

}
