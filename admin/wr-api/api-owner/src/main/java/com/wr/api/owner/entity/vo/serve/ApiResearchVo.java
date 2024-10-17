package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.serve.ApiResearch;
import com.wr.api.owner.entity.serve.QuestionItem;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 党建调研扩展类
 * @Author: lvzy
 * @Date: 2022-11-15 18:02:55
 * @Desc:党建调研扩展类
 */
@Data
public class ApiResearchVo extends ApiResearch implements Serializable {

    private static final long serialVersionUID = -7206664574433872305L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 参与人数
     */
    private Integer count;

    /**
     * 创建时间展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createTimes;

    /**
     * 党建调研状态 (列表用) 0:进行中 1:已完成 2:已结束
     */
    private String partyStatus;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 题目名称
     */
    private String questionName;

    /**
     * 题目类型：0=单选，1=多选，2=简答
     */
    private String questionType;

    /**
     * 组织ID
     */
    private String partyId;

    /**
     * 账号ID
     */
    private String accountId;

    /**
     * 狀態
     */
    private String status;

    /**
     * 展示裝藥
     */
    private String showStatus;

    /**
     * 题目列表
     */
    private List<QuestionItem> questionItems;
}
