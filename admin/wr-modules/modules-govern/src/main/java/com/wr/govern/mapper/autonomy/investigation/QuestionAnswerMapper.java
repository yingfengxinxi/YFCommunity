package com.wr.govern.mapper.autonomy.investigation;

import com.wr.remote.govern.autonomy.investigation.QuestionAnswer;

import java.util.List;
import java.util.Map;

/**
 * 党建问卷调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface QuestionAnswerMapper {

    /**
     * 根据题目ID查询信息
     * @param questionId
     * @return
     */
    List<QuestionAnswer> findByQuestionId(Long questionId);

    /**
    * @Author SUNk
    * @Description 根据问题ID查询答题人
    * @Date 16:51 2024/3/1
    * @Param [questionId]
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> findListAccoutByQuestionId(Long tableId);

    /**
     * @Author SUNk
     * @Description 根据问题ID查询答题人
     * @Date 16:51 2024/3/1
     * @Param [questionId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<QuestionAnswer> findListAnswerByQuestionId(Map<String,Object> map);
}
