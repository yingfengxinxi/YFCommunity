//package com.wr.estate.service.work.inspect;
//
//import com.wr.remote.estate.order.vo.OrderAuditDetailVO;
//import com.wr.remote.estate.order.vo.OrderAuditListVO;
//import com.wr.remote.estate.order.vo.OrderAuditQueryVO;
//import com.wr.remote.work.inspect.OrderAudit;
//
//import java.util.List;
//
///**
// * 工单协助审核
// *
// * @Author: lvzy
// * @Date: 2022-10-31 20:04:28
// * @Desc: 工单协助审核
// */
//public interface OrderAuditService {
//
//    /**
//     * 分页查询
//     *
//     * @param query 查询条件
//     * @return
//     */
//    List<OrderAuditListVO> listPage(OrderAuditQueryVO query);
//
//    /**
//     * 修改
//     *
//     * @param audit 审核信息
//     * @author yx
//     * @date 2022/11/4 15:43
//     */
//    void update(OrderAudit audit);
//
//    /**
//     * 审核驳回
//     *
//     * @param audit 驳回参数
//     * @author yx
//     * @date 2022/11/4 16:12
//     */
//    void reject(OrderAudit audit);
//
//    /**
//     * 审核同意
//     *
//     * @param audit 同意参数
//     * @author yx
//     * @date 2022/11/4 16:12
//     */
//    void adopt(OrderAudit audit);
//
//    /**
//     * 审核详情
//     *
//     * @param query 查询条件
//     * @return com.wr.remote.estate.order.vo.OrderAuditDetailVO
//     * @author yx
//     * @date 2022/11/4 17:44
//     */
//    OrderAuditDetailVO getDetail(OrderAuditQueryVO query);
//
//}
