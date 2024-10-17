package com.wr.estate.service.center.audit;


import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.BusTenant;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.estate.entity.vo.TenantAuditVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-15 17:06:43
 * @Desc: 租客审核服务类
 */
public interface BusTenantService {

    /**
     * 查询租客审核数据集合
     * @param busTenant
     * @return
     */
    List<TenantAuditVo> listOwnerAuditQure(BusTenant busTenant);


    /**
     * 批量删除租客审批信息
     *
     * @param tenantIds 需要删除的参数ID
     */
    void deleteConfigByIds(Long[] tenantIds);


    /**
     * 查询单个租客审核信息
     * @param tenantId
     * @return
     */
    TenantAuditVo selectOneOwneAuditVo(Long tenantId);

    /**
     * 审核通过或驳回
     * @param tenantId
     * @param auditStatus
     * @param rejectReason
     * @return
     */
    AjaxResult passAndRejection(Long tenantId, Long roomId, String auditStatus, String rejectReason);
}
