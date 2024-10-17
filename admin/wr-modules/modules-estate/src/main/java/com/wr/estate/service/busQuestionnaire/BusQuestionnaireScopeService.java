package com.wr.estate.service.busQuestionnaire;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.estate.entity.BusQuestionnaireScope;

/**
 * @Author lvzy
 * @Date 2024/1/24 11:19
 */
public interface BusQuestionnaireScopeService extends IService<BusQuestionnaireScope> {


    /**
     *
     * @param busQuestionnaireScope
     */
    public void add(BusQuestionnaireScope busQuestionnaireScope);
}
