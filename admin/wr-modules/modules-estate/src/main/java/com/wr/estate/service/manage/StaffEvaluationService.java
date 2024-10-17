package com.wr.estate.service.manage;

import com.wr.remote.domain.vo.StaffEvaluationVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/18 13:42
 */
public interface StaffEvaluationService {

    /**
     * @param staffId
     * @return
     */
    List<StaffEvaluationVo> getList( Long staffId);

}
