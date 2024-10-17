package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.application.serve.QuestionnaireAccountVo;
import com.wr.api.estate.entity.application.serve.QuestionnaireVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:05
 */
public interface QuestionnaireAccountMapper {

    /**
     * 分页
     *
     * @param questionnaireInvestigationId
     * @return
     */
    List<QuestionnaireAccountVo> getList(@Param("questionnaireInvestigationId") String questionnaireInvestigationId);


    /**
     *
     * @param id
     * @return
     */
    QuestionnaireAccountVo getById(@Param("id") String id);
}
