package com.wr.api.estate.service.order;

import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-15 09:40:21
 * @Desc:
 */
public interface OrderService {

    /**
     * 工单列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/15 9:42
     */
    List<OrderListVO> listPage(OrderQueryVO query);

    /**
     * 工单详情
     *
     * @param query 查询条件
     * @return T
     * @author yx
     * @date 2022/11/15 9:42
     */
    Object detail(OrderQueryVO query);

    /**
     * 抢单
     *
     * @param query 抢单信息
     * @author yx
     * @date 2022/11/15 16:43
     */
    void robOrder(OrderQueryVO query);

    /**
     * 接单
     *
     * @param query 接单参数
     * @author yx
     * @date 2022/11/15 16:58
     */
    void receiveOrder(OrderQueryVO query);

    /**
     * 开始执行
     *
     * @param query 开始执行参数
     * @author yx
     * @date 2022/11/15 19:17
     */
    void startPerform(OrderQueryVO query);

    /**
     * 申请转让
     *
     * @param order 申请转让参数
     * @author yx
     * @date 2022/11/15 19:19
     */
    void transfer(OrderQueryVO order);

    /**
     * 请求协助
     *
     * @param order 请求协助参数
     * @author yx
     * @date 2022/11/15 19:19
     */
    void assist(OrderQueryVO order);

    /**
     * 签到
     *
     * @param order 签到参数
     * @author yx
     * @date 2022/11/15 19:21
     */
    void signIn(OrderQueryVO order);

    /**
     * 完成工单
     *
     * @param order 完成执行参数
     * @author yx
     * @date 2022/11/15 19:18
     */
    void finish(OrderQueryVO order);
}
