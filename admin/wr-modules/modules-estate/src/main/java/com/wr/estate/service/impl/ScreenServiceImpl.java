package com.wr.estate.service.impl;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.DateSectionUtil;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.screen.*;
import com.wr.estate.mapper.ScreenMapper;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.service.ScreenService;
import com.wr.remote.aiot.WarnEvent;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.govern.manage.VehicleLog;
import com.wr.remote.govern.manage.VehicleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author: GuanYu
 * @Date: 2022-11-05 14:54:34
 * @Desc: 物业端大屏
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScreenServiceImpl implements ScreenService {
    private final ScreenMapper screenMapper;
    private final TableMapper tableMapper;

    @Override
    public AjaxResult selectVisitLogCount(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        LogDto logDto;
        //本周访客人次
        int thisWeekNum = 0;
        String thisWt = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, communityId);
        int thisWe = tableMapper.existTable(thisWt);
        if (thisWe == 1) {
            logDto = new LogDto();
            logDto.setTableName(TableConstants.LOG_DB_NAME + "." + thisWt);
            logDto.setBeginTime(getWeekFirstDay());
            logDto.setEndTime(new Date());
            thisWeekNum = screenMapper.getVisitorNum(logDto);
        }
        ajaxResult.put("thisWeekNum", thisWeekNum);
        //上周访客人次
        int lastWeekNum = 0;
        Calendar firstCal = lastWeekFirstDay();
        int firstMonth = firstCal.get(Calendar.MONTH) + 1;
        Calendar lastCal = lastWeekLastDay();
        int lastMonth = lastCal.get(Calendar.MONTH) + 1;
        String lastWft = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, communityId, firstCal.getTime());
        int lastWfe = tableMapper.existTable(lastWft);
        if (lastWfe == 1) {
            logDto = new LogDto();
            logDto.setTableName(TableConstants.LOG_DB_NAME + "." + lastWft);
            logDto.setBeginTime(firstCal.getTime());
            logDto.setEndTime(lastCal.getTime());
            lastWeekNum = screenMapper.getVisitorNum(logDto);
        }
        if (firstMonth != lastMonth) {
            String lastWlt = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, communityId, lastCal.getTime());
            int lastWle = tableMapper.existTable(lastWlt);
            if (lastWle == 1) {
                logDto = new LogDto();
                logDto.setTableName(TableConstants.LOG_DB_NAME + "." + lastWlt);
                logDto.setBeginTime(firstCal.getTime());
                logDto.setEndTime(lastCal.getTime());
                lastWeekNum += screenMapper.getVisitorNum(logDto);
            }
        }
        //本周环比
        BigDecimal weekRate = new BigDecimal(0);
        if (thisWeekNum != 0) {
            weekRate = new BigDecimal(1);
        }
        if (lastWeekNum != 0) {
            BigDecimal thisBd = new BigDecimal(thisWeekNum);
            BigDecimal lastBd = new BigDecimal(lastWeekNum);
            weekRate = thisBd.subtract(lastBd).divide(lastBd, 2, RoundingMode.HALF_UP);
        }
        ajaxResult.put("weekRate", weekRate);
        //本月访客人次
        int thisMonthNum = 0;
        String thisMt = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, communityId);
        int thisMe = tableMapper.existTable(thisMt);
        if (thisMe == 1) {
            logDto = new LogDto();
            logDto.setTableName(TableConstants.LOG_DB_NAME + "." + thisMt);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, 1);
            logDto.setBeginTime(cal.getTime());
            logDto.setEndTime(new Date());
            thisMonthNum = screenMapper.getVisitorNum(logDto);
        }
        ajaxResult.put("thisMonthNum", thisMonthNum);
        //上月访客人次
        int lastMonthNum = 0;
        Calendar lastM = Calendar.getInstance();
        lastM.add(Calendar.MONTH, -1);
        String lastMt = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, communityId, lastM.getTime());
        int lastMe = tableMapper.existTable(lastMt);
        if (lastMe == 1) {
            logDto = new LogDto();
            logDto.setTableName(TableConstants.LOG_DB_NAME + "." + lastMt);
            logDto.setBeginTime(lastMonthFirstDay(lastM));
            logDto.setEndTime(lastMonthLastDay());
            lastMonthNum = screenMapper.getVisitorNum(logDto);
        }
        //本月环比
        BigDecimal monthRate = new BigDecimal(0);
        if (thisMonthNum != 0) {
            monthRate = new BigDecimal(1);
        }
        if (lastMonthNum != 0) {
            BigDecimal thisBd = new BigDecimal(thisMonthNum);
            BigDecimal lastBd = new BigDecimal(lastMonthNum);
            monthRate = thisBd.subtract(lastBd).divide(lastBd, 2, RoundingMode.HALF_UP);
        }
        ajaxResult.put("monthRate", monthRate);
        return ajaxResult;
    }

    private static Date getWeekFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    private static Calendar lastWeekFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }


    private static Calendar lastWeekLastDay() {
        Calendar cal = lastWeekFirstDay();
        cal.add(Calendar.DATE, 6);
        return cal;
    }

    private static Date lastMonthFirstDay(Calendar lastCal) {
        lastCal.set(Calendar.DAY_OF_MONTH, 1);
        return lastCal.getTime();
    }

    private static Date lastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }


    @Override
    public AjaxResult getDmyVisitLogCount(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
        //今日访客数
        int dayVisitorNum = 0;
        //月访客数
        int monthVisitorNum = 0;
        //年访客数
        int yearVisitorNum = 0;
        dayVisitorNum = screenMapper.getDayVisitorNum(communityId);
        monthVisitorNum = screenMapper.getMonthNum(communityId);
        yearVisitorNum = screenMapper.getYearVisitorNum(communityId);
        ajaxResult.put("dayVisitorNum", dayVisitorNum);
        ajaxResult.put("monthVisitorNum", monthVisitorNum);
        ajaxResult.put("yearVisitorNum", yearVisitorNum);
        return ajaxResult;
    }

    @Override
    public EquipmentCount selectEquipmentCount(Long communityId) {
        return screenMapper.selectEquipmentCount(communityId);
    }

    @Override
    public OwnerAge selectOwnerAge(Long communityId) {
        return screenMapper.selectOwnerAge(communityId);
    }

    @Override
    public List<PatrolOrderScreen> selectPatrolOrder(Long communityId) {
        return screenMapper.selectPatrolOrder(communityId);
    }

    @Override
    public CommunityStaff selectStaffCount(Long deptId) {
        return screenMapper.selectStaffCount(deptId);
    }

    @Override
    public PeopleInAndOutChart selectPeopleInAndOutChart(Long communityId) {
        Map<String, Object> userInOutNum = screenMapper.getUserInOutNum(communityId);
        PeopleInAndOutChart pioc = new PeopleInAndOutChart();
        pioc.setInNum(String.valueOf(userInOutNum.get("inNum")));
        pioc.setOutNum(String.valueOf(userInOutNum.get("outNum")));
        return pioc;
    }

    @Override
    public AjaxResult selectVisitCharts(Long communityId) {
        AjaxResult ajaxResult = new AjaxResult();
//        VisitCharts userChart = getYearData(TableConstants.PREFIX_USER_LOG, communityId);
        List<Integer> userChart = screenMapper.selectMonthUserCount(communityId);
        ajaxResult.put("userChart", userChart);
//        VisitCharts vehicleChart = getYearData(TableConstants.PREFIX_VEHICLE_LOG, communityId);
        List<Integer> vehicleChart = screenMapper.selectMonthVisitCount(communityId);
        ajaxResult.put("vehicleChart", vehicleChart);
        int thisMonthUserNum = 0;
        thisMonthUserNum = screenMapper.getUserMonthNum(communityId);
//        String tableName1 = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG, communityId);
//        int exist1 = tableMapper.existTable(tableName1);
//        if (exist1 > 0) {
//            thisMonthUserNum = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName1);
//        }
        ajaxResult.put("thisMonthUserNum", thisMonthUserNum);
        int thisMonthVehicleNum = 0;
        thisMonthVehicleNum = screenMapper.getVehicleMonthNum(communityId);
//        String tableName2 = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, communityId);
//        int exist2 = tableMapper.existTable(tableName2);
//        if (exist2 > 0) {
//            thisMonthVehicleNum = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName2);
//        }
        ajaxResult.put("thisMonthVehicleNum", thisMonthVehicleNum);
        return ajaxResult;
    }

