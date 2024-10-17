package com.wr.api.estate.mapper.order;

import com.wr.api.estate.entity.vo.order.OrderAssistsVO;
import com.wr.api.estate.entity.vo.order.OrderQueryVO;
import com.wr.api.estate.entity.vo.order.OrderStaffVO;
import com.wr.api.estate.entity.vo.order.OrderTransferVO;
import com.wr.remote.work.Abnormal;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.inspect.OrderTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-11 15:59:37
 * @Desc: 保洁工单mapper
 */
public interface OrderCommonMapper {

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
     * 新增执行情况
     *
     * @param orderRecord 执行情况
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/15 20:03
     */
    Integer insertOrderRecord(OrderRecord orderRecord);

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
     * 新增转交记录
     *
     * @param orderTransfer 转交情况
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/15 20:05
     */
    Integer insertOrderTransfer(OrderTransfer orderTransfer);

    /**
     * 新增审核
     *
     * @param orderAudit 审核信息
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/15 20:06
     */
    Integer insertOrderAudit(OrderAudit orderAudit);

    /**
     * 批量新增协助人
     *
     * @param auditId  审核id
     * @param staffIds 协助人list
     * @author yx
     * @date 2022/11/15 20:27
     */
    void insertOrderAssistBatch(@Param("auditId") Long auditId,
                                @Param("staffIds") List<Long> staffIds);

    /**
     * 新增工单异常
     *
     * @param abnormal 工单异常信息
     * @author yx
     * @date 2022/11/17 14:17
     */
    void insertAbnormal(Abnormal abnormal);

    /**
     * 批量新增
     *
     * @param images     异常图片地址
     * @param abnormalId 异常id
     * @author yx
     * @date 2022/11/17 14:20
     */
    void insertAbnormalImagesBatch(@Param("images") List<String> images, @Param("abnormalId") Long abnormalId);

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
    List<Integer> selectOrderPermission(@Param("workPost") String workPost,
                                        @Param("communityId") Long communityId);
}
