package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/27 16:51
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("满意度调查")
@TableName(value = "xccj_community.bus_satisfaction_survey")
public class SatisfactionSurvey extends BaseEntity {

    //	主键
    private String id;
    //	小区id，表xccj_community.bus_community
    private Long communityId;
    //问卷标题
    private String title;
    //评测编号
    private String satisfactionNo;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //问卷描述
    private String description;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

    //楼栋集合
    @TableField(exist = false)
    private String buildingIds;
    //问题id
    @TableField(exist = false)
    private List<SatisfactionSurveyProblem> satisfactionSurveyProblemList;
    //问题项统计
    @TableField(exist = false)
    private List<SatisfactionSurveyOwnerVo> problemList;
    //做为传参时 0进行中 1已提交 2已结束
    @TableField(exist = false)
    private String state;
    //统计已经提交的人数
    @TableField(exist = false)
    private String countnum;
    @TableField(exist = false)
    private String ownerId;
    @TableField(exist = false)
    private String buildingId;
    @TableField(exist = false)
    private List<SatisfactionSurveyOwner> ownerList;
    @TableField(exist = false)
    private List<SatisfactionSurveyBuilding> buildingList;
    /**
     * 展示状态
     **/
    @TableField(exist = false)
    private String showStatus;
}
