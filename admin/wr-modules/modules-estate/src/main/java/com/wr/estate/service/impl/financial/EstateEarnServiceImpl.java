package com.wr.estate.service.impl.financial;

import cn.hutool.core.util.ObjectUtil;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.mapper.financial.EstateEarnMapper;
import com.wr.estate.service.financial.EstateEarnService;
import com.wr.remote.estate.financial.dto.MonthStatisticDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
public class EstateEarnServiceImpl implements EstateEarnService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final List<Integer> monthList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    @Resource
    private EstateEarnMapper estateEarnMapper;
    @Resource
    private TableMapper tableMapper;

    /**
     * 停车费统计
     *
     * @param communityId 小区id
     * @return 对象列表
     */
    @Override
    public AjaxResult parkingIncomeStatistics(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        //物业收益统计
        List<BigDecimal> currentYearMoneyList = new ArrayList<>();
        List<BigDecimal> lastYearMoneyList = new ArrayList<>();
        List<BigDecimal> rateList = new ArrayList<>();

        String thisYear = new SimpleDateFormat("yyyy").format(new Date());
        String lastYear = Integer.parseInt(thisYear) - 1 + "";
        for (Integer month : monthList) {
            String separator = "-";
            if (month < 10) {
                separator = "-0";
            }
            BigDecimal thisYearMoney;
            BigDecimal lastYearMoney;
            String thisYearTable = TableUtils.getDbTableName(TableConstants.PREFIX_PAY_LOG, communityId, thisYear + separator + month);
            String lastYearTable = TableUtils.getDbTableName(TableConstants.PREFIX_PAY_LOG, communityId, lastYear + separator + month);
            Integer thisYearTableNum = tableMapper.existTable(thisYearTable.split("\\.")[1]);
            if (thisYearTableNum == 1) {
                thisYearMoney = estateEarnMapper.selectList(thisYearTable);
            } else {
                thisYearMoney = BigDecimal.ZERO;
            }
            currentYearMoneyList.add(thisYearMoney);

            Integer lastYearTableNum = tableMapper.existTable(lastYearTable.split("\\.")[1]);
            if (lastYearTableNum == 1) {
                lastYearMoney = estateEarnMapper.selectList(lastYearTable);
            } else {
                lastYearMoney = BigDecimal.ZERO;
            }
            lastYearMoneyList.add(lastYearMoney);
            BigDecimal rate = rate(thisYearMoney, lastYearMoney);
            rateList.add(rate);
        }
        ajaxResult.put("parkingMoney", currentYearMoneyList);
        ajaxResult.put("lastParkingMoney", lastYearMoneyList);
        ajaxResult.put("parkingRate", rateList);
        return ajaxResult;
    }

    @Override
    public AjaxResult incomeStatistic(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();

        //物业收益统计
        List<BigDecimal> currentYearEstateMoneyList = new ArrayList<>();
        List<BigDecimal> lastYearEstateMoneyList = new ArrayList<>();
        List<BigDecimal> estateRateList = new ArrayList<>();
        // 查询今年支出按月统计数据
        List<MonthStatisticDTO> currentYearEstateStatistic = estateEarnMapper.estateIncomeMonthStatisticByThisYear(communityId);
        Map<Integer, BigDecimal> currentYearEstateMoneyMap = currentYearEstateStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));
        // 查询去年支出按月统计数据
        List<MonthStatisticDTO> lastYearEstateStatistic = estateEarnMapper.estateIncomeMonthStatisticByLastYear(communityId);
        Map<Integer, BigDecimal> lastYearEstateMoneyMap = lastYearEstateStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));


        // 场馆收益统计
        List<BigDecimal> currentYearVenueMoneyList = new ArrayList<>();
        List<BigDecimal> lastYearVenueMoneyList = new ArrayList<>();
        List<BigDecimal> venueRateList = new ArrayList<>();
        // 查询今年支出按月统计数据
        List<MonthStatisticDTO> currentYearVenueStatistic = estateEarnMapper.venueIncomeMonthStatisticByThisYear(communityId);
        Map<Integer, BigDecimal> currentYearVenueMoneyMap = currentYearVenueStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));
        // 查询去年支出按月统计数据
        List<MonthStatisticDTO> lastYearVenueStatistic = estateEarnMapper.venueIncomeMonthStatisticByLastYear(communityId);
        Map<Integer, BigDecimal> lastYearVenueMoneyMap = lastYearVenueStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));


        // 其他收益统计
        List<BigDecimal> currentYearServerMoneyList = new ArrayList<>();
        List<BigDecimal> lastYearServerMoneyList = new ArrayList<>();
        List<BigDecimal> serverRateList = new ArrayList<>();
        // 查询今年支出按月统计数据
        List<MonthStatisticDTO> currentYearServerStatistic = estateEarnMapper.serverIncomeMonthStatisticByThisYear(communityId);
        Map<Integer, BigDecimal> currentYearServerMoneyMap = currentYearServerStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));
        // 查询去年支出按月统计数据
        List<MonthStatisticDTO> lastYearServerStatistic = estateEarnMapper.serverIncomeMonthStatisticByLastYear(communityId);
        Map<Integer, BigDecimal> lastYearServerMoneyMap = lastYearServerStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));


        for (Integer month : monthList) {
            // 物业收益统计
            // 今年按月统计数据
            BigDecimal currentYearEstateMoney = currentYearEstateMoneyMap.get(month);
            currentYearEstateMoney = ObjectUtil.isEmpty(currentYearEstateMoney) ? BigDecimal.ZERO : currentYearEstateMoney;
            currentYearEstateMoneyList.add(currentYearEstateMoney);

            // 去年按月统计数据
            BigDecimal lastYearEstateMoney = lastYearEstateMoneyMap.get(month);
            lastYearEstateMoney = ObjectUtil.isEmpty(lastYearEstateMoney) ? BigDecimal.ZERO : lastYearEstateMoney;
            lastYearEstateMoneyList.add(lastYearEstateMoney);
            // 计算同比
            BigDecimal estateRate = rate(currentYearEstateMoney, lastYearEstateMoney);
            estateRateList.add(estateRate);

            // 场馆费收益统计
            // 今年按月统计数据
            BigDecimal currentYearVenueMoney = currentYearVenueMoneyMap.get(month);
            currentYearVenueMoney = ObjectUtil.isEmpty(currentYearVenueMoney) ? BigDecimal.ZERO : currentYearVenueMoney;
            currentYearVenueMoneyList.add(currentYearVenueMoney);

            // 去年按月统计数据
            BigDecimal lastYearVenueMoney = lastYearVenueMoneyMap.get(month);
            lastYearVenueMoney = ObjectUtil.isEmpty(lastYearVenueMoney) ? BigDecimal.ZERO : lastYearVenueMoney;
            lastYearVenueMoneyList.add(lastYearVenueMoney);

            // 计算同比
            BigDecimal venueRate = rate(currentYearVenueMoney, lastYearVenueMoney);
            venueRateList.add(venueRate);

            // 其他费用收益统计
            // 今年按月统计数据
            BigDecimal currentYearServerMoney = currentYearServerMoneyMap.get(month);
            currentYearServerMoney = ObjectUtil.isEmpty(currentYearServerMoney) ? BigDecimal.ZERO : currentYearServerMoney;
            currentYearServerMoneyList.add(currentYearServerMoney);

            // 去年按月统计数据
            BigDecimal lastYearServerMoney = lastYearServerMoneyMap.get(month);
            lastYearServerMoney = ObjectUtil.isEmpty(lastYearServerMoney) ? BigDecimal.ZERO : lastYearServerMoney;
            lastYearServerMoneyList.add(lastYearServerMoney);
            // 计算同比
            BigDecimal serverRate = rate(currentYearServerMoney, lastYearServerMoney);
            serverRateList.add(serverRate);
        }

        // 物业收益
        ajaxResult.put("estatemoney", currentYearEstateMoneyList);
        ajaxResult.put("lastEstateMoney", lastYearEstateMoneyList);
        ajaxResult.put("estatemoneyrate", estateRateList);

        // 场馆费收益
        ajaxResult.put("venuemoney", currentYearVenueMoneyList);
        ajaxResult.put("lastVenueMoney", lastYearVenueMoneyList);
        ajaxResult.put("venuemoneyrate", venueRateList);

        // 其他收益
        ajaxResult.put("servermoney", currentYearServerMoneyList);
        ajaxResult.put("lastServerMoney", lastYearServerMoneyList);
        ajaxResult.put("servermoneyrate", serverRateList);
        return ajaxResult;
    }

    /**
     * 支出统计
     *
     * @param communityId 小区id
     * @return ajax result
     */
    @Override
    public AjaxResult expendStatistic(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        List<BigDecimal> currentYearMoneyList = new ArrayList<>(12);
        List<BigDecimal> lasttYearMoneyList = new ArrayList<>(12);
        List<BigDecimal> rateList = new ArrayList<>(12);
        // 查询今年支出按月统计数据
        List<MonthStatisticDTO> currentYearStatistic = estateEarnMapper.expendMonthStatisticByCurrentYear(communityId);
        Map<Integer, BigDecimal> currentYearMoneyMap = currentYearStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));
        // 查询去年支出按月统计数据
        List<MonthStatisticDTO> lastYearStatistic = estateEarnMapper.expendMonthStatisticByLastYear(communityId);
        Map<Integer, BigDecimal> lastYearMoneyMap = lastYearStatistic.stream().collect(Collectors.toMap(MonthStatisticDTO::getMonth, MonthStatisticDTO::getAmount));

        for (Integer month : monthList) {
            // 今年按月统计数据
            BigDecimal currentYearMoney = currentYearMoneyMap.get(month);
            currentYearMoney = ObjectUtil.isEmpty(currentYearMoney) ? BigDecimal.ZERO : currentYearMoney;
            currentYearMoneyList.add(currentYearMoney);

            // 去年按月统计数据
            BigDecimal lastYearMoney = lastYearMoneyMap.get(month);
            lastYearMoney = ObjectUtil.isEmpty(lastYearMoney) ? BigDecimal.ZERO : lastYearMoney;
            lasttYearMoneyList.add(lastYearMoney);
            // 计算同比
            BigDecimal rate = rate(currentYearMoney, lastYearMoney);
            rateList.add(rate);
        }
        ajaxResult.put("buymoney", currentYearMoneyList);
        ajaxResult.put("lastBuyMoney", lasttYearMoneyList);
        ajaxResult.put("buymoneyrate", rateList);
        return ajaxResult;
    }

    /**
     * 收益详情
     *
     * @param communityId 小区id
     * @return 详情数据
     */
    @Override
    public AjaxResult incomeDetail(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        AjaxResult incomeResult = this.incomeStatistic(communityId);
        AjaxResult parkingResult = this.parkingIncomeStatistics(communityId);
        // 物业费
        // 今年数据
        List<BigDecimal> thisEstateStatistic = (List<BigDecimal>) incomeResult.get("estatemoney");
        // 去年数据
        List<BigDecimal> lastEstateStatistic = (List<BigDecimal>) incomeResult.get("lastEstateMoney");
        BigDecimal thisEstateTotal = thisEstateStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastEstateTotal = lastEstateStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        ajaxResult.put("estateMoney", thisEstateTotal);
        ajaxResult.put("estateRate", rate(thisEstateTotal, lastEstateTotal));
        // 场馆费
        // 今年数据
        List<BigDecimal> thisVenueStatistic = (List<BigDecimal>) incomeResult.get("venuemoney");
        // 去年数据
        List<BigDecimal> lastVenueStatistic = (List<BigDecimal>) incomeResult.get("lastVenueMoney");
        BigDecimal thisVenueTotal = thisVenueStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastVenueTotal = lastVenueStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        ajaxResult.put("venueMoney", thisVenueTotal);
        ajaxResult.put("venueRate", rate(thisVenueTotal, lastVenueTotal));
        // 其他
        // 今年数据
        List<BigDecimal> thisServerStatistic = (List<BigDecimal>) incomeResult.get("servermoney");
        // 去年数据
        List<BigDecimal> lastServerStatistic = (List<BigDecimal>) incomeResult.get("lastServerMoney");
        BigDecimal thisServerTotal = thisServerStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastServerTotal = lastServerStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        ajaxResult.put("serverMoney", thisServerTotal);
        ajaxResult.put("serverRate", rate(thisServerTotal, lastServerTotal));
        // 停车费
        // 今年数据
        List<BigDecimal> thisParkingStatistic = (List<BigDecimal>) parkingResult.get("parkingMoney");
        // 去年数据
        List<BigDecimal> lastParkingStatistic = (List<BigDecimal>) parkingResult.get("lastParkingMoney");
        BigDecimal thisParkingTotal = thisParkingStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastParkingTotal = lastParkingStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        ajaxResult.put("parkingMoney", thisParkingTotal);
        ajaxResult.put("parkingRate", rate(thisParkingTotal, lastParkingTotal));

        // 总计
        BigDecimal totalThisMoney = thisEstateTotal.add(thisVenueTotal).add(thisServerTotal).add(thisParkingTotal);
        BigDecimal totalLastMoney = lastEstateTotal.add(lastVenueTotal).add(lastServerTotal).add(lastParkingTotal);
        ajaxResult.put("totalMoney", totalThisMoney);
        ajaxResult.put("totalRate", rate(totalThisMoney, totalLastMoney));
        return ajaxResult;
    }

    /**
     * 支出详情
     *
     * @param communityId 小区id
     * @return 详情数据
     */
    @Override
    public AjaxResult expendDetail(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        AjaxResult expendResult = this.expendStatistic(communityId);
        List<BigDecimal> thisExpendStatistic = (List<BigDecimal>) expendResult.get("buymoney");
        List<BigDecimal> lastExpendStatistic = (List<BigDecimal>) expendResult.get("lastBuyMoney");
        BigDecimal thisExpendTotal = thisExpendStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastExpendTotal = lastExpendStatistic.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        ajaxResult.put("expendMoney", thisExpendTotal);
        ajaxResult.put("expendRate", rate(thisExpendTotal, lastExpendTotal));
        return ajaxResult;
    }

    /**
     * 计算同比
     *
     * @param now  今年的数据
     * @param last 去年的数据
     * @return
     */
    BigDecimal rate(BigDecimal now, BigDecimal last) {
        if (BigDecimal.ZERO.compareTo(last) == 0) {
            if (BigDecimal.ZERO.compareTo(now) == 0) {
                return BigDecimal.valueOf(0);
            }
            return BigDecimal.valueOf(100);
        }
        return now.subtract(last).divide(last, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

}
