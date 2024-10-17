package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.vo.serve.AnswerVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 问卷答案表
 * @Author: lvzy
 * @Date: 2022-11-16 10:04:22
 * @Desc:问卷答案表
 */
public interface QuesAnswerMapper {

    /**
     * delete by primary key
     * @param answerId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long answerId);

    /**
     * insert record to table selective
     * @param quesAnswer the record
     * @return insert count
     */
    int insertSelective(QuesAnswer quesAnswer);

    /**
     * select by primary key
     * @param answerId primary key
     * @return object by primary key
     */
    QuesAnswer selectByPrimaryKey(Long answerId);

    /**
     * update record selective
     * @param quesAnswer the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(QuesAnswer quesAnswer);

    /**
     * 查找答案
     * @param researchId
     * @return
     */
    List<AnswerVo> answerDetail(@Param( "researchId" ) Long researchId,@Param( "accountId" ) Long accountId);
}