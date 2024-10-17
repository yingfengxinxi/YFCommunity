package com.wr.api.estate.service.impl.order;

import com.wr.api.estate.entity.vo.order.*;
import com.wr.api.estate.mapper.order.PatrolOrderMapper;
import com.wr.api.estate.service.order.OrderCommonService;
import com.wr.api.estate.service.order.OrderService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.remote.domain.Patrol;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:48:21
 * @Desc: 报事报修工单实现类
 */
@Service("patrolOrderService")
public class PatrolOrderServiceImpl implements OrderService {

    @Autowired
    private PatrolOrderMapper patrolOrderMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    /**
     * 巡查工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    @Override
    public List<OrderListVO> listPage(OrderQueryVO query) {
        return patrolOrderMapper.listPatrolOrders(query);
    }

    /**
     * 巡查工单详情移动端查询
     *
     * @param query 查询条件
     * @return com.wr.api.estate.entity.vo.order.PatrolOrderDetailVO
     * @author yx
     * @date 2022/11/14 21:39
     */
    @Override
    public Object detail(OrderQueryVO query) {
        PatrolOrderDetailVO detail = patrolOrderMapper.detailPatrolOrder(query.getOrderId());
        Integer hasCharge = 2;
        if (Objects.equals(query.getStaffId(), detail.getStaffId())) {
            hasCharge = 1;
        }
        detail.setHasCharge(hasCharge);

        // 查询协助人员
        List<OrderAssistsVO> orderAssistsList = orderCommonService.listAssists(query);
        detail.setOrderAssists(orderAssistsList);

        // 查询是否有协助正在审核
        Integer count = orderCommonService.countInAudit(query);
        detail.setHasAssistAudit(count > 0 ? 1 : 0);

        // 转交中，查询转交人
        if (detail.getOrderStatus() == 4) {
            // 查询转交人
            OrderTransferVO transfer = orderCommonService.getTransfer(query);
            detail.setOrderTransfer(transfer);
        }

        return detail;
    }

