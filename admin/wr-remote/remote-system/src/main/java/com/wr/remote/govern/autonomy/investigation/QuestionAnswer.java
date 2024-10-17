package com.wr.remote.govern.autonomy.investigation;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc:问题答案
 */
@Data
public class QuestionAnswer extends BaseEntity {
    /**
     * 主键
     */
    private Long answerId;
    /**
     * 答题者ID
     */
    private Long accountId;
    /**
     *类型
     */
    private String  tableType;
    /**
     * 表id
     */
    private Long tableId;
    /**
     * 问卷调查名称
     */
    private Long  questionId;
    /**
     * 状态
     */
    private String userAnswer;

    /**
     * 标识
     */
    private String  delFlag;



}
