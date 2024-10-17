package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.application.serve.QuestionnaireAccountVo;
import com.wr.api.estate.mapper.application.QuestionnaireAccountMapper;
import com.wr.api.estate.service.application.serve.QuestionnaireAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:13
 */
@Service
public class QuestionnaireAccountServiceImpl implements QuestionnaireAccountService {

    @Autowired
    private QuestionnaireAccountMapper questionnaireAccountMapper;

    /**
     *
     * @param questionnaireInvestigationId
     * @return
     */
    @Override
    public List<QuestionnaireAccountVo> getList(String questionnaireInvestigationId) {
        return questionnaireAccountMapper.getList(questionnaireInvestigationId);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public QuestionnaireAccountVo getById(String id) {
        return questionnaireAccountMapper.getById(id);
    }
}
