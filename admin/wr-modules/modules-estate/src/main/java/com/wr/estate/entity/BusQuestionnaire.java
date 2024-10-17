package com.wr.estate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 13:59
 * 调查问卷
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("调查问卷")
@TableName(value = "xccj_community.bus_questionnaire")
public class BusQuestionnaire extends BaseEntity {


    private String id;

    //小区id
    private Long communityId;
    //物业id
    private Long estateId;

    //标题
    private String title;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String releaseTime;

    //发布状态0=未发布1=发布
    private String releaseStatus;

    //调查问卷内容
    private String content;

    //截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //调查问卷状态0=未发布1=进行中2=已提交3=已结束
    private String status;
    //显示顺序
    private int sort;

    //发布人
    private String releaseBy;
    //发布范围0=全部1=部分
    private String actRang;

    @TableLogic
    private String delFlag;

    //答题人数
    @TableField(exist = false)
    private int num;

    //发布范围楼栋集合
    @TableField(exist = false)
    private List<Long> buildingIds;
}
