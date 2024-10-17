package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷答案表 实体，表：bus_ques_answer
 *
 * @author SJiang
 * @since 2022-11-14 16:08:45
 */
@Data
public class QuesAnswer implements Serializable {
    private static final long serialVersionUID = -14510671351656188L;
    /**
     * 主键
     */
    private Long answerId;
    /**
     * 答题人账号id，表xccj_community.bus_account
     */
    private Long accountId;
    /**
     * 主表类型：0=问卷调查（bus_questionnaire），1=党建调研（bus_research）
     */
    private String tableType;
    /**
     * 对应表id
     */
    private Long tableId;
    /**
     * 题目id，表xccj_govern.bus_question
     */
    private Long questionId;
    /**
     * 答案，若是选择题，存选项id英文逗号拼接，简答题存文字
     */
    private String userAnswer;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 选择答案集合
     */
    private List<QuesAnswer> quesAnswers;

    @Override
    public String toString() {
        return "QuesAnswer{" +
                "answerId=" + answerId +
                ", accountId=" + accountId +
                ", tableType='" + tableType + '\'' +
                ", tableId=" + tableId +
                ", questionId=" + questionId +
                ", userAnswer='" + userAnswer + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}

