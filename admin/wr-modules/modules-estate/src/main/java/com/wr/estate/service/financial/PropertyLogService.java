package com.wr.estate.service.financial;

import com.wr.remote.domain.PropertyLog;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/10 16:17
 */
public interface PropertyLogService {


    /**
     * @param propertyLog
     * @return
     */
    List<PropertyLog> getList(PropertyLog propertyLog);

    /**
     *
     * @param roomId
     * @param isUnpaidPropertyFee
     * @return
     */
    PropertyLog getByRoomIdAmount(Long roomId,String isUnpaidPropertyFee);

    /**
     *
     * @param discountAmount
     * @param paidAmount
     * @param logId
     */
    void updateByLogIdAmount(BigDecimal discountAmount,BigDecimal paidAmount,Long logId);

    /**
     * @param logId
     * @return
     */
    PropertyLog getById(Long logId);

    /**
     *
     * @param orderNo
     * @return
     */
    PropertyLog getOrderNoInfo(String orderNo);

    /**
     * 添加物业缴费账单
     *
     * @param propertyLogList 数据
     * @return java.lang.Integer
     */
    void insertPropertyLog(List<PropertyLog> propertyLogList);

    /**
     *
     * @param propertyLog
     */
    void saveProperty(PropertyLog propertyLog);

    void deleteByOrderNo(String orderNo);

    void bachUpdatePayStatus(List<String> logIdList);

    void updateById(PropertyLog propertyLog);

    /**
     * @param propertyLog
     */
    void updateByLogIdPropertyLog(PropertyLog propertyLog);
}
