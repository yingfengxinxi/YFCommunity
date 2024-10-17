package com.wr.api.owner.service.estateapi.busQuestionnaire;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.owner.entity.estateapi.BusQuestionnaire;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:20
 */
public interface BusQuestionnaireService extends IService<BusQuestionnaire> {

    /**
     * @Author SUNk
     * @Description 问卷调查分页查询
     * @Date 20:23 2024/3/11
     * @Param [busQuestionnaire]
     * @return com.wr.api.owner.entity.estateapi.BusQuestionnaire
     **/
    List<BusQuestionnaire> findListStatusBusQuestionnaire(BusQuestionnaire busQuestionnaire);
}
