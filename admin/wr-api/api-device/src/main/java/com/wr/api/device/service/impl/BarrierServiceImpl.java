package com.wr.api.device.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.entity.vo.AccountVO;
import com.wr.api.device.mapper.*;
import com.wr.api.device.service.BarrierService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.aiot.Park;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.BusVehicle;
import com.wr.remote.estate.financial.Duration;
import com.wr.remote.estate.log.PayLog;
import com.wr.remote.govern.manage.VehicleLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 19:38:45
 * @Desc: 车辆事件处理层
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class BarrierServiceImpl implements BarrierService {
    private static final Logger log = LoggerFactory.getLogger(BarrierServiceImpl.class);

    private final BarrierMapper barrierMapper;
    private final UserMapper userMapper;
    private final TableMapper tableMapper;
    private final VehicleLogMapper vehicleLogMapper;
    private final CommunityMapper communityMapper;

    @Override
    @Transactional(readOnly = false)
    public void entryPressureLine(String req) {
        try {
            //事件详情
            JSONObject data =  checkData(req);
            VehicleLog vehicleLog = new VehicleLog();
            //车库
            Park park = checkParking(data, vehicleLog);
            //车位
            BusCarport carport = checkUserInfo(data, vehicleLog);

            String roadwayIndex = setUpVehicleLogData(data, vehicleLog, "0");

            String tableName = checkExistTable(park.getCommunityId());
            vehicleLog.setTableName(tableName);
            //非临时车
            if(null != carport){
                //记录已支付数据
                log.info("非临时车进场===>");
                vehicleLog.setPayStatus("1");
                vehicleLog.setInoutTime(new Date());
                vehicleLog.setInoutOpen(new Date());
                vehicleLogMapper.insertVehicleLog(vehicleLog);
            }
            //临时车
            else {
                //从大门进入
                if("1".equals(park.getParkType())){
                    log.info("临时车进大门===>");
                    vehicleLog.setPayStatus("1");
                }else {
                    log.info("临时车进车库===>");
                    vehicleLog.setPayStatus("0");
                }
                vehicleLog.setInoutTime(new Date());
                vehicleLog.setInoutOpen(new Date());
                vehicleLogMapper.insertVehicleLog(vehicleLog);
            }
            //开门
           // roadwayControl(park.getCommunityId(), roadwayIndex);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void outPressureLine(String req) {
        try {
            //事件详情
            JSONObject data = checkData(req);
            VehicleLog vehicleLog = new VehicleLog();
            //车库
            Park park = checkParking(data, vehicleLog);
            //车位
            BusCarport carport = checkUserInfo(data, vehicleLog);
            //车道
            String roadwayIndex = setUpVehicleLogData(data, vehicleLog, "1");
            String tableName = checkExistTable(park.getCommunityId());
            vehicleLog.setTableName(tableName);
            //缴费开关
            String switchType = barrierMapper.getSwitchTypeByCommunityId(park.getCommunityId());
            //从大门出
            if("1".equals(park.getParkType())){
                //记录已支付数据
                //非临时车
                if(null != carport){
                    log.info("非临时车有车位，出大门，开门===>");
                    addVehicleLog(vehicleLog, "1", new Date(), new Date());
                    //开门
                    //roadwayControl(park.getCommunityId(), roadwayIndex);
                } else {
                    log.info("非临时车，无车位，出大门===>");
                    addVehicleLog(vehicleLog, "1", new Date(), new Date());
                    if("1".equals(switchType)){
                        //开门
                        //roadwayControl(park.getCommunityId(), roadwayIndex);
                    }
                }
                return;
            }
            //从车库出
            outFromCarport(carport, vehicleLog, switchType, park);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

    }

    /**
     * 从车库出
     * @param carport 车位
     * @param vehicleLog 车行日志
     * @param switchType 缴费开关
     * @param park 停车场
     */
    private void outFromCarport(BusCarport carport, VehicleLog vehicleLog, String switchType, Park park){
        //非临时车
        if(null != carport){
            log.info("非临时车出车库，开闸===>");
            addVehicleLog(vehicleLog, "1", new Date(), new Date());
            //roadwayControl(park.getCommunityId(), roadwayIndex);
            return;
        }
        //临时车
        log.info("临时车出车库===>");
        if("1".equals(switchType)){
            log.info("不收费，直接开门===>");
            addVehicleLog(vehicleLog, "0", new Date(), null);
            //roadwayControl(parking.getCommunityId(), roadwayIndex);
            return;
        }
        //进库记录
        VehicleLog inLog = getInLogData(park.getCommunityId(), vehicleLog.getPlateNo());
        if(null == inLog || "0".equals(inLog.getPayStatus())){
            addVehicleLog(vehicleLog, "0", new Date(), null);
            if(null == inLog){
                log.info("无进库记录，不开闸===>");
            }else if("0".equals(inLog.getPayStatus())){
                log.info("有未支付进库数据，不开闸===>");
            }
            return;
        }
        //查询时长
        List<Duration> durations = barrierMapper.selectDurationByCommunityId(park.getCommunityId());
        // 无时长
        if(durations.isEmpty()){
            addVehicleLog(vehicleLog, "1", new Date(), new Date());
            //roadwayControl(parking.getCommunityId(), roadwayIndex);
            return;
        }
        log.info("有已支付进库数据===>");
        PayLog payLog = getNewestPayLog(park.getCommunityId(), vehicleLog.getPlateNo());
        if(null == payLog){
            log.info("未查询到最新的支付记录===>");
            return;
        }
        // 获取已停车小时数
        long diff = DateUtil.between(payLog.getPayTime(), new Date(), DateUnit.HOUR);
        if(durations.get(0).getMinHour() > diff){
            log.info("在免费时长内===>开闸");
            addVehicleLog(vehicleLog, "1", new Date(), new Date());
            //roadwayControl(parking.getCommunityId(), roadwayIndex);
            return;
        }
        log.info("不在免费时长内,计算金额===>");
        BigDecimal amount = BigDecimal.ZERO;
        long remain = 0L;
        for (Duration duration : durations) {
            log.info("{}至{}小时，每小时收费{}元===>", duration.getMinHour(), duration.getMaxHour(), duration.getHourFee());
            if (remain == 0) {
                remain = diff - duration.getMaxHour();
                amount = new BigDecimal(duration.getMaxHour()).multiply(duration.getHourFee());
            } else {
                if (remain <= (duration.getMaxHour() - duration.getMinHour())) {
                    amount = new BigDecimal(remain).multiply(duration.getHourFee()).add(amount);
                    break;
                } else {
                    int temp = duration.getMaxHour() - duration.getMinHour();
                    remain = remain - temp;
                    amount = new BigDecimal(temp).multiply(duration.getHourFee()).add(amount);
                }
            }
        }
        if(amount.compareTo(BigDecimal.ZERO) == 0){
            log.info("无需收费===>开闸");
            addVehicleLog(vehicleLog, "1", new Date(), new Date());
            //roadwayControl(parking.getCommunityId(), roadwayIndex);
            return;
        }
        log.info("需收费{}元===>等待支付后开闸", amount);
        addVehicleLog(vehicleLog, "0", new Date(), null);
    }

    /**
     * 检查数据
     * @param req
     * @return
     */
    private static JSONObject checkData(String req) throws NullPointerException{
        if(StringUtils.isEmpty(req)){
            log.error("压线接收数据为空");
            throw new NullPointerException("压线接收数据为空");
        }
        JSONObject reqBody = JsonUtil.parseObject(req);
        if(null == reqBody){
            throw new NullPointerException("压线接收数据为空");
        }
        JSONObject params = reqBody.getJSONObject("params");
        if(null == params){
            log.error("事件参数信息为空");
            throw new NullPointerException("事件参数信息为空");
        }
        JSONArray events = params.getJSONArray("events");
        if(events.isEmpty()){
            log.error("事件信息为空");
            throw new NullPointerException("事件信息为空");
        }
        return events.getJSONObject(0).getJSONObject("data");
    }

    /**
     * 检查车库信息
     * @param data
     * @param vehicleLog
     * @return
     * @throws NullPointerException
     */
    private Park checkParking(JSONObject data, VehicleLog vehicleLog) throws NullPointerException{
        //停车库编号
        String parkIndex = data.getString("parkIndex");
        String parkName = data.getString("parkName");
        log.info("parkIndex={},parkName={}",parkIndex,parkName);
        //查询停车库信息
        Park park = barrierMapper.getByParkIndex(parkIndex);
        Assert.notNull(park, "未查询到停车场信息");
        Long communityId = park.getCommunityId();
        Assert.notNull(communityId, "车库未绑定小区信息");
        setUpParkInfo(park, vehicleLog);
        return park;
    }

    /**
     * 检查人员信息
     * @param data
     * @param vehicleLog
     * @return 车位
     * @throws NullPointerException
     */
    private BusCarport checkUserInfo(JSONObject data, VehicleLog vehicleLog) throws NullPointerException{
        //车牌号
        String plateNo = data.getString("plateNo");
        //根据车牌号查询车辆信息
        BusVehicle vehicle = barrierMapper.getByPlateNo(plateNo);
        Assert.notNull(vehicle, "未查询到车辆信息");
        AccountVO account = userMapper.getAccountById(vehicle.getAccountId());
        Assert.notNull(account, "未查询到人员信息");
        if("1".equals(account.getUserType())) {
            vehicleLog.setUserType("0");
        } else if("2".equals(account.getUserType())) {
            vehicleLog.setUserType("1");
        }else {
            vehicleLog.setUserType("2");
        }
        setUserInfo(account, vehicleLog);
        return barrierMapper.getCarportByAccountId(account.getAccountId());
    }
    /**
     * 设置停车场信息
     * @param park 车库
     * @param vehicleLog 行车记录
     * @return
     */
    private void setUpParkInfo(Park park, VehicleLog vehicleLog){
        vehicleLog.setParkIndex(park.getIndexCode());
        vehicleLog.setParkName(park.getParkName());
        vehicleLog.setParkType(park.getParkType());
        vehicleLog.setCommunityId(park.getCommunityId());
        vehicleLog.setCommunityName(park.getCommunityName());
    }

    private void setUserInfo(AccountVO account,VehicleLog vehicleLog){
        vehicleLog.setUserName(account.getUserName());
        vehicleLog.setUserPhone(account.getUserPhone());
        vehicleLog.setIdCardNo(account.getCardNo());
    }

    private String setUpVehicleLogData(JSONObject data, VehicleLog vehicleLog, String inoutType){
        setUpEntranceInfo(data, vehicleLog);
        String roadwayIndex = setUpRoadwayInfo(data, vehicleLog);
        setUpVehicleInfo(data, vehicleLog);
        String plateNo = data.getString("plateNo");
        vehicleLog.setPlateNo(plateNo);
        vehicleLog.setInoutType(inoutType);
        vehicleLog.setPayStatus("0");
        return roadwayIndex;
    }

    /**
     * 设置出入口信息
     * @param data
     * @param vehicleLog
     */
    private void setUpEntranceInfo(JSONObject data, VehicleLog vehicleLog){
        //出入口编号
        String gateIndex = data.getString("gateIndex");
        String gateName = data.getString("gateName");
        vehicleLog.setGateIndex(gateIndex);
        vehicleLog.setGateName(gateName);
    }

    /**
     * 设置车道信息
     * @param data
     * @param vehicleLog
     */
    private String setUpRoadwayInfo(JSONObject data, VehicleLog vehicleLog){
        //车道编号
        String roadwayIndex = data.getString("roadwayIndex");
        String roadwayName = data.getString("roadwayName");
        vehicleLog.setRoadwayIndex(roadwayIndex);
        vehicleLog.setRoadwayName(roadwayName);
        return roadwayIndex;
    }

    /**
     * 设置车牌信息
     * @param data
     * @param vehicleLog
     */
    private void setUpVehicleInfo(JSONObject data, VehicleLog vehicleLog){
        //车牌信息
        int plateType = data.getInteger("plateType");
        int plateColor = data.getInteger("plateColor");
        int vehicleType = data.getInteger("vehicleType");
        int vehicleColor = data.getInteger("vehicleColor");
        vehicleLog.setPlateType(plateType);
        vehicleLog.setPlateColor(plateColor);
        vehicleLog.setVehicleType(vehicleType);
        vehicleLog.setVehicleColor(vehicleColor);
    }

    private String checkExistTable(Long communityId){
        String table = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG,communityId);
        int num = tableMapper.existTable(table);
        String tableName = TableConstants.LOG_DB_NAME + "." + table;
        if(num == 0){
            tableMapper.createVehicleLogTable(tableName);
        }
        return tableName;
    }

    /**
     * 查询进车库数据
     * @param communityId
     * @param plantNo
     * @return
     */
    private VehicleLog getInLogData(Long communityId, String plantNo){
        Calendar cal = Calendar.getInstance();
        for(int i=0;i<12;i++){
            cal.add(Calendar.MONTH,-i);
            String table = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG,communityId,cal.getTime());
            int num = tableMapper.existTable(table);
            if(num == 0){
                continue;
            }
            String tableName = TableConstants.LOG_DB_NAME + "." +table;
            VehicleLog vehicleLog = vehicleLogMapper.getNewestInLog(tableName, plantNo);
            if(null != vehicleLog){
                return vehicleLog;
            }
        }
        return null;
    }

    /**
     * 查询今日的缴费记录
     * @param communityId
     * @param plateNo
     * @return
     */
    private PayLog getNewestPayLog(Long communityId, String plateNo){
        //查询支付记录
        String payTableName = TableUtils.getTableName(TableConstants.PREFIX_PAY_LOG,communityId);
        int num = tableMapper.existTable(payTableName);
        if(num == 0){
            return null;
        }
        payTableName = TableConstants.LOG_DB_NAME + "." + payTableName;
        return barrierMapper.selectNewestPayByPlantNo(payTableName,plateNo);
    }

    /**
     * 开门
     * @param communityId 小区id
     * @param roadwayIndex 车道标识
     */
    private void roadwayControl(Long communityId, String roadwayIndex){
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            log.error("请检查小区海康配置后重试===>");
            return ;
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        boolean flag = HikUtils.roadwayDeviceControl(roadwayIndex,1);
        String msg = flag ? "开门成功" : "开门失败";
        log.info(msg);
    }

    private void addVehicleLog(VehicleLog vehicleLog,String payStatus ,Date inoutTime, Date inoutOpen){
        vehicleLog.setPayStatus(payStatus);
        vehicleLog.setInoutTime(inoutTime);
        Optional.ofNullable(inoutOpen).ifPresent(vehicleLog::setInoutOpen);
        vehicleLogMapper.insertVehicleLog(vehicleLog);
    }

}
