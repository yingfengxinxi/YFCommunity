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
@ApiModel("调查问卷发布范围")
@TableName(value = "xccj_community.bus_questionnaire_scope")
public class BusQuestionnaireScope extends BaseEntity {


    private String id;

    //小区id
    private Long communityId;
    //发布范围楼栋id
    private Long buildingId;

    //问卷调查表id
    private String questionnaireId;

    @TableField(exist = false)
    private List<Long> buildingIds;

    @TableLogic
    private String delFlag;
}
