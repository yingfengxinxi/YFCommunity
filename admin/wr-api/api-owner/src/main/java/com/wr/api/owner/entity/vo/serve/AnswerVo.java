package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

import java.util.List;

/**
 * 答案扩展类
 * @Author: lvzy
 * @Date: 2022-11-16 13:32:15
 * @Desc: 答案扩展类
 */
@Data
public class AnswerVo {

    // 题目ID
    private Long questionId;

    /**
     * 答案 (str)
     */
    private String userAnswer;

    /**
     * 答案 (arr)
     */
    private List<String> userAnswerArr;

    /**
     * 题目类型：0=单选，1=多选，2=简答
     */
    private String questionType;
}
