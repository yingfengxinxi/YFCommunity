package com.wr.remote.domain;

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
 * @Author lvzy
 * @Date 2024/2/1 21:34
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("巡查设置")
@TableName(value = "xccj_community.bus_inspection_setting")
public class InspectionSetting extends BaseEntity {
    //	id
    private String id;
    //物业id
    private Long estateId;
    //	所属小区id，表xccj_community.bus_community
    private Long communityId;
    //巡查名称
    private String inspectName;
    //巡查地址
    private String inspectionAddress;
    //	巡查点状态0=关1=开
    private String inspectStatus;
    //	执行人,多个执行人用逗号隔开
    private String executorBy;
    //巡查频率0=每日1=每半月2=每月
    private String frequency;
    //	第一次工单生成时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstExecutionTime;
    //工单结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderExpirationTime;
    //	执行次数
    private Integer executionNumber;
    //完成执行次数
    private Integer endExecutionNumber;
    //巡查内容
    private String content;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
