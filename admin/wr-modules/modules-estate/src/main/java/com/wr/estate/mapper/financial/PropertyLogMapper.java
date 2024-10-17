package com.wr.estate.mapper.financial;

import com.wr.estate.entity.vo.PropertyLogCountVo;
import com.wr.remote.domain.PropertyLog;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/3/10 16:03
 */
public interface PropertyLogMapper {

    /**
     * @param propertyLog
     * @return
     */
    List<PropertyLog> getList(PropertyLog propertyLog);

    /**
     * @param roomId
     * @param isUnpaidPropertyFee
     * @return
     */
    PropertyLog getByRoomIdAmount(@Param("roomId") Long roomId, @Param("isUnpaidPropertyFee") String isUnpaidPropertyFee);

    /**
     * @param discountAmount
     */
    void updateByLogIdAmount(@Param("discountAmount") BigDecimal discountAmount, @Param("paidAmount") BigDecimal paidAmount, @Param("logId") Long logId);

    /**
     * @param logId
     * @return
     */
    PropertyLog getById(@Param("logId") Long logId);

    /**
     * @param orderNo
     * @return
     */
    PropertyLog getOrderNoInfo(@Param("orderNo") String orderNo);

    /**
     * @return
     */
    List<PropertyLogCountVo> getByDateCount();

    /**
     * 添加物业缴费记录
     *
     * @param propertyLog 数据
     * @return java.lang.Integer
     */
    Integer insertPropertyLog(PropertyLog propertyLog);


    void updatePropertyLog(PropertyLog propertyLog);

    void deleteByOrderNo(@Param("orderNo") String orderNo);

    Integer getRoomIdCount(
            @Param("communityId") Long communityId,
            @Param("buildingId") Long buildingId,
            @Param("unitId") Long unitId,
            @Param("roomId") Long roomId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime
    );

    Date getByRoomIdEntTime(
            @Param("communityId") Long communityId,
            @Param("buildingId") Long buildingId,
            @Param("unitId") Long unitId,
            @Param("roomId") Long roomId
    );

    /**
     * 发送账单
     *
     * @param logId
     */
    void updatePayStatus(@Param("logId") Long logId);

    /**
     * @param propertyLog
     */
    void updateById(PropertyLog propertyLog);

    /**
     * @param propertyLog
     */
    void updateByLogIdPropertyLog(PropertyLog propertyLog);

}
