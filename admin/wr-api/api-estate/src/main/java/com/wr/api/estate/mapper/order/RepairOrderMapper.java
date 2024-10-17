package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.RepairOrderDetailVO;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 报事报修工单mapper
 */
public interface RepairOrderMapper {

    /**
     * 居家维修工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listHomeOrders(OrderQueryVO query);

    /**
     * 居家维修工单详情
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.RepairOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:04
     */
    RepairOrderDetailVO detailHomeOrder(Long orderId);

    /**
     * 园区报事工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listParkOrders(OrderQueryVO query);

    /**
     * 园区报事工单详情
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.RepairOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:07
     */
    RepairOrderDetailVO detailParkOrder(Long orderId);

    /**
     * 灾害报事工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listDisasterOrders(OrderQueryVO query);

    /**
     * 灾害报事工单详情移动端查询
     *
     * @param orderId 订单id
     * @return com.wr.api.estate.entity.vo.order.RepairOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:11
     */
    RepairOrderDetailVO detailDisasterOrder(Long orderId);

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
     * @param order 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:09
     */
    int startPerform(OrderQueryVO order);

    /**
     * 完成工单
     *
     * @param order 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:10
     */
    int finish(OrderQueryVO order);

    /**
     * 申请转交
     *
     * @param order 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:10
     */
    int transfer(OrderQueryVO order);

}
