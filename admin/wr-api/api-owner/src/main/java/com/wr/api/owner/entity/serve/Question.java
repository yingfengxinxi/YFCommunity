package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目表 实体，表：bus_question
 *
 * @author SJiang
 * @since 2022-11-14 16:06:17
 */
@Data
public class Question implements Serializable {
    private static final long serialVersionUID = 718576047560096422L;
    /**
     * 主键
     */
    private Long questionId;
    /**
     * 主表类型：0=问卷调查（bus_questionnaire），1=党建调研（bus_research）
     */
    private String tableType;
    /**
     * 对应表id
     */
    private Long tableId;
    /**
     * 题目名称
     */
    private String questionName;
    /**
     * 题目类型：0=单选，1=多选，2=简答
     */
    private String questionType;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 题目选项
     */
    private List<QuestionItem> questionItems;
    /**
     * 问卷答案
     */
    private List<QuesAnswer> quesAnswers;

    @Override
    public String toString() {
        return "BusQuestion{" +
                "questionId=" + questionId +
                ", tableType='" + tableType + '\'' +
                ", tableId=" + tableId +
                ", questionName='" + questionName + '\'' +
                ", questionType='" + questionType + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}

