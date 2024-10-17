package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.NightPatrolOrderDetailVO;
import com.wr.api.estate.entity.vo.order.NightPatrolOrderPointVO;
import com.wr.api.estate.entity.vo.order.OrderListVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.remote.work.nightPatrol.Watchman;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 巡更工单mapper
 */
public interface NightPatrolOrderMapper {

    /**
     * 巡更工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderListVO> listNightPatrolOrders(OrderQueryVO query);

    /**
     * 巡更详情移动端查询
     *
     * @param orderId 工单id
     * @return com.wr.api.estate.entity.vo.order.NightPatrolOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:53
     */
    NightPatrolOrderDetailVO detailNightPatrolOrders(Long orderId);

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
     * @date 2022/11/16 10:00
     */
    int startPerform(OrderQueryVO query);

    /**
     * 完成工单
     *
     * @param query 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:01
     */
    int finish(OrderQueryVO query);

    /**
     * 转交工单
     *
     * @param query 工单信息
     * @return int
     * @author yx
     * @date 2022/11/16 10:01
     */
    int transfer(OrderQueryVO query);

    /**
     * 查询巡更工单
     *
     * @param watchmanId 巡更工单主键
     * @return 巡更工单
     */
    Watchman selectWatchmanByWatchmanId(Long watchmanId);

    /**
     * 查询巡更点信息
     *
     * @param watchmanId 巡更工单id
     * @return java.util.List<com.wr.api.estate.entity.vo.order.NightPatrolOrderPointVO>
     * @author yx
     * @date 2022/11/17 13:33
     */
    List<NightPatrolOrderPointVO> selectWatchmanPoint(Long watchmanId);

    /**
     * 查询巡更的巡更点
     *
     * @param watchmanId
     * @return com.wr.remote.work.nightPatrol.WatchmanPoint
     * @author yx
     * @date 2022/11/16 13:25
     */
    Integer watchmanPointCount(Long watchmanId);
    Integer signInPoint(OrderQueryVO order);
}
