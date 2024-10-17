package com.wr.api.owner.service.estateapi.satisfaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.SatisfactionSurveyOwner;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:06
 */
public interface SatisfactionSurveyService extends IService<SatisfactionSurvey> {


    /**
     * @param satisfactionSurvey
     * @return
     */
    List<SatisfactionSurvey> getList(SatisfactionSurvey satisfactionSurvey);

    /**
     *
     * @param satisfactionSurvey
     */
    public void saveSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey);

    /**
     *
     * @param satisfactionSurvey
     */
    public void updateSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey);

    /**
     *
     * @param id
     * @return
     */
    public SatisfactionSurvey get(String id,String flag);

    /**
     *
     * @param id
     */
    public void delete(String id);

    /**
     *
     * @param satisfactionSurveyId
     * @return
     */
    public List<SatisfactionSurveyOwnerVo>getSatisfactionProblemCount(String satisfactionSurveyId);

    /**
     * @Author SUNk
     * @Description 业主端查询列表
     * @Date 10:13 2024/3/17
     * @Param [satisfactionSurvey]
     * @return java.util.List<com.wr.remote.domain.SatisfactionSurvey>
     **/
    List<SatisfactionSurvey> findListSatisfaction(SatisfactionSurvey satisfactionSurvey);
    /**
     * @Author SUNk
     * @Description 业主端查询详情
     * @Date 10:13 2024/3/17
     * @Param [satisfactionSurvey]
     * @return java.util.List<com.wr.remote.domain.SatisfactionSurvey>
     **/
    SatisfactionSurvey findDetilSatisfaction(SatisfactionSurvey satisfactionSurvey);

    /**
    * @Author SUNk
    * @Description 保存满意度答案
    * @Date 10:45 2024/3/18
    * @Param [satisfactionSurvey]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult saveSurveyOwner(SatisfactionSurvey satisfactionSurvey);

    /**
    * @Author SUNk
    * @Description 更新满意度答案
    * @Date 10:43 2024/4/12
    * @Param [surveyOwnerList]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult updateSurveyOwner(List<SatisfactionSurveyOwner> surveyOwnerList);
}
