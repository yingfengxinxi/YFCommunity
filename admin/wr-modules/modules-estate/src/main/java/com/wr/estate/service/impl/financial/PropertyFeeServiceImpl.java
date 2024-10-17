package com.wr.estate.service.impl.financial;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.PropertyLogCountVo;
import com.wr.estate.mapper.financial.PropertyLogMapper;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.info.NoticeEstateService;
import com.wr.remote.domain.*;
import com.wr.estate.mapper.financial.PropertyFeeMapper;
import com.wr.estate.service.financial.PropertyFeeService;
import com.wr.remote.domain.vo.QuarterPropertyFeeVo;
import com.wr.remote.estate.center.Room;
import com.wr.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeServiceImpl
 * @History:
 */
@Service
@Slf4j
public class PropertyFeeServiceImpl implements PropertyFeeService {

    @Autowired
    private PropertyFeeMapper busPropertyFeeMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PropertyLogMapper propertyLogMapper;

    @Autowired
    private NoticeEstateService noticeEstateService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询物业缴费设置
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 物业缴费设置
     */
    @Override
    public AjaxResult selectBusPropertyFeeByCommunityId(String propertyFeeId) {
        PropertyFeeEntity propertyFeeEntity = busPropertyFeeMapper.selectBusPropertyFeeByCommunityId(propertyFeeId);
        String buildingInfo = busPropertyFeeMapper.getByPropertyFeeIdBuildingInfo(propertyFeeId);
        if (StringUtils.isNotEmpty(buildingInfo)) {
            propertyFeeEntity.setBuildingId(buildingInfo);
        }
        return AjaxResult.success(propertyFeeEntity);
    }

    @Override
    public String getByPropertyFeeIdBuildingInfo(String propertyFeeId) {
        return busPropertyFeeMapper.getByPropertyFeeIdBuildingInfo(propertyFeeId);
    }

    /**
     * 查询物业缴费设置列表
     *
     * @param propertyFee 物业缴费设置
     * @return 物业缴费设置
     */
    @Override
    public List<PropertyFeeEntity> selectBusPropertyFeeList(PropertyFeeEntity propertyFee) {
        List<PropertyFeeEntity> propertyFeeEntities = busPropertyFeeMapper.selectBusPropertyFeeList(propertyFee);
        if (CollectionUtils.isNotEmpty(propertyFeeEntities)) {
            propertyFeeEntities.stream().forEach(propertyFeeEntity -> {
                propertyFeeEntity.setIsUpdate("0");
                Integer payCount = busPropertyFeeMapper.getPropertyIsPayCount(propertyFeeEntity.getPropertyFeeId());
                if (payCount >= 1 && propertyFeeEntity.getStartTime() != null) {
                    propertyFeeEntity.setIsUpdate("1");
                }

                String updateBy = propertyFeeEntity.getUpdateBy();
                if (StringUtils.isNotEmpty(updateBy)) {
                    SysUser user = userMapper.selectUserByUserName(updateBy);
                    propertyFeeEntity.setUpdateBy(user.getNickName());
                }

                String createBy = propertyFeeEntity.getCreateBy();
                if (StringUtils.isNotEmpty(createBy)) {
                    SysUser user = userMapper.selectUserByUserName(createBy);
                    propertyFeeEntity.setCreateBy(user.getNickName());
                }

            });

        }

        return propertyFeeEntities;
    }

    /**
     * @param buildingId
     * @param communityId
     * @return
     */
    @Override
    public Integer getByBuildingIdPropertyFeeCount(Long buildingId, Long communityId) {
        return busPropertyFeeMapper.getByBuildingIdPropertyFeeCount(buildingId, communityId);
    }

    @Override
    public List<PropertyFeeEntity> getAllPropertyFeeList() {
        return busPropertyFeeMapper.getAllPropertyFeeList();
    }

