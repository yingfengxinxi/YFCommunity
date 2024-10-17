package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目选项实体，表：bus_question_item
 *
 * @author SJiangg
 * @since 2022-11-14 16:07:44
 */
@Data
public class QuestionItem implements Serializable {
    private static final long serialVersionUID = -92914624218592347L;
    /**
     * 主键
     */
    private Long itemId;
    /**
     * 题目id，表xccj_govern.bus_question(question_type!=2)
     */
    private Long questionId;
    /**
     * 选项名称
     */
    private String itemName;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}

