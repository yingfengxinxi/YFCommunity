package com.wr.api.estate.service.impl.order;

import com.wr.api.estate.entity.vo.order.*;
import com.wr.api.estate.mapper.order.DeviceOrderMapper;
import com.wr.api.estate.mapper.order.OrderCommonMapper;
import com.wr.api.estate.service.order.OrderCommonService;
import com.wr.api.estate.service.order.OrderService;
import com.wr.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:48:21
 * @Desc: 保洁工单实现类
 */
@Service("deviceOrderService")
public class DeviceOrderServiceImpl implements OrderService {

    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Autowired
    private OrderCommonMapper orderCommonMapper;

    @Autowired
    private OrderCommonService orderCommonService;


    /**
     * 设备维修工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    @Override
    public List<OrderListVO> listPage(OrderQueryVO query) {
        return deviceOrderMapper.listDeviceOrders(query);
    }

    /**
     * 设备维修工单详情
     *
     * @param query 查询条件
     * @return java.lang.Object
     * @author yx
     * @date 2022/11/15 13:22
     */
    @Override
    public Object detail(OrderQueryVO query) {
        DeviceOrderDetailVO detail = deviceOrderMapper.detailDeviceOrders(query.getOrderId());
        Integer hasCharge = 2;
        if (Objects.equals(query.getStaffId(), detail.getStaffId())){
            hasCharge = 1;
        }
        detail.setHasCharge(hasCharge);

        // 查询协助人
        List<OrderAssistsVO> orderAssists = orderCommonMapper.listAssists(query);
        detail.setOrderAssists(orderAssists);

        // 查询是否有协助正在审核
        Integer count = orderCommonService.countInAudit(query);
        detail.setHasAssistAudit(count > 0 ? 1 : 0);

        // 转交中，查询转交人
        if (detail.getOrderStatus() == 4) {
            // 查询转交人
            OrderTransferVO transfer = orderCommonMapper.getTransfer(query);
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
        int i = deviceOrderMapper.robOrder(query);
        if (i <= 0) {
            throw new ServiceException("您来晚了，工单被别人抢走啦！",1000);
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
        int i = deviceOrderMapper.receiveOrder(query);
        if (i <= 0) {
            throw new ServiceException("接单失败，工单数据发生改变，请刷新后重试！",1000);
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
        int i = deviceOrderMapper.startPerform(order);
        if (i <= 0) {
            throw new ServiceException("开始执行失败，工单数据发生改变，请刷新后重试！",1000);
        }

        // 新增工单执行情况记录
        orderCommonService.insertOrderRecorder(order, "1", "开始执行");
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
        deviceOrderMapper.finish(order);

        // 新增工单执行情况记录
        orderCommonService.insertOrderRecorder(order, "1", "完成工单");
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
            throw new ServiceException("工单已经开始，禁止转让！",1000);
        }
        // 转让
        int i = deviceOrderMapper.transfer(order);
        if (i <= 0) {
            throw new ServiceException("工单转让失败，工单数据发生改变，请刷新后重试！",1000);
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
        // 查询工单签到次数
        int count = orderCommonService.signCount(order);
        if (count > 0) {
            throw new ServiceException("工单已经开始，禁止请求协助！",1000);
        }

        // 新增协助申请
        orderCommonService.assist(order);
    }

    /**
     * 签到
     *
     * @param query 签到参数
     * @author yx
     * @date 2022/11/15 19:21
     */
    @Override
    public void signIn(OrderQueryVO query) {
    }
}
