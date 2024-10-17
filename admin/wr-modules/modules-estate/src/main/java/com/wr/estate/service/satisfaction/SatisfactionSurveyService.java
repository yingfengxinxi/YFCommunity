package com.wr.estate.service.satisfaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;

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
}