    /**
     * 新增物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    @Override
    public AjaxResult insertBusPropertyFee(PropertyFeeEntity propertyFee) {
        String id = UUID.randomUUID().toString().replace("-", "");
        propertyFee.setPropertyFeeId(id);

        try {
            List<String> buildingIds = Arrays.asList(propertyFee.getBuildingId().split(","));
            Integer count = busPropertyFeeMapper.getCommunityIdFeeTypeCount(propertyFee.getCommunityId(), propertyFee.getFeeType(), propertyFee.getPayMethod(), propertyFee.getPropertyFeeId(), buildingIds);
            if (count >= 1) {
                return AjaxResult.error(510, "当前缴费类型,缴费方式已存在,请勿重复添加");
            }

            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sim.parse(DateUtils.getDate());
//            if (propertyFee.getStartTime().getTime() < parse.getTime()) {
//                return AjaxResult.error(510, "缴费适用开始日期不得小于当前日期");
//            }

            if (propertyFee.getEndTime().getTime() < parse.getTime()) {
                return AjaxResult.error(510, "缴费适用结束日期不得小于当前日期");
            }

            if (propertyFee.getEndTime().getTime() < propertyFee.getStartTime().getTime()) {
                return AjaxResult.error(510, "缴费适用结束日期不得小于缴费适用开始日期");
            }

            propertyFee.setCreateTime(DateUtils.getNowDate());
            propertyFee.setCreateBy(SecurityUtils.getUsername());

            propertyFee.setOrderNo("WYF" + new Date().getTime());
            busPropertyFeeMapper.insertBusPropertyFee(propertyFee);

            if (CollectionUtils.isNotEmpty(buildingIds)) {
                extracted(buildingIds, id);
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

    /**
     * 生成账单
     *
     * @param propertyFee
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generatePropertyBill(PropertyFeeEntity propertyFee, String buildingId) throws Exception {
        //账单生成规则：建筑面积*费用*天数
        //查询交房日期
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date dayDate = sim.parse(DateUtils.getDate());
        List<Room> roomList = roomService.getByBuildingIdRoomList(Long.valueOf(buildingId));
        if (CollectionUtils.isNotEmpty(roomList)) {
            roomList.stream().forEach(room -> {
                try {
                    PropertyLog propertyLog = new PropertyLog();
                    propertyLog.setPropertyFeeId(propertyFee.getPropertyFeeId());
                    propertyLog.setCommunityId(room.getCommunityId());
                    propertyLog.setBuildingId(room.getBuildingId());
                    propertyLog.setUnitId(room.getUnitId());
                    propertyLog.setRoomId(room.getRoomId());
                    propertyLog.setRoomArea(new BigDecimal(room.getTotalArea()));
                    propertyLog.setPayStatus("0");
                    propertyLog.setPayMethod(propertyFee.getPayMethod());
                    propertyLog.setFeeAmount(propertyFee.getFeeAmount());
                    propertyLog.setFeeType(propertyFee.getFeeType());
                    propertyLog.setIsUnpaidPropertyFee("0");

                    //3、根据查询到的房号查询账单表中是否存在数据
                    Date endTime = propertyLogMapper.getByRoomIdEntTime(propertyLog.getCommunityId(), propertyLog.getBuildingId(), propertyLog.getUnitId(), propertyLog.getRoomId());
                    if (endTime != null) {
                        //4.1、判断结束时间是否等于当天时间
                        if (endTime.getTime() == dayDate.getTime()) {
                            //根据上次缴费结束日期生成账单
                            room.setDeliveryTime(dayDate);
                            propertyEntTime(propertyFee, room, propertyLog);
                        }
                        if (endTime.getTime() < dayDate.getTime()) {
                            //结束时间小于当前时间按第一次生成账单规则生成
                            room.setDeliveryTime(endTime);
                            propertyDeliveryTime(propertyFee, room, dayDate, sim, propertyLog);
                        }

                    } else {
                        //第一次根据交房日期生成账单
                        propertyDeliveryTime(propertyFee, room, dayDate, sim, propertyLog);
                    }


                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        //统计今日生成账单数量

        List<PropertyLogCountVo> propertyLogCountList = propertyLogMapper.getByDateCount();
        if (CollectionUtils.isNotEmpty(propertyLogCountList)) {
            propertyLogCountList.stream().forEach(propertyLogCountVo -> {
                NoticeEstate noticeEstate = new NoticeEstate();
                noticeEstate.setEstateId(propertyLogCountVo.getEstateId());
                noticeEstate.setReadStatus("0");
                noticeEstate.setFlag("1");
                noticeEstate.setNoticeTitle(propertyLogCountVo.getCommunityName() + "小区物业费账单已生成,请核对");
                noticeEstateService.save(noticeEstate);
            });
        }

    }

    /**
     * 未交房生成物业费生成账单
     *
     * @param propertyFee
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unpaidPropertyGeneratePropertyBill(PropertyFeeEntity propertyFee, String buildingId) throws Exception {
        //账单生成规则：建筑面积*费用
        //查询交房日期
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date dayDate = sim.parse(DateUtils.getDate());
        List<Room> roomList = roomService.getByBuildingIdNullRoomList(Long.valueOf(buildingId));
        if (CollectionUtils.isNotEmpty(roomList)) {
            roomList.stream().forEach(room -> {
                try {

                    PropertyLog propertyLog = new PropertyLog();
                    propertyLog.setPropertyFeeId(propertyFee.getPropertyFeeId());
                    propertyLog.setCommunityId(room.getCommunityId());
                    propertyLog.setBuildingId(room.getBuildingId());
                    propertyLog.setUnitId(room.getUnitId());
                    propertyLog.setRoomId(room.getRoomId());
                    propertyLog.setRoomArea(new BigDecimal(room.getTotalArea()));
                    propertyLog.setPayStatus("0");
                    propertyLog.setPayMethod(propertyFee.getPayMethod());
                    propertyLog.setFeeAmount(propertyFee.getFeeAmount());
                    propertyLog.setFeeType(propertyFee.getFeeType());
                    propertyLog.setIsUnpaidPropertyFee("1");

                    //3、根据查询到的房号查询账单表中是否存在数据
                    Date endTime = propertyLogMapper.getByRoomIdEntTime(propertyLog.getCommunityId(), propertyLog.getBuildingId(), propertyLog.getUnitId(), propertyLog.getRoomId());
                    if (endTime != null) {
                        //4.1、判断结束时间是否等于当天时间
                        if (endTime.getTime() == dayDate.getTime()) {
                            //根据上次缴费结束日期生成账单
                            room.setDeliveryTime(dayDate);
                            propertyEntTime(propertyFee, room, propertyLog);
                        }
                        if (endTime.getTime() < dayDate.getTime()) {
                            //结束时间小于当前时间按第一次生成账单规则生成
                            room.setDeliveryTime(endTime);
                            propertyDeliveryTime(propertyFee, room, dayDate, sim, propertyLog);
                        }

                    } else {
                        //第一次根据交房日期生成账单
                        room.setDeliveryTime(propertyFee.getStartTime());
                        propertyDeliveryTime(propertyFee, room, dayDate, sim, propertyLog);
                    }


                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        //统计今日生成账单数量

        List<PropertyLogCountVo> propertyLogCountList = propertyLogMapper.getByDateCount();
        if (CollectionUtils.isNotEmpty(propertyLogCountList)) {
            propertyLogCountList.stream().forEach(propertyLogCountVo -> {
                NoticeEstate noticeEstate = new NoticeEstate();
                noticeEstate.setEstateId(propertyLogCountVo.getEstateId());
                noticeEstate.setReadStatus("0");
                noticeEstate.setFlag("1");
                noticeEstate.setNoticeTitle(propertyLogCountVo.getCommunityName() + "小区物业费账单已生成,请核对");
                noticeEstateService.save(noticeEstate);
            });
        }

    }

    /**
     * 第二次生成订单使用
     *
     * @param propertyFee
     * @param room
     * @param propertyLog
     * @throws ParseException
     */

