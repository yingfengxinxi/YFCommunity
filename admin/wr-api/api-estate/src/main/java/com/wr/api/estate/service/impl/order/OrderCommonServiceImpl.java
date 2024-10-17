package com.wr.api.estate.service.impl.order;

import com.wr.api.estate.entity.vo.order.OrderAssistsVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.OrderStaffVO;
import com.wr.api.estate.entity.vo.order.OrderTransferVO;
import com.wr.api.estate.mapper.order.OrderCommonMapper;
import com.wr.api.estate.service.order.OrderCommonService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.work.Abnormal;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.inspect.OrderTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:48:21
 * @Desc: 保洁工单实现类
 */
@Service
public class OrderCommonServiceImpl implements OrderCommonService {

    @Autowired
    private OrderCommonMapper orderCommonMapper;

    /**
     * 工单协助人员查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    @Override
    public List<OrderAssistsVO> listAssists(OrderQueryVO query) {
        return orderCommonMapper.listAssists(query);
    }

    /**
     * 查询协助审核中的数量
     * @author yx
     * @date 2022/12/24 13:27
     * @param query
     * @return java.lang.Integer
     */
    @Override
    public Integer countInAudit(OrderQueryVO query) {
        return orderCommonMapper.countInAudit(query);
    }

    /**
     * 查询转交人信息
     *
     * @param query 查询条件
     * @return com.wr.api.estate.entity.vo.order.OrderTransferVO
     * @author yx
     * @date 2022/11/15 16:37
     */
    @Override
    public OrderTransferVO getTransfer(OrderQueryVO query) {
        return orderCommonMapper.getTransfer(query);
    }

    /**
     * 新增工单执行情况
     *
     * @param query       查询条件
     * @param dataType    数据类型：0=其他，1=执行
     * @param orderResult 结果
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/15 9:42
     */
    @Override
    public void insertOrderRecorder(OrderQueryVO query, String dataType, String orderResult) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType(String.valueOf(query.getOrderType()));
        orderRecord.setOrderId(query.getOrderId());
        orderRecord.setUserName(query.getStaffName());
        orderRecord.setRelevant("");
        orderRecord.setDataType(dataType);
        orderRecord.setOrderResult(orderResult);
        orderCommonMapper.insertOrderRecord(orderRecord);
    }

    /**
     * 查询工单最新执行情况
     *
     * @param query 查询条件
     * @return com.wr.remote.work.inspect.OrderRecord
     * @author yx
     * @date 2022/11/15 20:56
     */
    @Override
    public OrderRecord latestOrderRecord(OrderQueryVO query) {
        return orderCommonMapper.latestOrderRecord(query);
    }

    /**
     * 签到次数
     *
     * @param query 查询条件
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/16 9:43
     */
    @Override
    public Integer signCount(OrderQueryVO query) {
        return orderCommonMapper.signCount(query);
    }

    /**
     * 新增申请转让审核和记录
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 20:09
     */
    @Override
    public void insertTransfer(OrderQueryVO order) {
        OrderAudit orderAudit = new OrderAudit();
        orderAudit.setAuditType("0");
        orderAudit.setOrderType(String.valueOf(order.getOrderType()));
        orderAudit.setOrderId(order.getOrderId());
        orderAudit.setAuditStatus("0");
        orderAudit.setRejectReason("");
        orderAudit.setDelFlag("0");
        orderAudit.setCreateBy(SecurityUtils.getUsername());
        orderAudit.setRemark(order.getRemark());
        orderCommonMapper.insertOrderAudit(orderAudit);

        // 新增转交记录
        OrderTransfer orderTransfer = new OrderTransfer();
        orderTransfer.setAuditId(orderAudit.getAuditId());
        orderTransfer.setOrderType(String.valueOf(order.getOrderType()));
        orderTransfer.setOrderId(order.getOrderId());
        orderTransfer.setSourceId(order.getStaffId());
        orderTransfer.setTargetId(order.getTargetId());
        orderTransfer.setAcceptType("0");
        orderTransfer.setDelFlag("0");

        orderCommonMapper.insertOrderTransfer(orderTransfer);
    }

    /**
     * 请求协助
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 20:22
     */
    @Override
    public void assist(OrderQueryVO order) {
        OrderAudit orderAudit = new OrderAudit();
        orderAudit.setAuditType("1");
        orderAudit.setOrderType(String.valueOf(order.getOrderType()));
        orderAudit.setOrderId(order.getOrderId());
        orderAudit.setAuditStatus("0");
        orderAudit.setRejectReason("");
        orderAudit.setDelFlag("0");
        orderAudit.setCreateBy(order.getUserName());
        orderAudit.setRemark(order.getRemark());
        orderAudit.setUserId(order.getUserId());
        orderCommonMapper.insertOrderAudit(orderAudit);

        // 批量新增协助人信息
        orderCommonMapper.insertOrderAssistBatch(orderAudit.getAuditId(), order.getAssistIds());
    }

    /**
     * 新增工单异常
     *
     * @param abnormal 工单异常信息
     * @author yx
     * @date 2022/11/17 14:17
     */
    @Override
    public void insertAbnormal(Abnormal abnormal) {
        orderCommonMapper.insertAbnormal(abnormal);
    }

    /**
     * 批量新增工单异常图片
     *
     * @param images     异常图片地址
     * @param abnormalId 异常id
     * @author yx
     * @date 2022/11/17 14:20
     */
    @Override
    public void insertAbnormalImagesBatch(List<String> images, Long abnormalId) {
        orderCommonMapper.insertAbnormalImagesBatch(images, abnormalId);
    }

    /**
     * 查询工单空闲 繁忙 的人员
     *
     * @param order 工单信息
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderStaffVO>
     * @author yx
     * @date 2022/11/22 15:16
     */
    @Override
    public List<OrderStaffVO> listLeisureStaffs(OrderQueryVO order) {
        return orderCommonMapper.listLeisureStaffs(order);
    }

    /**
     * 查询协助人数
     *
     * @param order 工单信息
     * @return java.lang.Integer
     * @author yx
     * @date 2022/12/23 11:53
     */
    @Override
    public Integer helpNumber(OrderQueryVO order) {
        return orderCommonMapper.helpNumber(order);
    }

    /**
     * 查询工单权限
     *
     * @param workPost    工作职位
     * @param communityId 社区id
     * @return {@code List<Integer>}
     */
    @Override
    public List<Integer> selectOrderPermission(String workPost, Long communityId) {
        return orderCommonMapper.selectOrderPermission(workPost, communityId);
    }
}