    /**
     * 抢单
     *
     * @param query 抢单信息
     * @author yx
     * @date 2022/11/15 16:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void robOrder(OrderQueryVO query) {
        int i = patrolOrderMapper.robOrder(query);
        if (i <= 0) {
            throw new ServiceException("您来晚了，工单被别人抢走啦！", 1000);
        }
        // 工单记录
        orderCommonService.insertOrderRecorder(query, "0", "接单");
    }

    /**
     * 接单
     *
     * @param query 接单参数
     * @author yx
     * @date 2022/11/15 16:58
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrder(OrderQueryVO query) {
        int i = patrolOrderMapper.receiveOrder(query);
        if (i <= 0) {
            throw new ServiceException("接单失败，工单数据发生改变，请刷新后重试！", 1000);
        }
        // 工单记录
        orderCommonService.insertOrderRecorder(query, "0", "接单");
    }

    /**
     * 开始执行
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:17
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startPerform(OrderQueryVO order) {
        int i = patrolOrderMapper.startPerform(order);
        if (i <= 0) {
            throw new ServiceException("开始执行失败，工单数据发生改变，请刷新后重试！", 1000);
        }
        // 新增工单执行情况记录
        orderCommonService.insertOrderRecorder(order, "1", "开始执行");
    }

    /**
     * 申请转让
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(OrderQueryVO order) {
        // 校验是否已经开始执行了
        // 查询工单签到次数
        int count = orderCommonService.signCount(order);

        if (count > 0) {
            throw new ServiceException("工单已经开始，禁止转让！", 1000);
        }

        int i = patrolOrderMapper.transfer(order);
        if (i <= 0) {
            throw new ServiceException("工单转让失败，工单数据发生改变，请刷新后重试！", 1000);
        }

        // 申请审核记录
        orderCommonService.insertTransfer(order);
    }

    /**
     * 请求协助
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assist(OrderQueryVO order) {
        // 校验是否已经开始执行了
        // 查询工单签到次数，-1是减去开始执行的统计
        int count = orderCommonService.signCount(order);
        if (count > 0) {
            throw new ServiceException("工单已经开始，禁止请求协助！", 1000);
        }

        // 新增协助申请
        orderCommonService.assist(order);
    }

    /**
     * 签到
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:21
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signIn(OrderQueryVO order) {
        // 查询工单信息
//        Patrol patrol = patrolOrderMapper.selectPatrolByPatrolId(order.getOrderId());
//
//        if ("2".equals(patrol.getExecuteStatus()) || "3".equals(patrol.getExecuteStatus())) {
//            throw new ServiceException("签到失败，工单已完成！", 1000);
//        }

//        // 查询所有工单协助人员
//        List<OrderAssistsVO> orderAssists = orderCommonService.listAssists(order);
//
//        // 校验签到人员是否可以进行签到
//        boolean match = orderAssists.stream().anyMatch(t -> t.getStaffId().equals(order.getStaffId()));
//        if (!order.getStaffId().equals(patrol.getStaffId()) && !match) {
//            throw new ServiceException("签到失败，仅工单相关人员扫码签到！", 1000);
//        }
//
//
//        //校验签到点和巡查点是否一致
//        if (!Objects.equals(order.getId(), String.valueOf(patrol.getPointId()))) {
//            throw new ServiceException("签到失败，请在正确的巡查点进行扫码签到！", 1000);
//        }
//
//        //  多日一查情况，校验上次签到时间距离本次签到时间
//        if (patrol.getGapTime().doubleValue() == 0) {
//            // 不需要校验，直接签到
//            orderCommonService.insertOrderRecorder(order, "1", "签到");
//        } else {
//            // 一日多查情况，校验上次签到时间距离本次签到时间
//            oneDayCheck(order, patrol.getGapTime());
//
//            // 没有异常，说明可以进行签到
//            // 签到
//            orderCommonService.insertOrderRecorder(order, "1", "签到");
//        }
    }

    /**
     * 一日多查
     *
     * @param order   工单信息
     * @param gapTime 间隔时长
     * @return boolean
     * @author yx
     * @date 2022/11/15 21:00
     */
    private void oneDayCheck(OrderQueryVO order, BigDecimal gapTime) {
        // 查询最新执行情况最新记录
        OrderRecord orderRecord = orderCommonService.latestOrderRecord(order);

        // 没有执行情况，直接执行即可
        if (ObjectUtils.isEmpty(orderRecord)) {
            // 校验是否到开始时间
            return;
        }

        Date nowDate = DateUtils.getNowDate();
        // 有执行情况，校验距离上次执行时长
        BigDecimal hourCount = DateUtils.getHourCount(nowDate, orderRecord.getCreateTime());

        int i = hourCount.compareTo(gapTime);
        if (i < 0) {
            // 间隔时长不够，禁止签到，并给出提示
            throw new ServiceException("签到失败，请在" + DateUtils.addHour(orderRecord.getCreateTime(), gapTime) + "后进行签到！", 1000);
        }

        // 间隔时长够，允许签到
    }

    /**
     * 完成工单
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finish(OrderQueryVO order) {
//        // 查询工单信息
//        Patrol patrol = patrolOrderMapper.selectPatrolByPatrolId(order.getOrderId());
//        if (Objects.equals(patrol.getExecuteStatus(), "2") || Objects.equals(patrol.getExecuteStatus(), "3")) {
//            throw new ServiceException("工单已完成，请勿重复操作！", 1000);
//        }
//
//        // 查询工单签到次数
//        int count = orderCommonService.signCount(order);
//        //  一日多查，校验签到次数和巡查频率
//        if (patrol.getGapTime().doubleValue() != 0) {
//            if (count < patrol.getPatrolNum()) {
//                throw new ServiceException("完成工单失败，您还未完成" + patrol.getPatrolNum() + "次签到！", 1000);
//            }
//        } else {
//            //多日一查
//            if (count <= 0) {
//                throw new ServiceException("完成工单失败，您还未签到！", 1000);
//            }
//        }
//
//        patrolOrderMapper.finish(order);
//
//        // 新增工单执行情况记录
//        orderCommonService.insertOrderRecorder(order, "1", "完成工单");
    }
}
