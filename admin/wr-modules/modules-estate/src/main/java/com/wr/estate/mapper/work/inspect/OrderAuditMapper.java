package com.wr.estate.mapper.work.inspect;

import com.wr.remote.estate.order.vo.OrderAuditDetailVO;
import com.wr.remote.estate.order.vo.OrderAuditListVO;
import com.wr.remote.estate.order.vo.OrderAuditQueryVO;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.vo.BusDisasterAuditVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单协助审核表
 *
 * @Author: lvzy
 * @Date: 2022-10-31 19:55:11
 * @Desc: 工单协助审核表
 */
@Mapper
public interface OrderAuditMapper {

    /**
     * 根据id查询数据
     *
     * @param id id
     * @return com.wr.remote.work.inspect.OrderAudit
     * @author yx
     * @date 2022/11/5 16:10
     */
    OrderAudit getInfo(Long id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    Integer insertSelective(OrderAuditListVO record);

    /**
     * @param audit
     * @return
     */
    Integer updateByPrimaryKeySelective(OrderAudit audit);

    /**
     * 分页查询审核列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.estate.order.vo.OrderAuditListVo>
     * @author yx
     * @date 2022/11/4 15:09
     */
    List<OrderAuditListVO> listPage(OrderAuditQueryVO query);


    /**
     * 审核详情
     *
     * @param orderType 工单类型
     * @param auditId   审核id
     * @return com.wr.remote.estate.order.vo.OrderAuditDetailVO
     * @author yx
     * @date 2022/11/4 17:44
     */
    OrderAuditDetailVO getDetail(@Param("orderType") String orderType,
                                 @Param("auditId") Long auditId);

    /**
     * 修改工单转交人
     *
     * @param orderAudit 审核信息
     * @author yx
     * @date 2022/11/16 18:58
     */
    void updateOrderTransferStaff(OrderAudit orderAudit);

    /**
     * 删除协助人
     *
     * @param auditId 审核id
     */
    void deleteAssistByStaffId(Long auditId);

    /**
     * 工单审核
     * @param auditId
     * @return: int
     * @date: 2023/1/10 9:39
     * @author: SJiang
     **/
    int updateById(Long auditId);


    Integer updateByIdEmp(BusDisasterAuditVo busDisasterAuditVo);
}
