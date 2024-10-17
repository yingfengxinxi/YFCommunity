package com.wr.govern.service.autonomy.research;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.govern.autonomy.Research.Research;

import java.util.List;
import java.util.Map;

/**
 * 党建调研管理 服务层
 * 
 * @author liubei
 * @CreateDate 2022-10-31
 */
public interface ResearchService
{
    /**
     * 查询所有党建信息
     * @param research
     * @return
     */
    List<Research> selectResearchList(Research research);

    /**
     * 根据党建ID查询党建信息
     * @param researchId
     * @return
     */
    Research selectResearchById(Long researchId);

    /**
     * 新增党建信息
     * @param research
     * @return
     */
    int insertResearch(Research research);

    /**
     * 根据党建ID查询党建信息
     * @param researchId
     * @return
     */
    int deleteResearchById(Long[] researchId);

    /**
    * @Author SUNk
    * @Description 查询党建活动参与人员详细信息
    * @Date 16:50 2024/3/1
    * @Param [research]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult findListPartyMemberResearch(Research research);

    /**
     * @Author SUNk
     * @Description 根据问题ID查询答题人列表
     * @Date 16:50 2024/3/1
     * @Param [research]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    List<Map<String, Object>> findListAccoutByQuestionId(Long tableId);
}
