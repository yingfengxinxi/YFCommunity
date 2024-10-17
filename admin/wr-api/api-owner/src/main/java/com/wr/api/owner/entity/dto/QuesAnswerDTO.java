package com.wr.api.owner.entity.dto;

import com.wr.api.owner.entity.serve.QuesAnswer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 答案接收接口复用
 * @Author: lvzy
 * @Date: 2022-12-03 14:47:36
 * @Desc: 答案接收接口复用
 */
@Data
public class QuesAnswerDTO extends QuesAnswer implements Serializable {

    /**
     * 答题人账号
     */
    private Long accountId;

    /**
     * 问卷ID
     */
    private Long tableId;

    /**
     * 答案复用
     */
    private List<QuesAnswer> quesAnswers;
}
