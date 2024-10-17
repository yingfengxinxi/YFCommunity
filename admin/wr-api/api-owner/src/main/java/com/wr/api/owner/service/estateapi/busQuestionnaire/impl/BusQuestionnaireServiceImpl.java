package com.wr.api.owner.service.estateapi.busQuestionnaire.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.estateapi.BusQuestionnaire;
import com.wr.api.owner.mapper.estateapi.busQuestionnaire.BusQuestionnaireMapper;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:21
 */
@Service
public class BusQuestionnaireServiceImpl extends ServiceImpl<BusQuestionnaireMapper, BusQuestionnaire> implements BusQuestionnaireService {

    @Resource
    private BusQuestionnaireMapper busQuestionnaireMapper;
    @Override
    public List<BusQuestionnaire> findListStatusBusQuestionnaire(BusQuestionnaire busQuestionnaire) {
        return busQuestionnaireMapper.findListStatusBusQuestionnaire( busQuestionnaire );
    }
}
