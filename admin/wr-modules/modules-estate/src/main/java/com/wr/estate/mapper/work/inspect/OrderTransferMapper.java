package com.wr.estate.mapper.work.inspect;

import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.inspect.OrderTransfer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工单转交记录表
 *
 * @Author: lvzy
 * @Date: 2022-11-01 15:51:21
 * @Desc: 工单转交记录表
 */
@Mapper
public interface OrderTransferMapper {

    /**
     * 新增
     *
     * @param orderTransfer
     * @return
     */
    Integer insertSelective(OrderTransfer orderTransfer);

    /**
     * 详情
     *
     * @param transferId
     * @return
     */
    Staff getTransferTargetStaffByAuditId(Long transferId);

    /**
     * 修改
     *
     * @param orderTransfer
     * @return
     */
    int updateByPrimaryKeySelective(OrderTransfer orderTransfer);

    void updateByAuditId(OrderAudit orderAudit);
}
