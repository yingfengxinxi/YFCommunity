package com.wr.api.estate.entity.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:55
 */
@Data
public class QuestionnaireAccountVo {

    private String id;
    //业主名称
    private String accountName;
    //手机号
    private String loginName;
    //提交时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    //调查问卷id
    private String questionnaireInvestigationId;

    //调查问卷标题
    private String title;

    //调查问卷内容
    private String content;

    //调查问卷截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String releaseTime;
}
