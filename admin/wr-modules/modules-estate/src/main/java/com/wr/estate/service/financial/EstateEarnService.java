package com.wr.estate.service.financial;

import com.wr.common.core.web.domain.AjaxResult;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:39:49
 * @Desc:
 */
public interface EstateEarnService {
    /**
     * 停车费统计
     *
     * @param comunityId 小区id
     * @return 对象列表
     */
    AjaxResult parkingIncomeStatistics(Long comunityId);

    /**
     * 收益统计
     *
     * @param communityId 小区id
     * @return 对象列表
     */
    AjaxResult incomeStatistic(Long communityId);

    /**
     * 支出统计
     *
     * @param communityId 小区id
     * @return ajax result
     */
    AjaxResult expendStatistic(Long communityId);

    /**
     * 收益详情
     *
     * @param communityId 小区id
     * @return 详情数据
     */
    AjaxResult incomeDetail(Long communityId);

    /**
     * 支出详情
     *
     * @param communityId 小区id
     * @return 详情数据
     */
    AjaxResult expendDetail(Long communityId);

}
