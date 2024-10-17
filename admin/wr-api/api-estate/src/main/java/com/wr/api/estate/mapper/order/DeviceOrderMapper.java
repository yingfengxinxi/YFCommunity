package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.DeviceOrderDetailVO;
import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 设备维修工单mapper
 */
public interface DeviceOrderMapper {

    /**
     * 设备维修工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listDeviceOrders(OrderQueryVO query);

    /**
     * 设备维修工单详情
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.DeviceOrderDetailVO
     * @author yx
     * @date 2022/11/15 13:21
     */
    DeviceOrderDetailVO detailDeviceOrders(Long orderId);

    /**
     * 抢单
     *
     * @param query 抢单信息
     * @return int 更新数据
     * @author yx
     * @date 2022/11/15 16:50
     */
    int robOrder(OrderQueryVO query);

    /**
     * 接单
     *
     * @param query 查询条件
     * @return int
     * @author yx
     * @date 2022/11/15 17:45
     */
    int receiveOrder(OrderQueryVO query);

    /**
     * 开始执行
     *
     * @param query 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:09
     */
    int startPerform(OrderQueryVO query);

    /**
     * 完成工单
     *
     * @param query 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:10
     */
    int finish(OrderQueryVO query);

    /**
     * 申请转交
     *
     * @param query 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:10
     */
    int transfer(OrderQueryVO query);

}
