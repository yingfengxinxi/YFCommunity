package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.DeviceDetailVO;
import com.wr.api.estate.entity.vo.order.InspectOrderDetailVO;
import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.remote.work.vo.InspectVo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 巡检工单mapper
 */
public interface InspectOrderMapper {

    /**
     * 巡检工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listInspectOrders(OrderQueryVO query);

    /**
     * 巡检工单详情
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.InspectOrderDetailVO
     * @author yx
     * @date 2022/11/15 13:29
     */
    InspectOrderDetailVO detailInspectOrders(Long orderId);

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

    /**
     * 查询巡检工单
     *
     * @param inspectId 巡检id
     * @return com.wr.remote.work.vo.InspectVo
     * @author yx
     * @date 2022/11/16 13:43
     */
    InspectVo selectByPrimaryKey(Long inspectId);

    /**
     * 巡检设备数量
     *
     * @param inspectId 巡检工单id
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/16 13:50
     */
    Integer inspectDeviceCount(Long inspectId);

    /**
     * 巡检设备签到
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/17 15:45
     */
    void signInDevice(OrderQueryVO order);

    /**
     * 巡检设备查询
     *
     * @param inspectId 巡检id
     * @return java.util.List<com.wr.api.estate.entity.vo.order.DeviceDetailVO>
     * @author yx
     * @date 2022/11/17 15:57
     */
    List<DeviceDetailVO> listInspectDevices(Long inspectId);
}