//    private VisitCharts getYearData(String prefix, Long communityId) {
//        VisitCharts visitCharts = new VisitCharts();
//        Calendar cal = Calendar.getInstance();
//        //一月
//        int january = 0;
//        cal.set(Calendar.MONTH, 0);
//        String tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int janE = tableMapper.existTable(tableName);
//        if (janE > 0) {
//            january = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setJanuary(january);
//        //二月
//        int february = 0;
//        cal.set(Calendar.MONTH, 1);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int febE = tableMapper.existTable(tableName);
//        if (febE > 0) {
//            february = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setFebruary(february);
//        //三月
//        int march = 0;
//        cal.set(Calendar.MONTH, 2);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int marE = tableMapper.existTable(tableName);
//        if (marE > 0) {
//            march = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setMarch(march);
//        //四月
//        int april = 0;
//        cal.set(Calendar.MONTH, 3);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int aprE = tableMapper.existTable(tableName);
//        if (aprE > 0) {
//            april = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setApril(april);
//        //五月
//        int may = 0;
//        cal.set(Calendar.MONTH, 4);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int mayE = tableMapper.existTable(tableName);
//        if (mayE > 0) {
//            may = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setMay(may);
//        //六月
//        int june = 0;
//        cal.set(Calendar.MONTH, 5);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int junE = tableMapper.existTable(tableName);
//        if (junE > 0) {
//            june = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setJune(june);
//        //七月
//        int july = 0;
//        cal.set(Calendar.MONTH, 6);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int julE = tableMapper.existTable(tableName);
//        if (julE > 0) {
//            july = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setJuly(july);
//        //八月
//        int august = 0;
//        cal.set(Calendar.MONTH, 7);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int augE = tableMapper.existTable(tableName);
//        if (augE > 0) {
//            august = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setAugust(august);
//        //九月
//        int september = 0;
//        cal.set(Calendar.MONTH, 8);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int sepE = tableMapper.existTable(tableName);
//        if (sepE > 0) {
//            september = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setSeptember(september);
//        //十月
//        int october = 0;
//        cal.set(Calendar.MONTH, 9);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int octE = tableMapper.existTable(tableName);
//        if (octE > 0) {
//            october = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setOctober(october);
//        //十一月
//        int november = 0;
//        cal.set(Calendar.MONTH, 10);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int novE = tableMapper.existTable(tableName);
//        if (novE > 0) {
//            november = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setNovember(november);
//        //十二月
//        int december = 0;
//        cal.set(Calendar.MONTH, 11);
//        tableName = TableUtils.getTableName(prefix, communityId, cal.getTime());
//        int decE = tableMapper.existTable(tableName);
//        if (decE > 0) {
//            december = screenMapper.getMonthNum(TableConstants.LOG_DB_NAME + "." + tableName);
//        }
//        visitCharts.setDecember(december);
//        return visitCharts;
//    }

    @Override
    public AjaxResult selectInAndOut(Long communityId) {
        //进场
        AjaxResult ajaxResult = new AjaxResult();
        VehicleVo inLog = screenMapper.selectInAndOut("0", communityId);
        VehicleVo outLog = screenMapper.selectInAndOut("1", communityId);
        ajaxResult.put("inLog", inLog);
        ajaxResult.put("outLog", outLog);
        return ajaxResult;
    }

    @Override
    public AjaxResult selectPraise(Long communityId) {
        //好评
        Integer praise = screenMapper.selectAssessCount(communityId, "0");

        //差评
        Integer negative = screenMapper.selectAssessCount(communityId, "1");

        //未评价
        Integer notEvaluated = screenMapper.selectAssessCount(communityId, "2");

        //总数
        Integer total = screenMapper.selectAssessCount(communityId, "3");

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("praise", praise);
        ajaxResult.put("negative", negative);
        ajaxResult.put("notEvaluated", notEvaluated);
        ajaxResult.put("total", total);
        return ajaxResult;
    }

    @Override
    public List<WarnEvent> selectWarnEventList() {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        WarnEvent warnEvent = new WarnEvent();
        warnEvent.setStartTime(DateSectionUtil.dateStr(-6));
        warnEvent.setEndTime(DateSectionUtil.dateStr(0));
        return screenMapper.selectWarnEventList(warnEvent, estateId);
    }

    @Override
    public List<CommunityNotice> selectNoticeTitle() {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        return screenMapper.selectNoticeTitle(estateId);
    }

    @Override
    public List<DictDataVo> selectDictData(DictDataVo dictDataVo) {
        return screenMapper.selectDictData(dictDataVo);
    }

    @Override
    public List<String> selectWaterNum(MeterReadingWorkVo meterReadingWorkVo) {
        List<String> list = new ArrayList<>();
        LocalDate currentDate = LocalDate.now(); // 获取当前日期
        int currentYear = currentDate.getYear(); // 获取当前年份
        if (meterReadingWorkVo.getYear() != null) {
            currentYear = meterReadingWorkVo.getYear();
        }
        for (int i = 0; i < 12; i++) {
            if (i == 11) {
                currentYear += 1;
            }
            String data = "";
            if (i + 2 < 10) {
                data += currentYear + "-0" + (i + 2);
            } else {
                if (i == 11) {
                    data += currentYear + "-01";
                } else {
                    data += currentYear + "-" + (i + 2);
                }
            }
            meterReadingWorkVo.setCreateTime(data);
            String num = screenMapper.selectWaterNum(meterReadingWorkVo);
            if (num != null) {
                list.add(num);
            } else {
                list.add("0");
            }
        }
        return list;
    }
}
