package com.wr.api.estate.service.order;

import com.wr.api.estate.entity.vo.order.OrderAssistsVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.OrderStaffVO;
import com.wr.api.estate.entity.vo.order.OrderTransferVO;
import com.wr.remote.work.Abnormal;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-15 09:40:21
 * @Desc:
 */
public interface OrderCommonService {

    /**
     * 工单协助人员查询
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderListVO>
     * @author yx
     * @date 2022/11/14 16:09
     */
    List<OrderAssistsVO> listAssists(OrderQueryVO query);

    /**
     * 查询协助审核中的数量
     * @author yx
     * @date 2022/12/24 13:27
     * @param query
     * @return java.lang.Integer
     */
    Integer countInAudit(OrderQueryVO query);

    /**
     * 查询转交人信息
     *
     * @param query 查询条件
     * @return com.wr.api.estate.entity.vo.order.OrderTransferVO
     * @author yx
     * @date 2022/11/15 16:37
     */
    OrderTransferVO getTransfer(OrderQueryVO query);

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
    void insertOrderRecorder(OrderQueryVO query,
                             String dataType,
                             String orderResult);

    /**
     * 查询工单最新执行情况
     *
     * @param query 查询条件
     * @return com.wr.remote.work.inspect.OrderRecord
     * @author yx
     * @date 2022/11/15 20:56
     */
    OrderRecord latestOrderRecord(OrderQueryVO query);

    /**
     * 签到次数
     *
     * @param query 查询条件
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/16 9:43
     */
    Integer signCount(OrderQueryVO query);

    /**
     * 新增申请转让审核和记录
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 20:09
     */
    void insertTransfer(OrderQueryVO order);

    /**
     * 请求协助
     *
     * @param order 工单信息
     * @author yx
     * @date 2022/11/15 20:22
     */
    void assist(OrderQueryVO order);

    /**
     * 新增工单异常
     *
     * @param abnormal 工单异常信息
     * @author yx
     * @date 2022/11/17 14:17
     */
    void insertAbnormal(Abnormal abnormal);

    /**
     * 批量新增工单异常图片
     *
     * @param images     异常图片地址
     * @param abnormalId 异常id
     * @author yx
     * @date 2022/11/17 14:20
     */
    void insertAbnormalImagesBatch(List<String> images, Long abnormalId);

    /**
     * 查询工单空闲 繁忙 的人员
     *
     * @param order 工单信息
     * @return java.util.List<com.wr.api.estate.entity.vo.order.OrderStaffVO>
     * @author yx
     * @date 2022/11/22 15:16
     */
    List<OrderStaffVO> listLeisureStaffs(OrderQueryVO order);


    /**
     * 查询协助人数
     *
     * @param order 工单信息
     * @return java.lang.Integer
     * @author yx
     * @date 2022/12/23 11:53
     */
    Integer helpNumber(OrderQueryVO order);

    /**
     * 查询工单权限
     *
     * @param workPost    工作职位
     * @param communityId 社区id
     * @return {@code List<Integer>}
     */
    List<Integer> selectOrderPermission(String workPost,Long communityId);

}
