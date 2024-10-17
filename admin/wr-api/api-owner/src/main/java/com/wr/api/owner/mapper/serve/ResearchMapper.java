package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiResearch;
import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.serve.QuestionItem;
import com.wr.api.owner.entity.vo.serve.ApiResearchVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 党建调研
 * @Author: lvzy
 * @Date: 2022-11-15 17:55:40
 * @Desc:党建调研
 */
public interface ResearchMapper {

    /**
     * delete by primary key
     * @param researchId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long researchId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiResearch record);

    /**
     * select by primary key
     * @param researchId primary key
     * @return object by primary key
     */
    ApiResearch selectByPrimaryKey(Long researchId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiResearch record);

    /**
     * 最新调研分页
     * @param researchVo
     * @return List
     */
    List<ApiResearchVo> listPage(ApiResearchVo researchVo);

    /**
     * 详情
     * @param researchId
     * @return
     */
    ApiResearchVo detail(Long researchId);

    /**
     * 详情 - 题目
     * @param researchId
     * @return
     */
    List<ApiResearchVo> selectItem(Long researchId);

    /**
     * 详情 - 答案列表
     * @param questionId
     * @return
     */
    List<QuesAnswer> selectAnswer(Long questionId);

    /**
     * 题目列表
     * @param questionId
     * @return
     */
    List<QuestionItem> selectQuestionList(Long questionId);

    /**
    * @Author SUNk
    * @Description 查询党建调研列表
    * @Date 18:36 2024/3/13
    * @Param [apiResearchVo]
    * @return java.util.List<com.wr.api.owner.entity.vo.serve.ApiResearchVo>
    **/
    List<ApiResearchVo> findListResearch(ApiResearchVo apiResearchVo);

    /**
    * @Author SUNk
    * @Description 统计回答人员数量
    * @Date 9:47 2024/3/26
    * @Param [tableId]
    * @return java.lang.Integer
    **/
    Integer findCountAnswer(@Param( "tableId" ) Long tableId);
}