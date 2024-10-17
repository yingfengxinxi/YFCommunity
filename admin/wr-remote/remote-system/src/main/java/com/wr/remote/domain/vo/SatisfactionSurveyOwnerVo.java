package com.wr.remote.domain.vo;

import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/29 17:08
 */
@Data
public class SatisfactionSurveyOwnerVo {

    private String problemName;
    //弃权
    private String answer0Percentage;
    //非常满意百分比
    private String answer1Percentage;
    //满意百分比
    private String answer2Percentage;
    //一般百分比
    private String answer3Percentage;
    //不满意百分比
    private String answer4Percentage;
}
