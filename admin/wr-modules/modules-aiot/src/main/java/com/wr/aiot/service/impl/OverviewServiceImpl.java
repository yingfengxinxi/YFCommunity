package com.wr.aiot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.entity.VehicleData;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.OverviewMapper;
import com.wr.aiot.mapper.TableMapper;
import com.wr.aiot.service.OverviewService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.model.GarageNodeSearch;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.Community;
import io.seata.common.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: RainCity
 * @Date: 2022-11-01 16:16:52
 * @Desc: 停车场数据总览业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OverviewServiceImpl implements OverviewService {
    private final CommunityMapper communityMapper;
    private final TableMapper tableMapper;
    private final OverviewMapper overviewMapper;


    @Override
    public JSONObject selectMonthParkData() {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        List<Community> communities = communityMapper.selectCommunityList(estateId);
        if(communities.isEmpty()){
            return new JSONObject();
        }
        // 今年数据
        Calendar thisCal = Calendar.getInstance();
        JSONObject thisData = getYearData(communities, thisCal);
        // 去年数据
        Calendar lastCal = Calendar.getInstance();
        lastCal.add(Calendar.YEAR,-1);
        JSONObject lastData = getYearData(communities, lastCal);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thisData",thisData);
        jsonObject.put("lastData",lastData);
        return jsonObject;
    }

    /**
     * 查询一至十二月份的停车费总和
     * @param communities 小区集
     * @param calendar 要查询的月份
     * @return com.alibaba.fastjson2.JSONObject
     */
    private JSONObject getYearData(List<Community> communities, Calendar calendar){
        BigDecimal zero = BigDecimal.ZERO;
        JSONObject data = new JSONObject();
        data.put("janData", zero);
        data.put("febData", zero);
        data.put("marData", zero);
        data.put("aprData", zero);
        data.put("mayData", zero);
        data.put("junData", zero);
        data.put("julData", zero);
        data.put("augData", zero);
        data.put("sepData", zero);
        data.put("octData", zero);
        data.put("novData", zero);
        data.put("decData", zero);
        communities.forEach(community ->{
            // 一月
            calendar.set(Calendar.MONTH,Calendar.JANUARY);
            getTotalAmount(community, calendar, data, "janData");
            // 二月
            calendar.set(Calendar.MONTH,Calendar.FEBRUARY);
            getTotalAmount(community, calendar, data, "febData");
            // 三月
            calendar.set(Calendar.MONTH,Calendar.MARCH);
            getTotalAmount(community, calendar, data, "marData");
            // 四月
            calendar.set(Calendar.MONTH,Calendar.APRIL);
            getTotalAmount(community, calendar, data, "aprData");
            // 五月
            calendar.set(Calendar.MONTH,Calendar.MAY);
            getTotalAmount(community, calendar, data, "mayData");
            // 六月
            calendar.set(Calendar.MONTH,Calendar.JUNE);
            getTotalAmount(community, calendar, data, "junData");
            // 七月
            calendar.set(Calendar.MONTH,Calendar.JULY);
            getTotalAmount(community, calendar, data, "julData");
            // 八月
            calendar.set(Calendar.MONTH,Calendar.AUGUST);
            getTotalAmount(community, calendar, data, "augData");
            // 九月
            calendar.set(Calendar.MONTH,Calendar.SEPTEMBER);
            getTotalAmount(community, calendar, data, "sepData");
            // 十月
            calendar.set(Calendar.MONTH,Calendar.OCTOBER);
            getTotalAmount(community, calendar, data, "octData");
            // 十一月
            calendar.set(Calendar.MONTH,Calendar.NOVEMBER);
            getTotalAmount(community, calendar, data, "novData");
            // 十二月
            calendar.set(Calendar.MONTH,Calendar.DECEMBER);
            getTotalAmount(community, calendar, data, "decData");
        });
        return data;
    }

    private void getTotalAmount(Community community, Calendar calendar, JSONObject jsonObject, String monthKey){
        String table = TableUtils.getTableName(TableConstants.PREFIX_PAY_LOG,community.getCommunityId(), calendar.getTime());
        int exist = tableMapper.existTable(table);
        if(exist > 0){
            String tableName = TableConstants.LOG_DB_NAME + "." + table;
            BigDecimal temp = overviewMapper.selectTotalAmount(tableName);
            BigDecimal dataAmount = jsonObject.getBigDecimal(monthKey);
            dataAmount = ObjectUtils.isEmpty(dataAmount)?BigDecimal.ZERO:dataAmount;
            temp = ObjectUtils.isEmpty(temp)?BigDecimal.ZERO:temp;
            jsonObject.put(monthKey, dataAmount.add(temp));
        }
    }

    @Override
    public JSONObject selectVehicleLogData(String inoutTime, String monthType) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        List<Community> communities = communityMapper.selectCommunityList(estateId);
        if(communities.isEmpty()){
            return new JSONObject();
        }
        int year = Integer.parseInt(inoutTime.split("-")[0]);
        int month = Integer.parseInt(inoutTime.split("-")[1]) - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        int max = calendar.getActualMaximum(Calendar.DATE);
        int firstHarf = max / 2;
        List<Integer> inParkArray = Lists.newArrayList();
        List<Integer> outParkArray = Lists.newArrayList();
        JSONArray dayArray = new JSONArray();
        initDataArray(max,firstHarf,monthType,dayArray,inParkArray,outParkArray);
        communities.forEach(community->{
            String tableName = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, community.getCommunityId(), inoutTime);
            int exist = tableMapper.existTable(tableName);
            if(exist > 0){
                if("0".equals(monthType)){
                    List<VehicleData> inList = overviewMapper.selectFirstHarfVehicleData(TableConstants.LOG_DB_NAME + "." + tableName, firstHarf, "0");
                    List<VehicleData> outList = overviewMapper.selectFirstHarfVehicleData(TableConstants.LOG_DB_NAME + "." + tableName, firstHarf,"1");
                    getAllVehicleData(firstHarf, 0, inList, outList, inParkArray, outParkArray);
                }else {
                    List<VehicleData> inList = overviewMapper.selectSecHarfVehicleData(TableConstants.LOG_DB_NAME + "." + tableName, firstHarf+1,"0");
                    List<VehicleData> outList = overviewMapper.selectSecHarfVehicleData(TableConstants.LOG_DB_NAME + "." + tableName, firstHarf+1,"1");
                    getAllVehicleData(max-firstHarf, firstHarf, inList, outList, inParkArray, outParkArray);
                }
            }
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dayArray", dayArray);
        jsonObject.put("inParkArray", inParkArray);
        jsonObject.put("outParkArray", outParkArray);
        return jsonObject;
    }

    private static void initDataArray(int max, int firstHarf, String monthType, JSONArray dayArray,
                                      List<Integer> inParkArray,List<Integer> outParkArray){
        if("0".equals(monthType)){
            initDayArray(1, firstHarf, dayArray, inParkArray, outParkArray);
        } else {
            initDayArray(firstHarf+1, max, dayArray, inParkArray, outParkArray);
        }
    }

    private static void initDayArray(int start, int end, JSONArray dayArray, List<Integer> inParkArray,List<Integer> outParkArray){
        for (int i = start; i <= end; i++) {
            dayArray.add(i);
            inParkArray.add(0);
            outParkArray.add(0);
        }
    }

    private static void getAllVehicleData(int end, int val, List<VehicleData> inList,List<VehicleData> outList,
                                         List<Integer> inParkArray,List<Integer> outParkArray){
        for (int i = 0; i < end; i++) {
            int inCount = 0;
            for (VehicleData data : inList) {
                if((i+val+1) == data.getDay()){
                    inCount = data.getInoutTimes();
                    break;
                }
            }
            inParkArray.set(i,inParkArray.get(i) + inCount);
            int outCount = 0;
            for (VehicleData data : outList) {
                if((i+val+1) == data.getDay()){
                    outCount = data.getInoutTimes();
                    break;
                }
            }
            outParkArray.set(i,outParkArray.get(i) + outCount);
        }
    }

    @Override
    public JSONObject selectCarportUseData() {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        // 所有车位
        int carportTotal = overviewMapper.countCarportTotal(estateId);
        // 注册车辆
        int vehicleTotal = overviewMapper.countVehicleTotal(estateId, null);
        // 今日注册车辆
        int vehicleToday = overviewMapper.countVehicleTotal(estateId, new Date());
        // 今日入园车辆
        AtomicInteger vehicleLogToday = new AtomicInteger();
        // 今日外来车辆
        AtomicInteger tempLogToday = new AtomicInteger();
        // 查询小区集
        List<Community> communities = communityMapper.selectCommunityList(estateId);
        communities.forEach(community->{
            String tableName = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG,community.getCommunityId());
            int exist = tableMapper.existTable(tableName);
            if(exist > 0){
                vehicleLogToday.addAndGet(overviewMapper.countVehicleLogToday(TableConstants.LOG_DB_NAME + "." + tableName, null));
                tempLogToday.addAndGet(overviewMapper.countVehicleLogToday(TableConstants.LOG_DB_NAME + "." + tableName, "2"));
            }
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("carportTotal", carportTotal);
        jsonObject.put("vehicleTotal", vehicleTotal);
        jsonObject.put("vehicleToday", vehicleToday);
        jsonObject.put("vehicleLogToday", vehicleLogToday.get());
        jsonObject.put("tempLogToday", tempLogToday.get());
        return jsonObject;
    }

    @Override
    public JSONArray parkList(Long communityId, String parkIndexCodes) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (ObjectUtil.isEmpty(hik)){
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.parkList(hik, parkIndexCodes);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    @Override
    public Boolean deviceControlBatch(Long communityId, String parkSyscode, Integer command) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (ObjectUtil.isEmpty(hik)){
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.deviceControlBatch(hik, parkSyscode,command);
        } catch (Exception e) {
            throw new ServiceException("道闸控制失败");
        }
    }

    @Override
    public Boolean deviceControl(Long communityId, String parkSyscode, Integer command) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (ObjectUtil.isEmpty(hik)) {
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.deviceControl(hik, parkSyscode, command);
        } catch (Exception e) {
            throw new ServiceException("道闸控制失败");
        }
    }

    @Override
    public JSONObject getGarageNode(GarageNodeSearch garageNodeSearch) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(garageNodeSearch.getCommunityId());
        if (ObjectUtil.isEmpty(hik)) {
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.getGarageNode(hik, garageNodeSearch);
        } catch (Exception e) {
            throw new ServiceException("停车场节点信息获取失败");
        }
    }

    @Override
    public JSONObject deviceRelation(Long communityId, String syscode, Integer nodeType, String subNode, String deviceType) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (ObjectUtil.isEmpty(hik)) {
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.deviceRelation(hik, syscode,nodeType,subNode,deviceType);
        } catch (Exception e) {
            throw new ServiceException("查询出入口设备关联关系失败");
        }
    }

    @Override
    public JSONObject resourcesByParams(Long communityId, String name, String resourceType, Integer pageNo, Integer pageSize) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (ObjectUtil.isEmpty(hik)) {
            throw new ServiceException("小区信息不存在");
        }
        try {
            return HaiKUtils.resourcesByParams(hik,name,resourceType,pageNo,pageSize);
        } catch (Exception e) {
            throw new ServiceException("查询资源列表失败");
        }
    }
}
