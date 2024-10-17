package com.wr.estate.service.center.audit;

import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-13 10:27:29
 * @Desc: 业主审核
 */
public interface BusOwnerService {
    /**
     * 查询业主审核数据集合
     * @param busOwner
     * @return
     */
    List<OwneAuditVo> listOwnerAuditQure(BusOwner busOwner);


    /**
     * 批量删除业主审批信息
     *
     * @param ownerIds 需要删除的参数ID
     */
    void deleteConfigByIds(Long[] ownerIds);


    /**
     * 查询单个业主审核信息
     * @param ownerId
     * @param roomId
     * @return
     */
    OwneAuditVo   selectOneOwneAuditVo(Long ownerId,Long roomId);

    /**
     * 审核通过或驳回
     * @param ownerId
     * @param auditStatus
     * @param rejectReason
     * @return
     */
    void passAndRejection(Long roomId,Long ownerId,String auditStatus,String rejectReason);
}
