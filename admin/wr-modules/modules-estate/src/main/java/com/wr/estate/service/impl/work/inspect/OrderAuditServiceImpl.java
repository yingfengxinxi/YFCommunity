//package com.wr.estate.service.impl.work.inspect;
//
//import com.wr.common.core.exception.ServiceException;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.mapper.work.inspect.OrderAuditMapper;
//import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
//import com.wr.estate.mapper.work.inspect.OrderTransferMapper;
//import com.wr.estate.mapper.work.patrol.PatrolMapper;
//import com.wr.estate.service.work.inspect.OrderAuditService;
//import com.wr.remote.estate.manage.Staff;
//import com.wr.remote.estate.order.vo.OrderAuditDetailVO;
//import com.wr.remote.estate.order.vo.OrderAuditListVO;
//import com.wr.remote.estate.order.vo.OrderAuditQueryVO;
//import com.wr.remote.work.inspect.OrderAudit;
//import com.wr.remote.work.inspect.OrderRecord;
//import com.wr.remote.work.patrol.vo.AssistStaffListVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 工单协助审核
// *
// * @Author: lvzy
// * @Date: 2022-10-31 20:05:11
// * @Desc: 工单协助审核
// */
//@Service
//public class OrderAuditServiceImpl implements OrderAuditService {
//
//    @Autowired
//    private OrderAuditMapper auditMapper;
//
//    @Autowired
//    private PatrolMapper patrolMapper;
//
//    @Autowired
//    private OrderRecordMapper orderRecordMapper;
//
//    @Autowired
//    private OrderTransferMapper orderTransferMapper;
//
//
//    /**
//     * 分页查询列表
//     *
//     * @param query 查询条件
//     * @return java.util.List<com.wr.remote.estate.order.vo.OrderAuditListVO>
//     * @author yx
//     * @date 2022/11/4 15:41
//     */
//    @Override
//    public List<OrderAuditListVO> listPage(OrderAuditQueryVO query) {
//        if (!StringUtils.hasLength(query.getOrderType())) {
//            throw new ServiceException("参数异常");
//        }
//        return auditMapper.listPage(query);
//    }
//
//    /**
//     * 修改
//     *
//     * @param audit 审核信息
//     * @author yx
//     * @date 2022/11/4 15:44
//     */
//    @Override
//    public void update(OrderAudit audit) {
//        auditMapper.updateByPrimaryKeySelective(audit);
//    }
//
//    /**
//     * 审核驳回
//     *
//     * @param audit 驳回参数
//     * @author yx
//     * @date 2022/11/4 16:12
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void reject(OrderAudit audit) {
//        if (ObjectUtils.isEmpty(audit.getAuditId())) {
//            throw new ServiceException("参数异常");
//        }
//        Long userId = SecurityUtils.getUserId();
//        audit.setAuditStatus("2");
//        audit.setUserId(userId);
//        audit.setAuditTime(new Date());
//        update(audit);
//
//        // 转交记录修改
//        orderTransferMapper.updateByAuditId(audit);
//
//        // 查询审核信息
//        OrderAudit info = auditMapper.getInfo(audit.getAuditId());
//        if ("0".equals(info.getAuditType())) {
//            // 转交记录修改
//            orderTransferMapper.updateByAuditId(info);
//            // 修改工单转交人
//            auditMapper.updateOrderTransferStaff(info);
//        }
//    }
//
//    /**
//     * 审核同意
//     *
//     * @param audit 同意参数
//     * @author yx
//     * @date 2022/11/4 16:12
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void adopt(OrderAudit audit) {
//        if (ObjectUtils.isEmpty(audit.getAuditId())) {
//            throw new ServiceException("参数异常");
//        }
//        Long userId = SecurityUtils.getUserId();
//        audit.setAuditStatus("1");
//        audit.setUserId(userId);
//        audit.setAuditTime(new Date());
//        update(audit);
//
//        // 查询审核信息
//        OrderAudit info = auditMapper.getInfo(audit.getAuditId());
//        OrderAuditQueryVO query = new OrderAuditQueryVO();
//        query.setOrderType(info.getOrderType());
//        query.setAuditId(info.getAuditId());
//        // 查询审核额外信息
//        OrderAuditDetailVO detail = getDetail(query);
//        String relevant = "";
//        // 相关人员数据
//        if ("0".equals(info.getAuditType())) {
//            // 转交人员
//            relevant = detail.getReceiveName();
//
//            // 转交记录修改
//            orderTransferMapper.updateByAuditId(info);
//            // 修改工单转交人
//            auditMapper.updateOrderTransferStaff(info);
//            // 如果转交人也是协助人，则删除转交人
//            auditMapper.deleteAssistByStaffId(audit.getAuditId());
//        } else if ("1".equals(info.getAuditType())) {
//            // 协助人员
//            relevant = detail.getStaffList().stream().map(AssistStaffListVO::getStaffName).collect(Collectors.joining(","));
//        }
//
//        OrderRecord orderRecord = new OrderRecord();
//        orderRecord.setOrderType(info.getOrderType());
//        orderRecord.setOrderId(info.getOrderId());
//        orderRecord.setUserName(detail.getApplyName());
//        orderRecord.setRelevant(relevant);
//        orderRecord.setDataType("0");
//        orderRecord.setOrderResult("0".equals(info.getAuditType()) ? "工单转交" : "工单协助");
//        // 添加工单执行情况记录
//        orderRecordMapper.insertSelective(orderRecord);
//    }
//
//    /**
//     * 审核详情
//     *
//     * @param query 查询条件
//     * @return com.wr.remote.estate.order.vo.OrderAuditDetailVO
//     * @author yx
//     * @date 2022/11/4 17:44
//     */
//    @Override
//    public OrderAuditDetailVO getDetail(OrderAuditQueryVO query) {
//        OrderAuditDetailVO detail = auditMapper.getDetail(query.getOrderType(), query.getAuditId());
//        // 判断是转交还是协助
//        // 工单转交
//        if ("0".equals(detail.getAuditType())) {
//            // 查询转交人信息
//            Staff staff = orderTransferMapper.getTransferTargetStaffByAuditId(detail.getAuditId());
//            if (com.wr.common.core.utils.StringUtils.isNull(staff)) {
//                staff = new Staff();
//            }
//            detail.setReceiveName(staff.getStaffName());
//            detail.setReceivePhone(staff.getStaffPhone());
//        } else if ("1".equals(detail.getAuditType())) {
//            // 工单协助，查询协助参数
//            List<AssistStaffListVO> assistStaffList = patrolMapper.listAuditAssistants(query.getOrderType(), detail.getAuditId(), null);
//            detail.setStaffList(assistStaffList);
//        }
//        return detail;
//    }
//}