    private void propertyEntTime(PropertyFeeEntity propertyFee, Room room, PropertyLog propertyLog) throws ParseException {
        Date deliveryTime = room.getDeliveryTime();
        BigDecimal totalArea = new BigDecimal(room.getTotalArea());
        //0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
        String payMethod = propertyFee.getPayMethod();
        //缴费金额
        BigDecimal money = new BigDecimal("0.00");
        if (StringUtils.equals(payMethod, "0")) {
            //按月缴费
            System.out.println("START按月缴费2...");
            payMonth01(propertyFee, deliveryTime, totalArea, propertyLog);
            System.out.println("END按月缴费2...");
        }
        if (StringUtils.equals(payMethod, "1")) {
            //按季度
            payMonth11(propertyFee, deliveryTime, totalArea, propertyLog);
        }
        if (StringUtils.equals(payMethod, "2")) {
            //按半年
            payMonth21(propertyFee, deliveryTime, totalArea, propertyLog);
        }
        if (StringUtils.equals(payMethod, "3")) {
            //按年
            payMonth31(propertyFee, deliveryTime, totalArea, propertyLog);
        }
    }


    /**
     * 按年缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param totalArea
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth31(PropertyFeeEntity propertyFee, Date deliveryTime, BigDecimal totalArea, PropertyLog propertyLog) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = DateUtils.stepDay(deliveryTime, 1);
        Date endTime = DateUtils.stepMonth(deliveryTime, 12);
        int year = Integer.parseInt(sim.format(endTime).split("-")[0]);
        int month = Integer.parseInt(sim.format(endTime).split("-")[1]);
        endTime = DateUtils.fastMonthDay(year, month);
        //计算当前时间到月底相差几天
        Long dayCount = DateUtils.getDayCount(deliveryTime, endTime);
        //建筑面积*费用
        BigDecimal money = new BigDecimal("0.00");
        money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
        if (dayCount > 0) {
            PropertyLog pl = new PropertyLog();
            BeanUtil.copyProperties(propertyLog, pl, true);
            pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
            pl.setStartTime(startTime);
            pl.setEndTime(endTime);
            pl.setYjAmount(money);
            pl.setPaidAmount(money);
            //保存数据
            savePropertyLog(pl);
        }
    }

    /**
     * 计算物业费
     *
     * @param propertyFee
     * @param money
     * @param totalArea
     * @param propertyFee
     * @param dayCount
     * @return
     */
    @NotNull
    private static BigDecimal getCalculateAmountPropertyFee(PropertyFeeEntity propertyFee, BigDecimal money, BigDecimal totalArea, Long dayCount) {
        if (StringUtils.equals(propertyFee.getFeeType(), "0")) {
            //面积收费
            //money = money.add(totalArea.multiply(propertyFee.getFeeAmount()).multiply(new BigDecimal(dayCount)));
            //0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
            money = money.add(totalArea.multiply(propertyFee.getFeeAmount()));

            if (StringUtils.equals(propertyFee.getPayMethod(), "1")) {
                money = money.add(totalArea.multiply(propertyFee.getFeeAmount()).multiply(new BigDecimal("3")));
            }

            if (StringUtils.equals(propertyFee.getPayMethod(), "2")) {
                money = money.add(totalArea.multiply(propertyFee.getFeeAmount()).multiply(new BigDecimal("6")));
            }

            if (StringUtils.equals(propertyFee.getPayMethod(), "3")) {
                money = money.add(totalArea.multiply(propertyFee.getFeeAmount()).multiply(new BigDecimal("12")));
            }

            log.info("面积收费计算物业费:面积{},*物业费{},等于{}", totalArea, propertyFee.getFeeAmount(), money);
        } else {
            //统一收费
            //money = money.add(propertyFee.getFeeAmount().multiply(new BigDecimal(dayCount)));
            money = propertyFee.getFeeAmount();
            log.info("统一收费计算物业费:{}", money);
        }
        return money;
    }

