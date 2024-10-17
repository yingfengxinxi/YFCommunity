package com.wr.estate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("调查问卷人员")
@TableName(value = "xccj_community.bus_questionnaire_account")
public class BusQuestionnaireAccount extends BaseEntity {


    private String id;

    //小区id
    private Long communityId;

    //调查问卷id
    private String questionnaireInvestigationId;

    //用户id
    private Long accountId;

    //调查问卷内容
    private String content;

    //业主姓名
    @TableField(exist = false)
    private String userName;

    //业主手机号
    @TableField(exist = false)
    private String userPhone;
}
