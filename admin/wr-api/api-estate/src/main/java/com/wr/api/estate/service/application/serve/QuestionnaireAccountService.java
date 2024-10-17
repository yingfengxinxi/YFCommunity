package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.QuestionnaireAccountVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:12
 */
public interface QuestionnaireAccountService {

    /**
     *
     * @param status
     * @return
     */
    List<QuestionnaireAccountVo> getList(String status);

    /**
     *
     * @param id
     * @return
     */
    QuestionnaireAccountVo getById(String id);
}
