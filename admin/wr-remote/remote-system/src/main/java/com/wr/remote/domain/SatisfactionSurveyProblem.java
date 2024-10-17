package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/2/27 16:55
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("满意度调查问题表")
@TableName(value = "xccj_community.bus_satisfaction_survey_problem")
public class SatisfactionSurveyProblem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //	主键
    private String id;
    //问卷调查表id
    private String satisfactionSurveyId;
    //问题名称
    private String problemName;
    //删除标记：0=未删除，1=已删除
    private String delFlag;

}
