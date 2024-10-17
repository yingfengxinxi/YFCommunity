package com.wr.estate.mapper.work.orderLedger;

import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.fitment.DecorateOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-07 15:54:34
 * @Desc: 装修评定台账
 */
@Mapper
public interface DecLedMapper {

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder);

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    DecorateOrder getDecorateOrderDetail(Long orderId);

    /**
     * 查询工单协助人信息
     *
     * @param orderId
     * @return
     */
    List<Staff> getStaffList(Long orderId);
}
