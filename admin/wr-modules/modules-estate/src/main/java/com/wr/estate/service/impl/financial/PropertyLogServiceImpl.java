package com.wr.estate.service.impl.financial;

import com.wr.estate.mapper.financial.PropertyLogMapper;
import com.wr.estate.service.financial.PropertyLogService;
import com.wr.remote.domain.PropertyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/10 16:17
 */
@Service
public class PropertyLogServiceImpl implements PropertyLogService {

    @Autowired
    private PropertyLogMapper propertyLogMapper;

    @Override
    public List<PropertyLog> getList(PropertyLog propertyLog) {
        return propertyLogMapper.getList(propertyLog);
    }

    @Override
    public PropertyLog getByRoomIdAmount(Long roomId,String isUnpaidPropertyFee) {
        return propertyLogMapper.getByRoomIdAmount(roomId,isUnpaidPropertyFee);
    }

    @Override
    public void updateByLogIdAmount(BigDecimal discountAmount, BigDecimal paidAmount, Long logId) {
        propertyLogMapper.updateByLogIdAmount(discountAmount, paidAmount, logId);
    }

    @Override
    public PropertyLog getById(Long logId) {
        return propertyLogMapper.getById(logId);
    }

    @Override
    public PropertyLog getOrderNoInfo(String orderNo) {
        return propertyLogMapper.getOrderNoInfo(orderNo);
    }

    @Override
    public void insertPropertyLog(List<PropertyLog> propertyLogList) {
        for (PropertyLog propertyLog : propertyLogList) {
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

    }

    @Override
    public void saveProperty(PropertyLog propertyLog) {
        Integer roomIdCount = propertyLogMapper.getRoomIdCount(
                propertyLog.getCommunityId(),
                propertyLog.getBuildingId(),
                propertyLog.getUnitId(),
                propertyLog.getRoomId(),
                propertyLog.getStartTime(),
                propertyLog.getEndTime()
        );
        if (roomIdCount >= 1) {
            propertyLogMapper.updatePropertyLog(propertyLog);
        } else {
            propertyLogMapper.insertPropertyLog(propertyLog);
        }

    }


    @Override
    public void deleteByOrderNo(String orderNo) {
        propertyLogMapper.deleteByOrderNo(orderNo);
    }

    @Override
    public void bachUpdatePayStatus(List<String> logIdList) {
        for (String logId : logIdList) {
            String payStatus = propertyLogMapper.getById(Long.valueOf(logId)).getPayStatus();
            if (payStatus.equals("0")) {
                propertyLogMapper.updatePayStatus(Long.valueOf(logId));
            }
        }

    }

    @Override
    public void updateById(PropertyLog propertyLog) {
        propertyLogMapper.updateById(propertyLog);
    }

    /**
     *
     * @param propertyLog
     */
    @Override
    public void updateByLogIdPropertyLog(PropertyLog propertyLog) {
        propertyLogMapper.updateByLogIdPropertyLog(propertyLog);
    }
}
