package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.serve.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 11:49:21
 * @Desc: 问卷调查 VO
 */
@Data
public class QuestionnaireVO implements Serializable {
    private static final long serialVersionUID = -8157244911368657078L;

    /**
     * 主键id
     */
    private Long questionnaireId;
    /**
     * 所属小区id
     */
    private Long communityId;
    /**
     * 所属小区
     */
    private String communityName;
    /**
     * 问卷调查名称
     */
    private String quesName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 题目
     */
    private List<Question> questions;
    /**
     * 参与人数
     */
    private Integer ParticipateCount;
    /**
     * 是否以作答：0=进行中，1=已完成，2=已结束
     */
    private String status;

    @Override
    public String toString() {
        return "QuestionnaireVO{" +
                "questionnaireId=" + questionnaireId +
                ", communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", quesName='" + quesName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
