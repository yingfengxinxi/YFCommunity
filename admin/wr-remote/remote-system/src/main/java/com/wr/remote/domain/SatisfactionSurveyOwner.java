package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/2/28 10:24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("满意度调查业主参与表")
@TableName(value = "xccj_community.bus_satisfaction_survey_owner")
public class SatisfactionSurveyOwner extends BaseEntity {

    //	主键
    private String id;
    //	满意度调查绑定楼栋表id
    private String satisfactionSurveyBuilding;
    //问题表id
    private String surveyProblemId;
    //业主id
    private String ownerId;
    //	0=未参与1=非常满意2=满意3=一般4=不满意
    private String answer;
    //删除标记：0=未删除，1=已删除
    private String delFlag;

}
