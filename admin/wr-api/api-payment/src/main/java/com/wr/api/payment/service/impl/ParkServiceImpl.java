package com.wr.api.payment.service.impl;

import com.wr.api.payment.mapper.CommunityMapper;
import com.wr.api.payment.mapper.ParkMapper;
import com.wr.api.payment.mapper.TableMapper;
import com.wr.api.payment.mapper.VehicleLogMapper;
import com.wr.api.payment.service.ParkService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.aiot.Park;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.financial.Duration;
import com.wr.remote.govern.manage.VehicleLog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 19:30:59
 * @Desc: 车库业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ParkServiceImpl implements ParkService {
    private static final Logger log = LoggerFactory.getLogger(ParkServiceImpl.class);

    private final ParkMapper parkMapper;
    private final TableMapper tableMapper;
    private final CommunityMapper communityMapper;
    private final VehicleLogMapper vehicleLogMapper;

    @Override
    public AjaxResult getNewestVehicleLog(Long parkId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //根据车库id查询数据
        Park park = parkMapper.selectById(parkId);
        if(null == park){
            log.error("车库id={}未查询到",parkId);
            return AjaxResult.error("车库信息未查询到");
        }
        if("1".equals(park.getParkType())){
            return AjaxResult.error("从大门出，无需缴费");
        }

        String tableName = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG, park.getCommunityId());
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            return AjaxResult.error("未查询到停车信息");
        }
        tableName = TableConstants.LOG_DB_NAME+"."+tableName;
        //根据停车库查询最新出去的记录
        VehicleLog vehicleLog = vehicleLogMapper.getNewestOutLogByParkIndex(tableName,park.getIndexCode());
        if(null == vehicleLog){
            return AjaxResult.error("未查询到停车出场信息，请联系管理员");
        }
        if("1".equals(vehicleLog.getPayStatus())){
            return AjaxResult.error("未查询到停车出场信息，请联系管理员");
        }
        ajaxResult.put("outLogId",vehicleLog.getLogId());
        ajaxResult.put("outTableName",tableName);
        //根据停车牌查询最新进入的记录
        log.info("最新进入的记录==>tableName={}",tableName);
        VehicleLog inLog = getInLogData(tableName, park.getCommunityId(), vehicleLog.getPlateNo());
        if(null == inLog){
            return AjaxResult.error("未查询到停车进场信息，请联系管理员");
        }
        if("1".equals(inLog.getPayStatus())){
            return AjaxResult.error("未查询到停车进场信息，请联系管理员");
        }
        ajaxResult.put("inLogId",inLog.getLogId());
        ajaxResult.put("inTableName",tableName);
        //小区
        ajaxResult.put("communityId",park.getCommunityId());
        ajaxResult.put("communityName",park.getCommunityName());
        //用户类型 0=业主，1=租客，2=临时车
        ajaxResult.put("userType",vehicleLog.getUserType());
        //用户姓名
        ajaxResult.put("userName",vehicleLog.getUserName());
        //用户手机号
        ajaxResult.put("userPhone",vehicleLog.getUserPhone());
        //车牌号
        ajaxResult.put("plateNo",vehicleLog.getPlateNo());
        //查询车位数量，有则无需缴费
        int count = parkMapper.selectCountByPlateNo(vehicleLog.getPlateNo());
        if(count > 0){
            return AjaxResult.error("非临时车，无需缴费");
        }
        //车道
        ajaxResult.put("roadwayCode",vehicleLog.getRoadwayIndex());
        //查询时长
        List<Duration> durations = parkMapper.selectDurationByCommunityId(park.getCommunityId());
        //免费时长
        int freeHour = getFreeHour(durations);
        ajaxResult.put("freeDuration",freeHour * 60);
        ajaxResult.put("entranceTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inLog.getInoutTime()));
        //总时长(页面显示)
        ajaxResult.put("tempDuration",DateUtils.getDatePoor(vehicleLog.getInoutTime(), inLog.getInoutTime()));
        //总金额
        BigDecimal totalAmount = getTotalAmount(inLog.getInoutTime(), vehicleLog.getInoutTime(), durations);
        ajaxResult.put("totalAmount",totalAmount);
        List<CommunityWechat> wechatList = communityMapper.selectWechatByCommunityId(park.getCommunityId());
        ajaxResult.put("wechatList",wechatList);
        List<CommunityAli> aliList = communityMapper.selectAliByCommunityId(park.getCommunityId());
        ajaxResult.put("aliList",aliList);
        return ajaxResult ;
    }

    /**
     * 查询进车库数据
     * @param tableName 表名
     * @param communityId 小区id
     * @param plantNo 车牌号
     * @return com.wr.remote.govern.manage.VehicleLog
     */
    private VehicleLog getInLogData(String tableName, Long communityId, String plantNo){
        VehicleLog nameLog = vehicleLogMapper.getNewestInLogByPlateNo(tableName,plantNo);
        if(null != nameLog){
            return nameLog;
        }
        Calendar cal = Calendar.getInstance();
        for(int i=0;i<12;i++){
            cal.add(Calendar.MONTH,-i);
            String tempTable = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG,communityId,cal.getTime());
            int num = tableMapper.existTable(tempTable);
            if(num == 0){
                continue;
            }
            String table = TableConstants.LOG_DB_NAME + "." +tempTable;
            VehicleLog vehicleLog = vehicleLogMapper.getNewestInLogByPlateNo(table, plantNo);
            if(null != vehicleLog){
                return vehicleLog;
            }
        }
        return null;
    }

    private Integer getFreeHour(List<Duration> durations) {
        // 未设置时长
        if(durations.isEmpty()){
            return 0;
        }
        //获取收费0元的最大时长
        for (Duration duration : durations) {
            if(BigDecimal.ZERO.equals(duration.getHourFee())) {
                return duration.getMaxHour();
            }
        }
        return 0;
    }

    private BigDecimal getTotalAmount(Date inTime, Date outTime, List<Duration> durations){
        // 获取已停车小时数
//        long diff = DateUtil.between(inTime, outTime, DateUnit.HOUR);
        long millis = outTime.getTime() - inTime.getTime();
        float diff = millis / 1000.0f / 60 / 60;
        log.info("millis:{},diff:{}",millis,diff);
        BigDecimal amount = BigDecimal.ZERO;
        float remain = 0f;
        for (Duration duration : durations) {
            log.info("{}至{}小时，每小时收费{}元===>", duration.getMinHour(), duration.getMaxHour(), duration.getHourFee());
            log.info("remain:{}",remain);
            if (remain == 0) {
                remain = diff - duration.getMaxHour();
                amount = new BigDecimal(duration.getMaxHour()).multiply(duration.getHourFee());
            } else if (null != duration.getMaxHour()){
                if (remain <= (duration.getMaxHour() - duration.getMinHour())) {
                    amount = new BigDecimal(remain).multiply(duration.getHourFee()).add(amount);
                    break;
                } else {
                    int temp = duration.getMaxHour() - duration.getMinHour();
                    remain = remain - temp;
                    amount = new BigDecimal(temp).multiply(duration.getHourFee()).add(amount);
                }
            } else {
                amount = new BigDecimal(remain).multiply(duration.getHourFee()).add(amount);
                break;
            }
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Park selectById(Long parkId) {

        return parkMapper.selectById(parkId);
    }


}
