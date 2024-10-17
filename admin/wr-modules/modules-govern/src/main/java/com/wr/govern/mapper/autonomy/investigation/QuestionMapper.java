package com.wr.govern.mapper.autonomy.investigation;

import com.wr.remote.govern.autonomy.investigation.Question;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 党建问卷调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface QuestionMapper {

    /**
     * 添加党建问卷调查题目数据层
     * @param questionItem
     * @return
     */
    public int insertQuestion(Question questionItem);
    /**
     * 查看党建问卷调查题目数据层
     * @param
     * @return
     */
    public List<Question> selectQuestion(Question question);
}
