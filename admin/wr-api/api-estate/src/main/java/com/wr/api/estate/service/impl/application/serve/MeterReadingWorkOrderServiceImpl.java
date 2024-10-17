package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wr.api.estate.mapper.LoginMapper;
import com.wr.api.estate.mapper.application.MeterReadingEquipmentMapper;
import com.wr.api.estate.mapper.application.MeterReadingRecordWorkOrderMapper;
import com.wr.api.estate.mapper.application.MeterReadingWorkOrderMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.MeterReadingWorkOrderService;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/3/5 13:39
 */
@Service
public class MeterReadingWorkOrderServiceImpl extends ServiceImpl<MeterReadingWorkOrderMapper, MeterReadingWorkOrder> implements MeterReadingWorkOrderService {

    @Autowired
    private MeterReadingRecordWorkOrderMapper meterReadingRecordWorkOrderMapper;

    @Autowired
    private MeterReadingEquipmentMapper meterReadingEquipmentMapper;


    @Autowired
    private LoginMapper loginMapper;

    /**
     * @param mwo
     * @return
     */
    @Override
    public List<MeterReadingWorkOrder> getList(MeterReadingWorkOrder mwo) {
        LambdaQueryWrapper<MeterReadingWorkOrder> queryWrapper = getMeterReadingWorkOrderLambdaQueryWrapper(mwo);
        queryWrapper.orderByDesc(MeterReadingWorkOrder::getUpdateTime);
        List<MeterReadingWorkOrder> list = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<SysDictData> energyConsumptionTypeDicts = DictUtils.getDictCache("energy_consumption_type");
            List<SysDictData> meterReadingTypeDicts = DictUtils.getDictCache("meter_reading_type");
            list.stream().forEach(meterReadingWorkOrder -> {
                String energyConsumptionType = meterReadingWorkOrder.getEnergyConsumptionType();
                String energyConsumptionTypeName = energyConsumptionTypeDicts.stream().filter(sysDictData -> sysDictData.getDictValue().equals(energyConsumptionType)).collect(Collectors.toList()).get(0).getDictLabel();

                String meterReadingType = meterReadingWorkOrder.getMeterReadingType();
                String meterReadingTypeName = meterReadingTypeDicts.stream().filter(sysDictData -> sysDictData.getDictValue().equals(meterReadingType)).collect(Collectors.toList()).get(0).getDictLabel();

                meterReadingWorkOrder.setEnergyMeterContent(energyConsumptionTypeName + "-" + meterReadingTypeName);

                //MeterReadingWorkOrder meterReadingWorkOrder = baseMapper.selectById(id);
                LambdaQueryWrapper<MeterReadingWorkOrder> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(MeterReadingWorkOrder::getEnergyConsumptionType, meterReadingWorkOrder.getEnergyConsumptionType());
                queryWrapper1.eq(MeterReadingWorkOrder::getMeterReadingType, meterReadingWorkOrder.getMeterReadingType());
                queryWrapper1.eq(MeterReadingWorkOrder::getCommunityId, meterReadingWorkOrder.getCommunityId());
                //queryWrapper1.apply(" id!='" + meterReadingWorkOrder.getId() + "'");
                List<String> statusList = Lists.newArrayList();
                if (StringUtils.isEmpty(mwo.getMeterReadingStatus())) {
                    statusList = Arrays.asList("0,2".split(","));
                } else {
                    statusList.add(mwo.getMeterReadingStatus());
                }

                /**
                 *
                 */
                if (StringUtils.equals(meterReadingWorkOrder.getMeterReadingStatus(), "0") || StringUtils.equals(meterReadingWorkOrder.getMeterReadingStatus(), "2")) {
                    queryWrapper1.in(MeterReadingWorkOrder::getMeterReadingStatus, statusList);
                    queryWrapper1.orderByAsc(MeterReadingWorkOrder::getCreateTime).last(" limit 1");
                    MeterReadingWorkOrder meterReadingWorkOrder1 = baseMapper.selectOne(queryWrapper1);
                    if (meterReadingWorkOrder1 != null) {
                        meterReadingWorkOrder.setIsRead("1");
                        if (meterReadingWorkOrder1.getId().equals(meterReadingWorkOrder.getId())) {
                            meterReadingWorkOrder.setIsRead("0");
                        }
                    } else {
                        meterReadingWorkOrder.setIsRead("0");
                    }
                } else {
                    meterReadingWorkOrder.setIsRead("0");
                }
            });

        }
        return list;
    }

    @NotNull
    private LambdaQueryWrapper<MeterReadingWorkOrder> getMeterReadingWorkOrderLambdaQueryWrapper(MeterReadingWorkOrder meterReadingWorkOrder) {
        LambdaQueryWrapper<MeterReadingWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingWorkOrder::getCommunityId, meterReadingWorkOrder.getCommunityId());
        SysUser sysUser = loginMapper.getByUserIdUser(Long.valueOf(meterReadingWorkOrder.getName()));
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {

            queryWrapper.apply(" (name='" + meterReadingWorkOrder.getName() + "' or name='' or name IS NULL) ");
        }
        if (com.wr.common.core.utils.StringUtils.isNotEmpty(meterReadingWorkOrder.getMeterReadingStatus())) {
            queryWrapper.eq(MeterReadingWorkOrder::getMeterReadingStatus, meterReadingWorkOrder.getMeterReadingStatus());
        }

        if (com.wr.common.core.utils.StringUtils.isNotEmpty(meterReadingWorkOrder.getMeterReadingType())) {
            queryWrapper.eq(MeterReadingWorkOrder::getMeterReadingType, meterReadingWorkOrder.getMeterReadingType());
        }
        return queryWrapper;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public MeterReadingWorkOrder get(String id) {
        MeterReadingWorkOrder meterReadingWorkOrder = baseMapper.selectById(id);
        if (StringUtils.isNotEmpty(meterReadingWorkOrder.getName())) {
            SysUser user = loginMapper.getByUserIdUser(Long.valueOf(meterReadingWorkOrder.getName()));
            meterReadingWorkOrder.setName(user.getNickName());
            meterReadingWorkOrder.setPhone(user.getPhonenumber());
        }

        LambdaQueryWrapper<MeterReadingRecordWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingRecordWorkOrder::getMeterReadingWorkOrderId, id);
        List<MeterReadingRecordWorkOrder> meterReadingRecordWorkOrders = meterReadingRecordWorkOrderMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(meterReadingRecordWorkOrders)) {
            meterReadingRecordWorkOrders.stream().forEach(meterReadingRecordWorkOrder -> {
                String meterReadingEquipmentId = meterReadingRecordWorkOrder.getMeterReadingEquipmentId();
                MeterReadingEquipment meterReadingEquipment = meterReadingEquipmentMapper.selectById(meterReadingEquipmentId);
                meterReadingRecordWorkOrder.setEquipmentNo(meterReadingEquipment.getEquipmentNo());
            });
            meterReadingWorkOrder.setRecordWorkOrderList(meterReadingRecordWorkOrders);
        }
        return meterReadingWorkOrder;
    }

    /**
     * @param meterReadingWorkOrder
     */
    @Override
    public void saveMeterReadingWorkOrder(MeterReadingWorkOrder meterReadingWorkOrder) {

        if (StringUtils.equals(meterReadingWorkOrder.getType(), "1")) {
            //提交数据
            meterReadingWorkOrder.setMeterReadingStatus("1");
        }
        meterReadingWorkOrder.setUpdateTime(new Date());

        super.updateById(meterReadingWorkOrder);

        LambdaQueryWrapper<MeterReadingRecordWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingRecordWorkOrder::getMeterReadingWorkOrderId, meterReadingWorkOrder.getId());
        meterReadingRecordWorkOrderMapper.delete(queryWrapper);

        List<MeterReadingRecordWorkOrder> recordWorkOrderList = meterReadingWorkOrder.getRecordWorkOrderList();
        if (CollectionUtils.isNotEmpty(recordWorkOrderList)) {
            for (MeterReadingRecordWorkOrder meterReadingRecordWorkOrder : recordWorkOrderList) {
                meterReadingRecordWorkOrder.setCreateTime(new Date());
                meterReadingRecordWorkOrder.setMeterReadingWorkOrderId(meterReadingWorkOrder.getId());
                meterReadingRecordWorkOrderMapper.insert(meterReadingRecordWorkOrder);
            }

        }

    }

    @Override
    public List<MeterReadingEquipment> meterReadingEquipmentList(Long communityId, String energyConsumptionType, String meterReadingType) {
        LambdaQueryWrapper<MeterReadingEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingEquipment::getCommunityId, communityId);
        queryWrapper.eq(MeterReadingEquipment::getEnergyConsumptionType, energyConsumptionType);
        queryWrapper.eq(MeterReadingEquipment::getMeterReadingType, meterReadingType);
        return meterReadingEquipmentMapper.selectList(queryWrapper);
    }
}
