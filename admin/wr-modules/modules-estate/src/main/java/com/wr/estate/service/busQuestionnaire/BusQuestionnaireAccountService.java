package com.wr.estate.service.busQuestionnaire;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.estate.entity.BusQuestionnaireAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:24
 */
public interface BusQuestionnaireAccountService extends IService<BusQuestionnaireAccount> {


    /**
     *
     * @param questionnaireInvestigationId
     * @return
     */
    List<BusQuestionnaireAccount> getList(String questionnaireInvestigationId);
}
