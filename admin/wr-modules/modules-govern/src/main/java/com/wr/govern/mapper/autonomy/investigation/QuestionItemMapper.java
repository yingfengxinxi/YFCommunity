package com.wr.govern.mapper.autonomy.investigation;

import com.wr.remote.govern.autonomy.investigation.Question;

import java.util.List;

/**
 * 党建问卷调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface QuestionItemMapper {

    /**
     * 添加党建问卷调查题目数据层
     * @param questionItem
     * @return
     */
    public int insertQuestionItem(Question questionItem);
    /**
     * 查看党建问卷调查题目数据层
     * @param
     * @return
     */
    public List<Question> selectQuestionItem(Long questionId);
}
