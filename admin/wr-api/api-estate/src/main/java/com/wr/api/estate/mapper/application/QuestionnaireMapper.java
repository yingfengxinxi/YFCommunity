package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.application.serve.QuestionnaireVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:05
 */
public interface QuestionnaireMapper {

    /**
     *
     * @param questionnaireVo
     * @return
     */
    List<QuestionnaireVo> getList(QuestionnaireVo questionnaireVo);
}
