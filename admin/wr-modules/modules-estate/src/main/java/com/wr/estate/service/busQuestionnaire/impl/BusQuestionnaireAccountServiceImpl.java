package com.wr.estate.service.busQuestionnaire.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.entity.BusQuestionnaireAccount;
import com.wr.estate.mapper.busQuestionnaire.BusQuestionnaireAccountMapper;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:25
 */
@Service
public class BusQuestionnaireAccountServiceImpl extends ServiceImpl<BusQuestionnaireAccountMapper, BusQuestionnaireAccount> implements BusQuestionnaireAccountService {

    /**
     *
     * @param questionnaireInvestigationId
     * @return
     */
    @Override
    public List<BusQuestionnaireAccount> getList(String questionnaireInvestigationId) {
        return baseMapper.getList(questionnaireInvestigationId);
    }
}
