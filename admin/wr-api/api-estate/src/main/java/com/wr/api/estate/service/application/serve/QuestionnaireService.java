package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.QuestionnaireVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:12
 */
public interface QuestionnaireService {

    /**
     *
     * @param questionnaireVo
     * @return
     */
    List<QuestionnaireVo> getList(QuestionnaireVo questionnaireVo);
}
