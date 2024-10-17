package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.PatrolOrderDetailVO;
import com.wr.remote.domain.Patrol;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 巡查工单mapper
 */
public interface PatrolOrderMapper {

    /**
     * 巡查工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listPatrolOrders(OrderQueryVO query);

    /**
     * 巡查工单详情移动端查询
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.PatrolOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:39
     */
    PatrolOrderDetailVO detailPatrolOrder(Long orderId);

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
     * @param query 执行参数
     * @author yx
     * @date 2022/11/15 19:44
     */
    int startPerform(OrderQueryVO query);

    /**
     * 完成工单
     *
     * @param query 完成条件
     * @author yx
     * @date 2022/11/15 19:44
     */
    void finish(OrderQueryVO query);

    /**
     * 转交工单
     *
     * @param query 转交参数
     * @author yx
     * @date 2022/11/15 19:44
     */
    int transfer(OrderQueryVO query);

    /**
     * 根据工单id查询巡查工单信息
     *
     * @param patrolId 工单id
     * @return
     * @author yx
     * @date 2022/11/15 20:45
     */
    Patrol selectPatrolByPatrolId(Long patrolId);
}
