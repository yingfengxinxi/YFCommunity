package com.wr.estate.mapper.financial;


import com.wr.remote.estate.financial.dto.MonthStatisticDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface EstateEarnMapper {

    /**
     * 查询停车费
     *
     * @param tableName 查询条件
     * @return 对象列表
     */
    BigDecimal selectList(String tableName);

    /**
     * 今年物业费收益
     *
     * @param communityId 小区id
     * @return 月份统计数据
     */
    List<MonthStatisticDTO> estateIncomeMonthStatisticByThisYear(Long communityId);

    /**
     * 去年物业费收益
     *
     * @param communityId 小区id
     * @return 月份统计数据
     */
    List<MonthStatisticDTO> estateIncomeMonthStatisticByLastYear(Long communityId);

    /**
     * 今年场馆费收益
     *
     * @param communityId 小区id
     * @return 按月统计收益数据
     */
    List<MonthStatisticDTO> venueIncomeMonthStatisticByThisYear(Long communityId);

    /**
     * 去年场馆费收益
     *
     * @param communityId 小区id
     * @return 按月统计收益数据
     */
    List<MonthStatisticDTO> venueIncomeMonthStatisticByLastYear(Long communityId);

    /**
     * 今年合同费收益按月统计
     *
     * @param communityId 小区id
     * @return List<MonthStatisticDTO> 统计数据
     */
    List<MonthStatisticDTO> serverIncomeMonthStatisticByThisYear(Long communityId);

    /**
     * 去年合同费收益按月统计
     *
     * @param communityId 小区id
     * @return List<MonthStatisticDTO> 统计数据
     */
    List<MonthStatisticDTO> serverIncomeMonthStatisticByLastYear(Long communityId);

    /**
     * 今年支出按月统计
     *
     * @param communityId 小区id
     * @return 按月统计数据
     */
    List<MonthStatisticDTO> expendMonthStatisticByCurrentYear(Long communityId);

    /**
     * 去年支出按月统计
     *
     * @param communityId 小区id
     * @return 按月统计数据
     */
    List<MonthStatisticDTO> expendMonthStatisticByLastYear(Long communityId);

}
