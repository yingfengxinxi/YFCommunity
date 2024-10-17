package com.wr.api.estate.service.impl.order;

import com.wr.api.estate.entity.vo.order.*;
import com.wr.api.estate.mapper.order.InspectOrderMapper;
import com.wr.api.estate.mapper.order.OrderCommonMapper;
import com.wr.api.estate.service.order.OrderCommonService;
import com.wr.api.estate.service.order.OrderService;
import com.wr.common.core.exception.ServiceException;
import com.wr.remote.work.Abnormal;
import com.wr.remote.work.vo.InspectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:48:21
 * @Desc: 巡检工单实现类
 */
@Service("inspectOrderService")
public class InspectOrderServiceImpl implements OrderService {

    @Autowired
    private InspectOrderMapper inspectOrderMapper;
    @Autowired
    private OrderCommonMapper orderCommonMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    /**
     * 巡检工单移动端列表查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    @Override
    public List<OrderListVO> listPage(OrderQueryVO query) {
        return inspectOrderMapper.listInspectOrders(query);
    }

    /**
     * 工单详情
     *
     * @param query 查询条件
     * @return T
     * @author yx
     * @date 2022/11/15 10:23
     */
    @Override
    public Object detail(OrderQueryVO query) {
        InspectOrderDetailVO detail = inspectOrderMapper.detailInspectOrders(query.getOrderId());
        Integer hasCharge = 2;
        if (Objects.equals(query.getStaffId(), detail.getStaffId())){
            hasCharge = 1;
        }
        detail.setHasCharge(hasCharge);

        // 查询协助人员
        List<OrderAssistsVO> orderAssistsList = orderCommonMapper.listAssists(query);
        detail.setOrderAssists(orderAssistsList);

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
        int i = inspectOrderMapper.robOrder(query);
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
        int i = inspectOrderMapper.receiveOrder(query);
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
        int i = inspectOrderMapper.startPerform(order);
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
        // 查询工单信息
        InspectVo inspectVo = inspectOrderMapper.selectByPrimaryKey(order.getOrderId());
        if (Objects.equals(inspectVo.getExecuteStatus(), "2") || Objects.equals(inspectVo.getExecuteStatus(), "3")) {
            throw new ServiceException("工单已完成，请勿重复操作！",1000);
        }

        // 查询巡查点
        Integer deviceCount = inspectOrderMapper.inspectDeviceCount(inspectVo.getInspectId());

        // 查询工单签到次数
        int count = orderCommonService.signCount(order);
        //  一日多查，校验签到次数和巡查频率
        if (inspectVo.getGapTime().doubleValue() != 0) {
            if (count < inspectVo.getPatrolNum() * deviceCount) {
                throw new ServiceException("完成工单失败，您还未完成" + inspectVo.getPatrolNum() * deviceCount + "次签到！",1000);
            }
        } else {
            //多日一查
            if (count <= 0) {
                throw new ServiceException("完成工单失败，您还未签到！",1000);
            }
        }

        inspectOrderMapper.finish(order);

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

        int i = inspectOrderMapper.transfer(order);
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
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 19:21
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signIn(OrderQueryVO order) {
        // 查询工单信息
        InspectVo inspect = inspectOrderMapper.selectByPrimaryKey(order.getOrderId());

        if ("2".equals(inspect.getExecuteStatus()) || "3".equals(inspect.getExecuteStatus())){
            throw new ServiceException("签到失败，工单已完成！",1000);
        }

        // 查询所有工单协助人员
        List<OrderAssistsVO> orderAssists = orderCommonService.listAssists(order);

        // 校验签到人员是否可以进行签到
        boolean match = orderAssists.stream().anyMatch(t -> t.getStaffId().equals(order.getStaffId()));
        if (!order.getStaffId().equals(inspect.getStaffId()) && !match){
            throw new ServiceException("签到失败，仅工单相关人员扫码签到！",1000);
        }

        // 获取下一个签到点
        List<DeviceDetailVO> points = inspectOrderMapper.listInspectDevices(order.getOrderId());

        // 校验当前工单是否包含巡查点
        boolean anyMatch = points.stream().anyMatch(t -> Objects.equals(order.getId(), String.valueOf(t.getIndexCode())) && Objects.equals(order.getDeviceType(), String.valueOf(t.getDeviceType())));
        if (!anyMatch) {
            throw new ServiceException("签到失败，请在指定巡检设备进行扫码签到！",1000);
        }

        //  多日一查情况，校验上次签到时间距离本次签到时间
        if (inspect.getGapTime().doubleValue() != 0) {
            // TODO 后面再优化一日多查，暂不考虑一日多查
            // TODO 一日多查情况，校验上次签到时间距离本次签到时间
//            oneDayCheck(order, patrol.getGapTime());
        }

        // 没有异常，说明可以进行签到
        // 签到
        orderCommonService.insertOrderRecorder(order, "1", "签到");
        // 巡查点签到
        inspectOrderMapper.signInDevice(order);
        // 异常，插入异常记录
        if (order.getSignInStatus() == 2) {
            // 新增工单异常
            Abnormal abnormal = new Abnormal();
            abnormal.setOrderId(order.getOrderId());
            abnormal.setOrderType(String.valueOf(order.getOrderType()));
            abnormal.setAbnormalInfo(order.getRemark());
            orderCommonService.insertAbnormal(abnormal);

            orderCommonService.insertAbnormalImagesBatch(order.getImages(), abnormal.getAbnormalId());
        }
    }
}
