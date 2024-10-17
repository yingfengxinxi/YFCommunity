package com.wr.api.estate.entity.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:55
 */
@Data
public class QuestionnaireVo {

    private String id;

    private Long communityId;

    //问卷调查标题
    private String title;

    //浏览次数
    private int num;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;
    //截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    //调查问卷状态0=未发布1=进行中2=已提交3=已结束
    private String status;

}
