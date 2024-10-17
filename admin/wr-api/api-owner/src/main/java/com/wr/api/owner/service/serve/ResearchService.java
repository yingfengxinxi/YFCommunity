package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.QuesAnswerDTO;
import com.wr.api.owner.entity.vo.serve.ApiResearchVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 党建调研
 * @Author: lvzy
 * @Date: 2022-11-15 18:01:14
 * @Desc:党建调研
 */
public interface ResearchService {

    /**
     * 最新分页
     * @param researchVo
     * @return List
     */
    List<ApiResearchVo> newList(ApiResearchVo researchVo);

    /**
     * 详情
     * @param researchId
     * @return
     */
    AjaxResult detail(Long researchId);

    /**
     * 查找题目
     * @param researchId
     * @return
     */
    AjaxResult selectItem(Long researchId);

    /**
     * 党建调研提交
     * @param answerList
     * @return
     */
    AjaxResult submit(QuesAnswerDTO answerList);

    /**
     * 答题答案查询
     * @param researchId
     * @return
     */
    AjaxResult answerDetail(Long researchId,Long accountId);

    /**
     * 历史调研分页
     * @param researchVo
     * @return
     */
    List<ApiResearchVo> oldList(ApiResearchVo researchVo);

    /**
     * @Author SUNk
     * @Description 查询党建调研列表
     * @Date 18:35 2024/3/13
     * @Param [apiResearchVo]
     * @return java.util.List<com.wr.api.owner.entity.vo.serve.ApiResearchVo>
     **/
    List<ApiResearchVo> findListResearch(ApiResearchVo apiResearchVo);
}