    /**
     * 按半年缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param totalArea
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth21(PropertyFeeEntity propertyFee, Date deliveryTime, BigDecimal totalArea, PropertyLog propertyLog) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = DateUtils.stepDay(deliveryTime, 1);
        Date endTime = DateUtils.stepMonth(deliveryTime, 6);
        int year = Integer.parseInt(sim.format(endTime).split("-")[0]);
        int month = Integer.parseInt(sim.format(endTime).split("-")[1]);
        endTime = DateUtils.fastMonthDay(year, month);
        //计算当前时间到月底相差几天
        Long dayCount = DateUtils.getDayCount(deliveryTime, endTime);
        //建筑面积*费用
        BigDecimal money = new BigDecimal("0.00");
        money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
        if (dayCount > 0) {
            PropertyLog pl = new PropertyLog();
            BeanUtil.copyProperties(propertyLog, pl, true);
            pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
            pl.setStartTime(startTime);
            pl.setEndTime(endTime);
            pl.setYjAmount(money);
            pl.setPaidAmount(money);
            //保存数据
            savePropertyLog(pl);
        }

    }

    /**
     * 按季度缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param totalArea
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth11(PropertyFeeEntity propertyFee, Date deliveryTime, BigDecimal totalArea, PropertyLog propertyLog) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = DateUtils.stepDay(deliveryTime, 1);
        Date endTime = DateUtils.stepMonth(deliveryTime, 3);
        int year = Integer.parseInt(sim.format(endTime).split("-")[0]);
        int month = Integer.parseInt(sim.format(endTime).split("-")[1]);
        endTime = DateUtils.fastMonthDay(year, month);
        //计算当前时间到月底相差几天
        Long dayCount = DateUtils.getDayCount(deliveryTime, endTime);
        BigDecimal money = new BigDecimal("0.00");
        money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
        if (dayCount > 0) {
            PropertyLog pl = new PropertyLog();
            BeanUtil.copyProperties(propertyLog, pl, true);
            pl.setStartTime(startTime);
            pl.setEndTime(endTime);
            pl.setYjAmount(money);
            pl.setPaidAmount(money);
            pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
            //保存数据
            savePropertyLog(pl);
        }
    }


    /**
     * 按月缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param totalArea
     * @param propertyLog
     */
    private void payMonth01(PropertyFeeEntity propertyFee, Date deliveryTime, BigDecimal totalArea, PropertyLog propertyLog) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = DateUtils.stepDay(deliveryTime, 1);
        Date endTime = DateUtils.stepMonth(deliveryTime, 1);
        int year = Integer.parseInt(sim.format(endTime).split("-")[0]);
        int month = Integer.parseInt(sim.format(endTime).split("-")[1]);
        endTime = DateUtils.fastMonthDay(year, month);
        //计算当前时间到月底相差几天
        Long dayCount = DateUtils.getDayCount(deliveryTime, endTime);
        //建筑面积*费用
        BigDecimal money = new BigDecimal("0.00");
        money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
        if (dayCount > 0) {
            PropertyLog pl = new PropertyLog();
            BeanUtil.copyProperties(propertyLog, pl, true);
            pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
            pl.setStartTime(startTime);
            pl.setEndTime(endTime);
            pl.setYjAmount(money);
            pl.setPaidAmount(money);
            SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("2周期:" + sim1.format(pl.getStartTime()) + ">>" + sim1.format(pl.getEndTime()));
            savePropertyLog(pl);
        }

    }


    /**
     * 第一次生成订单使用
     *
     * @param propertyFee
     * @param room
     * @param dayDate
     * @param sim
     * @param propertyLog
     * @throws ParseException
     */
    private void propertyDeliveryTime(PropertyFeeEntity propertyFee, Room room, Date dayDate, SimpleDateFormat sim, PropertyLog propertyLog) throws ParseException {
        Date deliveryTime = room.getDeliveryTime();
        BigDecimal totalArea = new BigDecimal(room.getTotalArea());
        //0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
        String payMethod = propertyFee.getPayMethod();
        //缴费金额
        BigDecimal money = new BigDecimal("0.00");
        //缴费周期
        Date startTime = dayDate;
        if (StringUtils.equals(payMethod, "0")) {
            //按月缴费
            System.out.println("START按月缴费...");
            payMonth0(propertyFee, deliveryTime, dayDate, sim, money, totalArea, startTime, propertyLog);
            System.out.println("END按月缴费...");
        }
        if (StringUtils.equals(payMethod, "1")) {
            //按季度
            System.out.println("START按季度缴费...");
            payMonth1(propertyFee, deliveryTime, dayDate, sim, money, totalArea, startTime, propertyLog);
            System.out.println("END按季度缴费...");
        }
        if (StringUtils.equals(payMethod, "2")) {
            //按半年
            System.out.println("START按半年缴费...");
            payMonth2(propertyFee, deliveryTime, dayDate, sim, money, totalArea, startTime, propertyLog);
            System.out.println("END按半年缴费...");
        }
        if (StringUtils.equals(payMethod, "3")) {
            //按年
            System.out.println("START按年缴费...");
            payMonth3(propertyFee, deliveryTime, dayDate, sim, money, totalArea, startTime, propertyLog);
            System.out.println("START按年缴费...");
        }
    }

    /**
     * 保存数据
     *
     * @param propertyLog
     */
    private void savePropertyLog(PropertyLog propertyLog) {
        SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("1周期:" + sim1.format(propertyLog.getStartTime()) + ">>" + sim1.format(propertyLog.getEndTime()));
        Integer roomIdCount = propertyLogMapper.getRoomIdCount(
                propertyLog.getCommunityId(),
                propertyLog.getBuildingId(),
                propertyLog.getUnitId(),
                propertyLog.getRoomId(),
                propertyLog.getStartTime(),
                propertyLog.getEndTime()
        );
        if (roomIdCount <= 0) {
            propertyLogMapper.insertPropertyLog(propertyLog);
        }
    }

    /**
     * 按年缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param dayDate
     * @param sim
     * @param money
     * @param totalArea
     * @param startTime
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth3(PropertyFeeEntity propertyFee, Date deliveryTime, Date dayDate, SimpleDateFormat sim, BigDecimal money, BigDecimal totalArea, Date startTime, PropertyLog propertyLog) throws ParseException {
        Date endTime;
        if (deliveryTime.getTime() == dayDate.getTime()) {
            //交房时间等于今天
            //计算今天到月底的费用
            String delivery = sim.format(deliveryTime);
            int year = Integer.parseInt(delivery.split("-")[0]);  //年
            Date endDate = DateUtils.fastMonthDay(year, 12);
            endTime = endDate;
            //计算当前时间到月底相差几天
            Long dayCount = DateUtils.getDayCount(deliveryTime, endDate);
            //建筑面积*费用
            money = new BigDecimal("0.00");
            money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
            if (dayCount > 0) {
                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);
                pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                pl.setStartTime(startTime);
                pl.setEndTime(endTime);
                pl.setYjAmount(money);
                pl.setPaidAmount(money);
                //保存数据
                savePropertyLog(pl);
            }
        }
        if (deliveryTime.getTime() < dayDate.getTime()) {

            List<String> monthBetweenDateList = DateUtils.getMonthBetweenDate(deliveryTime, dayDate);
            String dqMonth = DateUtils.getDate().substring(0, 7);
            if (!monthBetweenDateList.contains(dqMonth)) {
                monthBetweenDateList.add(dqMonth);
            }
            List<QuarterPropertyFeeVo> quarterPropertyFeeList = Lists.newArrayList();
            for (int i = 0; i < monthBetweenDateList.size(); i++) {
                if (i != 0) {
                    String delivery = monthBetweenDateList.get(i) + "-01";
                    deliveryTime = sim.parse(delivery);
                }
                QuarterPropertyFeeVo quarterPropertyFeeVo = new QuarterPropertyFeeVo();
                int year = Integer.parseInt(monthBetweenDateList.get(i).split("-")[0]);  //年
                quarterPropertyFeeVo.setStartTime(sim.format(deliveryTime));
                quarterPropertyFeeVo.setEndTime(sim.format(DateUtils.fastMonthDay(year, 12)));
                quarterPropertyFeeList.add(quarterPropertyFeeVo);
            }
            //分组去重
            Map<String, List<QuarterPropertyFeeVo>> collect = quarterPropertyFeeList.stream().collect(Collectors.groupingBy(QuarterPropertyFeeVo::getEndTime));
            quarterPropertyFeeList = Lists.newArrayList();
            for (String key : collect.keySet()) {
                List<QuarterPropertyFeeVo> qList = collect.get(key);
                quarterPropertyFeeList.add(qList.get(0));
            }
            //排序
            quarterPropertyFeeList = quarterPropertyFeeList.stream().sorted(Comparator.comparing(QuarterPropertyFeeVo::getStartTime)).collect(Collectors.toList());

            for (int i = 0; i < quarterPropertyFeeList.size(); i++) {

                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);
                QuarterPropertyFeeVo quarterPropertyFeeVo = quarterPropertyFeeList.get(i);
                //计算指定时间到季度最后一天的天数
                Long dayCount = DateUtils.getDayCount(sim.parse(quarterPropertyFeeVo.getStartTime()), sim.parse(quarterPropertyFeeVo.getEndTime()));
                if (i != 0) {
                    dayCount = dayCount + 1;
                }
                //计算费用
                //建筑面积*费用
                money = new BigDecimal("0.00");
                money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
                //计算费用
                //建筑面积*费用*天数
                if (dayCount > 0) {
                    pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                    pl.setStartTime(sim.parse(quarterPropertyFeeVo.getStartTime()));
                    pl.setEndTime(sim.parse(quarterPropertyFeeVo.getEndTime()));
                    pl.setYjAmount(money);
                    pl.setPaidAmount(money);
                    //保存数据
                    savePropertyLog(pl);
                }
            }
        }
    }

    /**
     * 按半年缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param dayDate
     * @param sim
     * @param money
     * @param totalArea
     * @param startTime
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth2(PropertyFeeEntity propertyFee, Date deliveryTime, Date dayDate, SimpleDateFormat sim, BigDecimal money, BigDecimal totalArea, Date startTime, PropertyLog propertyLog) throws ParseException {
        Date endTime;
        if (deliveryTime.getTime() == dayDate.getTime()) {
            //交房时间等于今天
            //计算今天到月底的费用
            String delivery = sim.format(deliveryTime);
            int year = Integer.parseInt(delivery.split("-")[0]);  //年
            int month = Integer.parseInt(delivery.split("-")[1]); //月
            if (month >= 7) {
                //下半年
                month = 12;
            } else {
                //上半年
                month = 6;
            }

            Date endDate = DateUtils.fastMonthDay(year, month);
            endTime = endDate;
            //计算当前时间到月底相差几天
            Long dayCount = DateUtils.getDayCount(deliveryTime, endDate);
            //建筑面积*费用*天数
            money = new BigDecimal("0.00");
            money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
            if (dayCount > 0) {
                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);
                pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                pl.setStartTime(startTime);
                pl.setEndTime(endTime);
                pl.setYjAmount(money);
                pl.setPaidAmount(money);
                //保存数据
                savePropertyLog(pl);
            }

        }
        if (deliveryTime.getTime() < dayDate.getTime()) {

            List<String> monthBetweenDateList = DateUtils.getMonthBetweenDate(deliveryTime, dayDate);
            String dqMonth = DateUtils.getDate().substring(0, 7);
            if (!monthBetweenDateList.contains(dqMonth)) {
                monthBetweenDateList.add(dqMonth);
            }
            List<QuarterPropertyFeeVo> quarterPropertyFeeList = Lists.newArrayList();
            for (int i = 0; i < monthBetweenDateList.size(); i++) {
                if (i != 0) {
                    String delivery = monthBetweenDateList.get(i) + "-01";
                    deliveryTime = sim.parse(delivery);
                }
                QuarterPropertyFeeVo quarterPropertyFeeVo = new QuarterPropertyFeeVo();
                int year = Integer.parseInt(monthBetweenDateList.get(i).split("-")[0]);  //年
                int month = Integer.parseInt(monthBetweenDateList.get(i).split("-")[1]); //月
                if (month >= 7) {
                    //下半年
                    month = 12;
                    quarterPropertyFeeVo.setQuarter(1);
                } else {
                    //上半年
                    month = 6;
                    quarterPropertyFeeVo.setQuarter(0);
                }
                quarterPropertyFeeVo.setStartTime(sim.format(deliveryTime));
                quarterPropertyFeeVo.setEndTime(sim.format(DateUtils.fastMonthDay(year, month)));
                quarterPropertyFeeList.add(quarterPropertyFeeVo);
            }
            //分组去重
            Map<String, List<QuarterPropertyFeeVo>> collect = quarterPropertyFeeList.stream().collect(Collectors.groupingBy(QuarterPropertyFeeVo::getEndTime));
            quarterPropertyFeeList = Lists.newArrayList();
            for (String key : collect.keySet()) {
                List<QuarterPropertyFeeVo> qList = collect.get(key);
                quarterPropertyFeeList.add(qList.get(0));
            }
            //排序
            quarterPropertyFeeList = quarterPropertyFeeList.stream().sorted(Comparator.comparing(QuarterPropertyFeeVo::getStartTime)).collect(Collectors.toList());

            for (int i = 0; i < quarterPropertyFeeList.size(); i++) {

                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);

                QuarterPropertyFeeVo quarterPropertyFeeVo = quarterPropertyFeeList.get(i);
                //计算指定时间到季度最后一天的天数
                Long dayCount = DateUtils.getDayCount(sim.parse(quarterPropertyFeeVo.getStartTime()), sim.parse(quarterPropertyFeeVo.getEndTime()));
                if (i != 0) {
                    dayCount = dayCount + 1;
                }
                //计算费用
                //建筑面积*费用
                money = new BigDecimal("0.00");
                money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
                if (dayCount > 0) {

                    pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                    pl.setStartTime(sim.parse(quarterPropertyFeeVo.getStartTime()));
                    pl.setEndTime(sim.parse(quarterPropertyFeeVo.getEndTime()));
                    pl.setYjAmount(money);
                    pl.setPaidAmount(money);
                    //保存数据
                    savePropertyLog(pl);
                }
            }
        }
    }

    /**
     * 按季度缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param dayDate
     * @param sim
     * @param money
     * @param totalArea
     * @param startTime
     * @param propertyLog
     * @throws ParseException
     */
    private void payMonth1(PropertyFeeEntity propertyFee, Date deliveryTime, Date dayDate, SimpleDateFormat sim, BigDecimal money, BigDecimal totalArea, Date startTime, PropertyLog propertyLog) throws ParseException {
        Date endTime;
        if (deliveryTime.getTime() == dayDate.getTime()) {
            //交房时间等于今天
            //计算今天到月底的费用
            //计算当前时间到未来三个月的时间
            //1-3月为一季度，4-6月为二季度7-9月为三季度;10-12月为四季度
            //Date deliveryTime = sim.parse("2024-04-08");
            // Date dayDate = sim.parse(DateUtils.getDate());
            int quarterOfDay = DateUtils.getQuarterOfDay(deliveryTime);
            Date endDate = DateUtils.getLastDayOfQuarter(Integer.valueOf(sim.format(deliveryTime).split("-")[0]), quarterOfDay);
            endTime = endDate;
            //计算当前时间到月底相差几天
            Long dayCount = DateUtils.getDayCount(deliveryTime, endDate);
            //建筑面积*费用
            money = new BigDecimal("0.00");
            money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
            if (dayCount > 0) {
                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);
                pl.setStartTime(startTime);
                pl.setEndTime(endTime);
                pl.setYjAmount(money);
                pl.setPaidAmount(money);
                pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                //保存数据
                savePropertyLog(pl);
            }

        }
        if (deliveryTime.getTime() < dayDate.getTime()) {
            // Date deliveryTime = sim.parse("2024-04-01");
            // Date dayDate = sim.parse(DateUtils.getDate());

            List<String> monthBetweenDateList = DateUtils.getMonthBetweenDate(deliveryTime, dayDate);
            String dqMonth = DateUtils.getDate().substring(0, 7);
            if (!monthBetweenDateList.contains(dqMonth)) {
                monthBetweenDateList.add(dqMonth);
            }
            List<QuarterPropertyFeeVo> quarterPropertyFeeList = Lists.newArrayList();
            for (int i = 0; i < monthBetweenDateList.size(); i++) {

                if (i != 0) {
                    String delivery = monthBetweenDateList.get(i) + "-01";
                    deliveryTime = sim.parse(delivery);
                }
                //计算指定时间第几季度
                int quarterOfDay = DateUtils.getQuarterOfDay(deliveryTime);
                //根据指定时间和季度算出季度最后一天
                Date endDate = DateUtils.getLastDayOfQuarter(Integer.valueOf(sim.format(deliveryTime).split("-")[0]), quarterOfDay);
                QuarterPropertyFeeVo quarterPropertyFeeVo = new QuarterPropertyFeeVo();
                quarterPropertyFeeVo.setStartTime(sim.format(deliveryTime));
                quarterPropertyFeeVo.setEndTime(sim.format(endDate));
                quarterPropertyFeeVo.setQuarter(quarterOfDay);
                quarterPropertyFeeList.add(quarterPropertyFeeVo);
            }
            //分组去重
            Map<String, List<QuarterPropertyFeeVo>> collect = quarterPropertyFeeList.stream().collect(Collectors.groupingBy(QuarterPropertyFeeVo::getEndTime));
            quarterPropertyFeeList = Lists.newArrayList();
            for (String key : collect.keySet()) {
                List<QuarterPropertyFeeVo> qList = collect.get(key);
                quarterPropertyFeeList.add(qList.get(0));
            }
            //排序
            quarterPropertyFeeList = quarterPropertyFeeList.stream().sorted(Comparator.comparing(QuarterPropertyFeeVo::getStartTime)).collect(Collectors.toList());

            for (int i = 0; i < quarterPropertyFeeList.size(); i++) {

                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);

                QuarterPropertyFeeVo quarterPropertyFeeVo = quarterPropertyFeeList.get(i);
                //计算指定时间到季度最后一天的天数
                Long dayCount = DateUtils.getDayCount(sim.parse(quarterPropertyFeeVo.getStartTime()), sim.parse(quarterPropertyFeeVo.getEndTime()));
                if (i != 0) {
                    dayCount = dayCount + 1;
                }
                //计算费用
                //建筑面积*费用
                money = new BigDecimal("0.00");
                money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);

                if (dayCount > 0) {
                    pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                    pl.setStartTime(sim.parse(quarterPropertyFeeVo.getStartTime()));
                    pl.setEndTime(sim.parse(quarterPropertyFeeVo.getEndTime()));
                    pl.setYjAmount(money);
                    pl.setPaidAmount(money);
                    //保存数据
                    savePropertyLog(pl);
                }
            }
        }
    }


    /**
     * 按月缴费
     *
     * @param propertyFee
     * @param deliveryTime
     * @param dayDate
     * @param sim
     * @param money
     * @param totalArea
     * @param startTime
     * @param propertyLog
     */
    private void payMonth0(PropertyFeeEntity propertyFee, Date deliveryTime, Date dayDate, SimpleDateFormat sim, BigDecimal money, BigDecimal totalArea, Date startTime, PropertyLog propertyLog) throws ParseException {
        Date endTime;
        if (deliveryTime.getTime() == dayDate.getTime()) {
            //交房时间等于今天
            //计算今天到月底的费用

            //计算当前时间到月底的时间
            LocalDate localDate = DateUtils.lastMonthDay(LocalDate.now());
            Date endDate = null;
            try {
                endDate = sim.parse(localDate.toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            endTime = endDate;
            //计算当前时间到月底相差几天
            Long dayCount = DateUtils.getDayCount(dayDate, endDate);
            //建筑面积*费用
            money = new BigDecimal("0.00");
            money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
            if (dayCount > 0) {
                propertyLog.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                propertyLog.setStartTime(startTime);
                propertyLog.setEndTime(endTime);
                propertyLog.setYjAmount(money);
                propertyLog.setPaidAmount(money);
                //保存数据
                savePropertyLog(propertyLog);
            }

        }
        if (deliveryTime.getTime() < dayDate.getTime()) {
            // Date deliveryTime = sim.parse("2024-04-01");
            // Date dayDate = sim.parse(DateUtils.getDate());
            List<String> monthBetweenDateList = DateUtils.getMonthBetweenDate(deliveryTime, dayDate);
            String dqMonth = DateUtils.getDate().substring(0, 7);
            if (!monthBetweenDateList.contains(dqMonth)) {
                monthBetweenDateList.add(dqMonth);
            }

            for (int i = 0; i < monthBetweenDateList.size(); i++) {
                PropertyLog pl = new PropertyLog();
                BeanUtil.copyProperties(propertyLog, pl, true);

                LocalDate localDate = LocalDate.now();
                if (i != 0) {
                    deliveryTime = sim.parse(monthBetweenDateList.get(i) + "-01");
                }
                startTime = deliveryTime;
                int year = Integer.parseInt(sim.format(deliveryTime).split("-")[0]);
                int month = Integer.parseInt(sim.format(deliveryTime).split("-")[1]);
                int day = Integer.parseInt(sim.format(deliveryTime).split("-")[2]);
                localDate = LocalDate.of(year, month, day);
                //计算当前时间到月底的时间
                Date endDate = null;
                try {
                    endDate = sim.parse(DateUtils.lastMonthDay(localDate).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                endTime = endDate;
                //计算当前时间到月底相差几天
                Long dayCount = DateUtils.getDayCount(deliveryTime, endDate);
                if (i != 0) {
                    dayCount = dayCount + 1L;
                }
                money = new BigDecimal("0.00");
                money = getCalculateAmountPropertyFee(propertyFee, money, totalArea, dayCount);
                if (dayCount > 0) {
                    pl.setOrderNo(propertyFee.getOrderNo() + new Date().getTime());
                    pl.setStartTime(startTime);
                    pl.setEndTime(endTime);
                    pl.setYjAmount(money);
                    pl.setPaidAmount(money);
                    SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("2周期:" + sim1.format(pl.getStartTime()) + ">>" + sim1.format(pl.getEndTime()));
                    //保存数据
                    savePropertyLog(pl);
                }
            }
        }
    }

    /**
     * @param buildingIdList
     * @param propertyFeeId
     */
    private void extracted(List<String> buildingIdList, String propertyFeeId) {
        this.deleteByPropertyFeeBuilding(propertyFeeId);
        for (String buildingId : buildingIdList) {
            PropertyFeeBuilding propertyFeeBuilding = new PropertyFeeBuilding();
            propertyFeeBuilding.setPropertyFeeId(propertyFeeId);
            propertyFeeBuilding.setBuildingId(Long.valueOf(buildingId));
            String id = UUID.randomUUID().toString().replace("-", "");
            propertyFeeBuilding.setPropertyFeeBuildingId(id);
            busPropertyFeeMapper.insertBusPropertyFeeBuilding(propertyFeeBuilding);
        }
    }

    /**
     * 修改物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    @Override
    public AjaxResult updateBusPropertyFee(PropertyFeeEntity propertyFee) {

        try {
            List<String> buildingIds = Arrays.asList(propertyFee.getBuildingId().split(","));
            Integer count = busPropertyFeeMapper.getCommunityIdFeeTypeCount(propertyFee.getCommunityId(), propertyFee.getFeeType(), propertyFee.getPayMethod(), propertyFee.getPropertyFeeId(), buildingIds);
            if (count >= 1) {
                return AjaxResult.error(510, "当前缴费类型,缴费方式已存在,请勿重复添加");
            }

            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sim.parse(DateUtils.getDate());


            if (propertyFee.getEndTime().getTime() < parse.getTime()) {
                return AjaxResult.error(510, "缴费适用结束日期不得小于当前日期");
            }

            if (propertyFee.getEndTime().getTime() < propertyFee.getEndTime().getTime()) {
                return AjaxResult.error(510, "缴费适用结束日期不得小于缴费适用开始日期");
            }
            //PropertyFeeEntity pf = busPropertyFeeMapper.selectBusPropertyFeeByCommunityId(propertyFee.getPropertyFeeId());
            propertyFee.setUpdateTime(DateUtils.getNowDate());
            propertyFee.setUpdateBy(SecurityUtils.getUsername());
            propertyFee.setOrderNo("WYF" + new Date().getTime());
            busPropertyFeeMapper.updateBusPropertyFee(propertyFee);
            extracted(buildingIds, propertyFee.getPropertyFeeId());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

    /**
     * 修改物业缴费设置
     *
     * @param propertyFee 物业缴费设置
     * @return 结果
     */
    @Override
    public void updatePropertyFeeStatus(PropertyFeeEntity propertyFee) {
        propertyFee.setUpdateTime(DateUtils.getNowDate());
        propertyFee.setUpdateBy(SecurityUtils.getUsername());

        busPropertyFeeMapper.updateBusPropertyFee(propertyFee);
    }

    /**
     * 批量删除物业缴费设置
     *
     * @param communityIds 需要删除的物业缴费设置主键
     * @return 结果
     */
    @Override
    public int deleteBusPropertyFeeByCommunityIds(Long[] communityIds) {
        return busPropertyFeeMapper.deleteBusPropertyFeeByCommunityIds(communityIds);
    }

    /**
     * 删除物业缴费设置信息
     *
     * @param propertyFeeId 物业缴费设置主键
     * @return 结果
     */
    @Override
    public int deleteBusPropertyFeeByCommunityId(String propertyFeeId) {
        return busPropertyFeeMapper.deleteBusPropertyFeeByCommunityId(propertyFeeId);
    }

    @Override
    public void clearByPropertyFeeIdTime(String propertyFeeId) {
        busPropertyFeeMapper.clearByPropertyFeeIdTime(propertyFeeId);
    }

    @Override
    public void deleteByPropertyFeeBuilding(String propertyFeeId) {
        busPropertyFeeMapper.deleteByPropertyFeeBuilding(propertyFeeId);
    }

    @Override
    public List<String> getEntTimeFeeIdList() {
        return busPropertyFeeMapper.getEntTimeFeeIdList();
    }


    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void generatePropertyBillTask() {

        try {
            //1、查询正在使用和未到期的缴费规则和缴费范围
            List<PropertyFeeEntity> propertyFeeList = this.getAllPropertyFeeList();
            if (CollectionUtils.isNotEmpty(propertyFeeList)) {
                propertyFeeList.stream().forEach(propertyFeeEntity -> {
                    //2、根据缴费范围查询房号
                    String buildings = this.getByPropertyFeeIdBuildingInfo(propertyFeeEntity.getPropertyFeeId());
                    if (StringUtils.isNotEmpty(buildings)) {
                        List<String> buildingList = Arrays.asList(buildings.split(","));
                        if (CollectionUtils.isNotEmpty(buildingList)) {
                            for (String buildingId : buildingList) {
                                try {
                                    log.info("<<<<<<<<<<<<<<<START未交房物业费>>>>>>>>>>>>>>>");
                                    //未交房物业费
                                    this.unpaidPropertyGeneratePropertyBill(propertyFeeEntity, buildingId);
                                    System.out.println("<<<<<<<<<<<<<<<END未交房物业费>>>>>>>>>>>>>>>");
                                    //已交房物业费
                                    log.info("<<<<<<<<<<<<<<<START已交房物业费>>>>>>>>>>>>>>>");
                                    this.generatePropertyBill(propertyFeeEntity, buildingId);
                                    System.out.println("<<<<<<<<<<<<<<<END未交房物业费>>>>>>>>>>>>>>>");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void unpaidPropertyGeneratePropertyBill() {

        try {
            //1、查询正在使用和未到期的缴费规则和缴费范围
            List<PropertyFeeEntity> propertyFeeList = this.getAllPropertyFeeList();
            if (CollectionUtils.isNotEmpty(propertyFeeList)) {
                propertyFeeList.stream().forEach(propertyFeeEntity -> {
                    //2、根据缴费范围查询房号
                    String buildings = this.getByPropertyFeeIdBuildingInfo(propertyFeeEntity.getPropertyFeeId());
                    if (StringUtils.isNotEmpty(buildings)) {
                        List<String> buildingList = Arrays.asList(buildings.split(","));
                        if (CollectionUtils.isNotEmpty(buildingList)) {
                            for (String buildingId : buildingList) {
                                try {
                                    log.info("<<<<<<<<<<<<<<<START未交房物业费>>>>>>>>>>>>>>>");
                                    //未交房物业费
                                    this.unpaidPropertyGeneratePropertyBill(propertyFeeEntity, buildingId);
                                    System.out.println("<<<<<<<<<<<<<<<END未交房物业费>>>>>>>>>>>>>>>");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}