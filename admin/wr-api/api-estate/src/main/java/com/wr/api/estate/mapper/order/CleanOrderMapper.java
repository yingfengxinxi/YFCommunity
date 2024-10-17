package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.CleanOrderDetailVO;
import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 保洁工单mapper
 */
public interface CleanOrderMapper {

    /**
     * 保洁工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listCleanOrders(OrderQueryVO query);

    /**
     * 保洁工单详情
     *
     * @param orderId
     * @return com.wr.api.estate.entity.vo.order.CleanOrderDetailVO
     * @author yx
     * @date 2022/11/15 11:46
     */
    CleanOrderDetailVO detailCleanOrder(Long orderId);

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
