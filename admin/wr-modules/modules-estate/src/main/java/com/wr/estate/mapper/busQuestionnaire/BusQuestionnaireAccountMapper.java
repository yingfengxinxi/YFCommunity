package com.wr.estate.mapper.busQuestionnaire;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.estate.entity.BusQuestionnaireAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:22
 */
public interface BusQuestionnaireAccountMapper extends BaseMapper<BusQuestionnaireAccount> {

    /**
     *
     * @param questionnaireInvestigationId
     * @return
     */
    List<BusQuestionnaireAccount> getList(@Param("questionnaireInvestigationId") String questionnaireInvestigationId);
}
