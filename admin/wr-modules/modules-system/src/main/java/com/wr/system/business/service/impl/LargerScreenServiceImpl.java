package com.wr.system.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.Tools;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ProtocolConstants;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.DateSectionUtil;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.customize.util.TreeUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.aiot.WarnEvent;
import com.wr.remote.domain.Area;
import com.wr.remote.domain.Community;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.TreeSelect;
import com.wr.system.business.domain.CommunityVo;
import com.wr.system.business.domain.screen.*;
import com.wr.system.business.mapper.*;
import com.wr.system.business.service.LargerScreenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: GuanYu
 * @Date: 2021-09-02 08:40:58
 * @Desc:
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LargerScreenServiceImpl implements LargerScreenService {
    private static final Logger log = LoggerFactory.getLogger(LargerScreenServiceImpl.class);

    private final LargerScreenMapper screenMapper;
    private final TableMapper tableMapper;
    private final CommunityMapper communityMapper;
    private final VisitorsTrafficMapper visitorsTrafficMapper;
    private final RepaireNumberMapper numberMapper;
    private final EquipmentAlarmMapper equipmentAlarmMapper;
    private final UserVehicleMapper userVehicleMapper;
    private final RedisService redisService;
    private final ReportingWarrantyMapper reportingWarrantyMapper;


    /**
     * 物业员工数据
     *
     * @return
     */
    @Override
    public EmployeeData selectEmployeeData(Long deptId) {
        return screenMapper.selectEmployeeData(deptId);
    }

    @Override
    public AjaxResult selectHousingStatusData(Long communityId) {
        JSONObject data = new JSONObject();
        Integer sellingNum = screenMapper.selectHousingStatusData(communityId, "0");
        data.put("sellingNum", Optional.ofNullable(sellingNum).orElse(0));
        Integer checkInNum = screenMapper.selectHousingStatusData(communityId, "2");
        data.put("checkInNum", Optional.ofNullable(checkInNum).orElse(0));
        Integer rentNum = screenMapper.selectHousingStatusData(communityId, "8");
        data.put("rentNum", Optional.ofNullable(rentNum).orElse(0));
        Integer furnishNum = screenMapper.selectHousingStatusData(communityId, "3");
        data.put("furnishNum", Optional.ofNullable(furnishNum).orElse(0));


//        //在售
//        data.put("sellingNum", 27);
//        //入住
//        data.put("checkInNum", 433);
//        //出租
//        data.put("rentNum", 0);
//        //装修
//        data.put("furnishNum", 214);
        return AjaxResult.success(data);
    }

    @Override
    public AjaxResult selectOwnerAgeData(Long communityId) {
        JSONObject data = new JSONObject();
        Integer ten = screenMapper.selectOwnerAgeData(communityId, "0");
        data.put("ten", Optional.ofNullable(ten).orElse(0));

        Integer twenty = screenMapper.selectOwnerAgeData(communityId, "1");
        data.put("twenty", Optional.ofNullable(twenty).orElse(0));

        Integer forty = screenMapper.selectOwnerAgeData(communityId, "2");
        data.put("forty", Optional.ofNullable(forty).orElse(0));

        Integer overForty = screenMapper.selectOwnerAgeData(communityId, "3");
        data.put("overForty", Optional.ofNullable(overForty).orElse(0));

        Integer total = screenMapper.selectOwnerAgeData(communityId, "4");
        data.put("total", Optional.ofNullable(total).orElse(0));
        return AjaxResult.success(data);
    }

    @Override
    public CommunityPersonnel selectCommunityPersonnel(Long communityId) {
        return screenMapper.selectCommunityPersonnel(communityId);
    }

    @Override
    public WorkOrder selectWorkOrderData(Long communityId) {
        return screenMapper.selectWorkOrderData(communityId);
    }

    @Override
    public EquipmentStatistics selectEquipmentStatistics(Long communityId) {
        return screenMapper.selectEquipmentStatistics(communityId);
    }

    @Override
    public EquipmentStatus selectEquipmentStatus(Long communityId) {
        return screenMapper.selectEquipmentStatus(communityId);
    }

    @Override
    public List<OperPatrolOrder> selectPatrolOrder(Long communityId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        return screenMapper.selectPatrolOrder(communityId, calendar.getTime());
    }

    @Override
    public AjaxResult selectPeopleVisit(Long communityId) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dayData = initTimeList();
        List userData = new ArrayList<>();
        List vehicleInData = new ArrayList<>();
        List vehicleOutData = new ArrayList<>();
        for (int i = 0; i < dayData.size(); i++) {
            String startTime = sdf.format(cal.getTime()) + " " + dayData.get(i).split("~")[0] + ":00";
            String endTime = sdf.format(cal.getTime()) + " " + dayData.get(i).split("~")[1] + ":59";
            VisitingSecord visitingSecord = new VisitingSecord();
            visitingSecord.setStartTime(startTime);
            visitingSecord.setEndTime(endTime);
            visitingSecord.setCommunityId(communityId);
            Map<String, Object> map = screenMapper.selectFlowNum(visitingSecord);
            userData.add(map.get("doorNum"));
            vehicleInData.add(map.get("inNum"));
            vehicleOutData.add(map.get("outNum"));
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("dayData", dayData);
        ajaxResult.put("userData", userData);
        ajaxResult.put("vehicleInData", vehicleInData);
        ajaxResult.put("vehicleOutData", vehicleOutData);
        return ajaxResult;
    }

    private void vehicleData(String vehicleTable, List<Integer> vehicleInList, List<Integer> vehicleOutList) {
        //车行--进
        List<PeopleVisit> inData = screenMapper.selectVehicleCountOut(TableConstants.LOG_DB_NAME + "." + vehicleTable, "0");
        peopleVisitData(inData, vehicleInList);
        //车行--出
        List<PeopleVisit> outData = screenMapper.selectVehicleCountOut(TableConstants.LOG_DB_NAME + "." + vehicleTable, "1");
        peopleVisitData(outData, vehicleOutList);
    }

    private void peopleVisitData(List<PeopleVisit> peopleVisits, List<Integer> list) {
        for (int i = 0; i < 24; i++) {
            for (PeopleVisit pv : peopleVisits) {
                int current = list.get(i);
                if (i == pv.getHourNum()) {
                    list.set(i, current + pv.getPassNum());
                    break;
                }
            }
        }
    }

    private static List<String> initTimeList() {
        List<String> list = Lists.newArrayList();
        list.add("00:00~01:59");
        list.add("02:00~03:59");
        list.add("04:00~05:59");
        list.add("06:00~07:59");
        list.add("08:00~09:59");
        list.add("10:00~11:59");
        list.add("12:00~13:59");
        list.add("14:00~15:59");
        list.add("16:00~17:59");
        list.add("18:00~19:59");
        list.add("20:00~21:59");
        list.add("22:00~23:59");
        return list;
    }

    private static List<Integer> initZeroList(int max) {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < max; i++) {
            list.add(0);
        }
        return list;
    }

    @Override
    public ReportRepair selectReportRepair(Long communityId) {
        return screenMapper.selectReportRepair(communityId);
    }

    @Override
    public List<Community> selectCommunityByAeraId(Long areaId) {
        return screenMapper.selectCommunityByAeraId(areaId);
    }


    @Override
    public List<Area> selectAreaList(Area area) {
        return screenMapper.selectAreaList(area);
    }

    @Override
    public List<TreeSelect> buildAreaTreeSelect(List<Area> areas) {
        List<Area> areaList = TreeUtils.buildLongTree(areas);
        return areaList.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<CameraVo> selectCameraVo(Long communityId) {
        String urlKey = "CAMERA_URL:";
        List<CameraVo> voList = screenMapper.selectCameraVo(communityId);
        if (!voList.isEmpty()) {
            CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
            if (null != hik) {
                HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
                for (int i = 0; i < voList.size(); i++) {
                    String url = redisService.getCacheObject(urlKey + voList.get(i).getIndexCode());
                    if (StringUtils.isNotEmpty(url)) {
                        voList.get(i).setCameraUrl(url);
                    } else {
                        url = HikUtils.camerasPreviewURLs(voList.get(i).getIndexCode(), ProtocolConstants.PROTOCOL_WS);
                        voList.get(i).setCameraUrl(url);
                        redisService.setCacheObject(urlKey + voList.get(i).getIndexCode(), url, 5L, TimeUnit.MINUTES);
                    }
                    if (i >= 8) {
                        break;
                    }
                }
            }
        }
        return voList;
    }

    @Override
    public AjaxResult selectUserList(Long communityId) {
        List<UserDoorPeopleVo> list = screenMapper.selectUserDoorLogList(communityId);
        for (UserDoorPeopleVo userDoorPeopleVo : list) {
            userDoorPeopleVo.setPersonName(desensitizeName(userDoorPeopleVo.getPersonName()));
        }
        return AjaxResult.success(list);
    }

    @Override
    public CameraVo getCameraById(Long id) {
        return screenMapper.getCameraById(id);
    }

    @Override
    public List<SuggestVo> selectSuggestScore() {
        return screenMapper.selectSuggestScore();
    }

    /**
     * 查询费用
     *
     * @param communityId
     * @return
     */
    @Override
    public AmountFee selectAmount(Long communityId) {
        Calendar cal = Calendar.getInstance();
        VisitingSecord visitingSecord = new VisitingSecord();
        visitingSecord.setStartTime(String.valueOf(cal.get(Calendar.YEAR)));
        visitingSecord.setCommunityId(communityId);
        AmountFee amountFee = screenMapper.selectAmount(visitingSecord);
        amountFee.setProportion("0%");
        if (!BigDecimal.ZERO.equals(amountFee.getTotalAmount()) && !amountFee.getTotalAmount().equals(new BigDecimal("0.00"))) {
            BigDecimal rate = amountFee.getParkAmount().multiply(new BigDecimal("100")).divide(amountFee.getTotalAmount(), 0, RoundingMode.HALF_UP);
            amountFee.setProportion(rate.toString() + "%");
        }
        return amountFee;
    }

    @Override
    public StaffVo selectStaffNum(Long communityId) {

        StaffVo staffVo = new StaffVo();
        StaffInfo staffInfo = new StaffInfo();
        if (Objects.nonNull(communityId)) {
            DeptVo dept = screenMapper.getDeptByCommunityId(communityId);
            staffInfo.setDeptId(dept.getDeptId());
            staffVo.setDeptName(dept.getDeptName());
        } else {
            staffVo.setDeptName("物业总人数");
        }
        staffInfo.setWorkPost("0");
        int i0 = screenMapper.selectStaffCount(staffInfo);
        staffInfo.setWorkPost("1");
        int i1 = screenMapper.selectStaffCount(staffInfo);
        staffInfo.setWorkPost("4");
        int i2 = screenMapper.selectStaffCount(staffInfo);

        //安防人员人数
        staffVo.setAfStaffNumber(i0);
        //维修人员人数
        staffVo.setWxStaffNumber(i1);
        //保洁人员人数
        staffVo.setWyglStaffNumber(i2);


        staffInfo.setWorkPost(null);
        int sum = screenMapper.selectStaffCount(staffInfo);
        if (sum > 0) {
            staffVo.setWxryzb(Double.valueOf(Tools.DecimalFormat(i0 / (double) sum)));
            staffVo.setAfryzb(Double.valueOf(Tools.DecimalFormat(i1 / (double) sum)));
            staffVo.setWyglryzb(Double.valueOf(Tools.DecimalFormat(i2 / (double) sum)));
        } else {
            staffVo.setWxryzb(0.00);
            staffVo.setAfryzb(0.00);
            staffVo.setWyglryzb(0.00);
        }
        staffVo.setSum(sum);

        return staffVo;
    }

    @Override
    public AjaxResult selectInformation(Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();
        //巡查
        VisitorsTraffic visitorsTrafficPatrol = visitorsTrafficMapper.selectInformation(communityId);
        //巡更
        VisitorsTraffic visitorsTraffic = visitorsTrafficMapper.selectInformationType(communityId);
        //巡检
        VisitorsTraffic visitorsTrafficPatrolOrder = visitorsTrafficMapper.selectInformationPatrolOrder(communityId);
        //昨天巡检设备数
        VisitorsTraffic countPatrolOrder = visitorsTrafficMapper.selectCountPatrolOrder(communityId);
        //查询本月巡检设备数
        VisitorsTraffic duringMonthPatrolOrder = visitorsTrafficMapper.selectCountDuringMonthPatrolOrder(communityId);
        ajaxResult.put("visitorsTrafficPatrol", visitorsTrafficPatrol);
        ajaxResult.put("visitorsTraffic", visitorsTraffic);
        ajaxResult.put("visitorsTrafficPatrolOrder", visitorsTrafficPatrolOrder);
        ajaxResult.put("countPatrolOrder", countPatrolOrder.getYesterday());
        ajaxResult.put("duringMonthPatrolOrder", duringMonthPatrolOrder.getDuringMonth());
        List<String> visitorsDate = org.apache.commons.compress.utils.Lists.newArrayList();
        List<String> weekByDate = org.apache.commons.compress.utils.Lists.newArrayList();
        List<String> list = getVisitorsDate(7);
        list.forEach(lists -> {
            visitorsDate.add(lists.substring(5));
            String str = getDayOfWeekByDate(lists);
            weekByDate.add(str.substring(2));
        });
        ajaxResult.put("list", visitorsDate);
        ajaxResult.put("weekByDate", weekByDate);
        return ajaxResult;
    }

    public static ArrayList<String> getVisitorsDate(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    public static String getDayOfWeekByDate(String date) {
        String dayOfWeek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            dayOfWeek = formatter.format(myDate);
        } catch (Exception e) {
            log.error("错误:{}", e.getMessage());
        }
        return dayOfWeek;
    }

    @Override
    public RepaireNumber selectCount() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        /*今天*/
        int i = reportingWarrantyMapper.selectCountNow();
        /*昨天*/
        int j = reportingWarrantyMapper.selectCountYesterday();
        /*依次类推后面的数量要减去前面的数量*/
        int a = reportingWarrantyMapper.selectCountOne();
        int b = reportingWarrantyMapper.selectCountTow();
        int c = reportingWarrantyMapper.selectCountThree();
        int d = reportingWarrantyMapper.selectCountFour();
        int e = reportingWarrantyMapper.selectCountFive();
        if ("星期一".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setMonday(i);
            number.setCountNumber(i);
            return number;
        } else if ("星期二".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setTuesday(i);
            number.setMonday(j);
            number.setCountNumber(i + j);
            return number;
        } else if ("星期三".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setWednesday(i);
            number.setTuesday(j);
            number.setMonday(a);
            number.setCountNumber(i + j + a);
            return number;
        } else if ("星期四".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setThursday(i);
            number.setWednesday(j);
            number.setTuesday(a);
            number.setMonday(b - a);
            number.setCountNumber(i + j + a + b - a);
            return number;
        } else if ("星期五".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setFriday(i);
            number.setThursday(j);
            number.setWednesday(a);
            number.setTuesday(b - a);
            number.setMonday(c - b);
            number.setCountNumber(i + j + a + b - a + c - b);
            return number;
        } else if ("星期六".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setSaturday(i);
            number.setFriday(j);
            number.setThursday(a);
            number.setWednesday(b - a);
            number.setTuesday(c - b);
            number.setMonday(d - c);
            number.setCountNumber(i + j + a + b - a + c - b + d - c);
            return number;
        } else if ("星期日".equals(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1])) {
            RepaireNumber number = new RepaireNumber();
            number.setSunday(i);
            number.setSaturday(j);
            number.setFriday(a);
            number.setThursday(b - a);
            number.setWednesday(c - b);
            number.setTuesday(d - c);
            number.setMonday(e - d);
            number.setCountNumber(i + j + a + b - a + c - b + d - c + e - d);
            return number;
        }
        return new RepaireNumber();
    }

    @Override
    public OrderStatusVo selectDealCount(Long communityId) {
        //今日处理的数据
        int i = reportingWarrantyMapper.selectNowCountDeal(communityId);
        //今日没有处理的数据
        int a = reportingWarrantyMapper.selectNowCountNotDeal(communityId);
        //统计总数数据
        int b = reportingWarrantyMapper.selectOrderStatusCount(communityId);
        OrderStatusVo statusVo = new OrderStatusVo();
        statusVo.setDealCount(i);
        statusVo.setNotDealCount(a);
        statusVo.setTotalCount(b);
        return statusVo;
    }

    @Override
    public List<EquipmentAlarm> selectEquipmentAlarmAll(Long communityId) {
        Long acsNum = equipmentAlarmMapper.getAcsNum(communityId);
        Long fireNum = equipmentAlarmMapper.getFireNum(communityId);
        Long videoNum = equipmentAlarmMapper.getVideoNum(communityId);
        Long visitNum = equipmentAlarmMapper.getVisitNum(communityId);
        List<EquipmentAlarm> equipmentAlarms = new ArrayList<>();
        EquipmentAlarm e1 = new EquipmentAlarm();
        e1.setEquipmentAlarmName("门禁预警");
        e1.setEquipmentAlarmNum(acsNum);
        equipmentAlarms.add(e1);
        EquipmentAlarm e2 = new EquipmentAlarm();
        e2.setEquipmentAlarmName("烟火预警");
        e2.setEquipmentAlarmNum(fireNum);
        equipmentAlarms.add(e2);
        EquipmentAlarm e3 = new EquipmentAlarm();
        e3.setEquipmentAlarmName("视频预警");
        e3.setEquipmentAlarmNum(videoNum);
        equipmentAlarms.add(e3);
        EquipmentAlarm e4 = new EquipmentAlarm();
        e4.setEquipmentAlarmName("可视对讲预警");
        e4.setEquipmentAlarmNum(visitNum);
        equipmentAlarms.add(e4);
        return equipmentAlarms;
    }

    @Override
    public AjaxResult getUserVehicleCount(Long communityId, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, (month - 1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> dayData = Lists.newArrayList();
        List<Integer> userList = new ArrayList<>();
        List<Integer> vehicleList = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            dayData.add(i);
        }
        VisitingSecord visitingSecord = new VisitingSecord();
        visitingSecord.setStartTime(sdf.format(cal.getTime()));
        visitingSecord.setCommunityId(communityId);
        //人行
        userList = screenMapper.getUserCount(visitingSecord);
        //车行
        vehicleList = screenMapper.getVehicleCount(visitingSecord);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("dayData", dayData);
        ajaxResult.put("userList", userList);
        ajaxResult.put("vehicleList", vehicleList);
        return ajaxResult;
    }

    private void addListData(int max, List<Integer> list, List<UserVehicle> vehicleCount) {
        for (int i = 0; i < max; i++) {
            int current = list.get(i);
            for (UserVehicle uv : vehicleCount) {
                if (Objects.equals(uv.getDay(), String.valueOf((i + 1)))) {
                    list.set(i, current + uv.getNum());
                    break;
                }
            }
        }
    }

    @Override
    public AnnualDay getMonthlyMaintenanceStatistics(Integer year, Long communityId) {
        AnnualDay annualDay = new AnnualDay();
        ArrayList<AnnualMaintenanceEquipmentVo> equipmentVos = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            List<AnnualMaintenanceEquipmentVo> list = equipmentAlarmMapper.getMonthlyMaintenanceStatistics(year, communityId);
            AnnualMaintenanceEquipmentVo maintenanceEquipmentVo = new AnnualMaintenanceEquipmentVo();
            if (i <= 9) {
                for (AnnualMaintenanceEquipmentVo annualMaintenanceEquipmentVo : list) {
                    if (("0" + i).equals(annualMaintenanceEquipmentVo.getDateMonth())) {
                        maintenanceEquipmentVo.setDateMonth(i + "");
                        maintenanceEquipmentVo.setNum(annualMaintenanceEquipmentVo.getNum());
                        break;
                    } else {
                        maintenanceEquipmentVo.setDateMonth(i + "");
                        maintenanceEquipmentVo.setNum("0");
                    }
                }
            } else {
                for (AnnualMaintenanceEquipmentVo annualMaintenanceEquipmentVo : list) {
                    if ((i + "").equals(annualMaintenanceEquipmentVo.getDateMonth())) {
                        maintenanceEquipmentVo.setDateMonth(i + "");
                        maintenanceEquipmentVo.setNum(annualMaintenanceEquipmentVo.getNum());
                        break;
                    } else {
                        maintenanceEquipmentVo.setDateMonth(i + "");
                        maintenanceEquipmentVo.setNum("0");
                    }
                }
            }
            equipmentVos.add(maintenanceEquipmentVo);
        }
        annualDay.setMaintenanceEquipmentVos(equipmentVos);
        return annualDay;
    }

    @Override
    public List<DeviceVo> getListDerviceAbnormal(Long communityId) {
        WarnEvent warnEvent = new WarnEvent();
        warnEvent.setStartTime(DateSectionUtil.dateStr(-6));
        warnEvent.setEndTime(DateSectionUtil.dateStr(0));
        warnEvent.setCommunityId(communityId);
        List<WarnEvent> warnEvents = equipmentAlarmMapper.selectWarnEvent(warnEvent);
        List<DeviceVo> list = new ArrayList<>();
        warnEvents.forEach(warn -> {
            DeviceVo deviceVo = new DeviceVo();
            deviceVo.setName(warn.getEventName());
            deviceVo.setTime(warn.getRecordTime());
            deviceVo.setAddress(warn.getAddress());
            list.add(deviceVo);
        });
        return list;
    }

    @Override
    public AjaxResult selectMonthVisitCount(Long communityId, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, (month - 1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> dayData = Lists.newArrayList();
        List visitList = Lists.newArrayList();
        for (int i = 1; i <= max; i++) {
            dayData.add(i);
//            Random random = new Random();
//            int min = 5;
//            int max1 = 10;
//            int randomNumber = random.nextInt(max1 - min + 1) + min;
//            visitList.add(randomNumber);
        }
        VisitingSecord visitingSecord = new VisitingSecord();
        visitingSecord.setStartTime(sdf.format(cal.getTime()));
        visitingSecord.setCommunityId(communityId);
        //List visitList = screenMapper.selectMonthVisitCount(visitingSecord);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("dayData", dayData);
        ajaxResult.put("visitList", visitList);
        return ajaxResult;
    }

    @Override
    public AjaxResult selectYearVisitCount(Long communityId, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
        List<Integer> dayData = Lists.newArrayList();
        for (int i = 1; i <= 12; i++) {
            dayData.add(i);
        }
        List<Integer> visitList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, i);
            VisitingSecord visitingSecord = new VisitingSecord();
            visitingSecord.setStartTime(sdf.format(cal.getTime()));
            visitingSecord.setCommunityId(communityId);
            visitList.add(screenMapper.selectYearVisitCount(visitingSecord));
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("dayData", dayData);
        ajaxResult.put("visitList", visitList);
        return ajaxResult;
    }

    @Override
    public CommunityCount selectCommunityCount(Long communityId) {
        return screenMapper.selectCommunityCount(communityId);
    }

    @Override
    public CommunityDetail selectCommunityDetail(Long communityId) {
        return screenMapper.selectCommunityDetail(communityId);
    }

    @Override
    public List<NoticeVo> selectNotice(Long communityId) {
        return screenMapper.selectNotice(communityId);
    }

    @Override
    public List<InvestVo> selectInvest(Long communityId) {
        return screenMapper.selectInvest(communityId);
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
            String data = "";
            if (i < 9) {
                data += currentYear + "-0" + (i + 1);
            } else {
                data += currentYear + "-" + (i + 1);
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

    private void monthVisitData(List<MonthVisit> monthVisits, int max, List<Integer> list) {
        for (int i = 0; i < max; i++) {
            for (MonthVisit mv : monthVisits) {
                int current = list.get(i);
                if (Objects.equals(mv.getDay(), String.valueOf(i + 1))) {
                    list.set(i, current + mv.getNum());
                    break;
                }
            }
        }
    }

    private static String desensitizeName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return fullName;  // 如果姓名为空或null，直接返回，不做处理
        }
        // 分割姓名，获取第一个字（姓）和最后一个字（名）
        String[] nameParts = fullName.split("(?<=.)");
        String firstCharacter = nameParts[0];
        String lastCharacter = "";
        if (nameParts.length > 2) {
            lastCharacter = nameParts[nameParts.length - 1];
        }
        // 计算需要替换的起始位置和长度
        int start = firstCharacter.length();
        int end = fullName.length() - lastCharacter.length();
        // 使用 overlay 方法进行脱敏处理，中间部分替换为星号
        return StringUtils.overlay(fullName, "*", start, end);
    }
}
