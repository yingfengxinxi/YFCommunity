package com.wr.estate.service.impl.center.audit;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusTenant;
import com.wr.estate.entity.vo.TenantAuditVo;
import com.wr.estate.mapper.center.audit.BusTenantMapper;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.service.center.audit.BusTenantService;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.RentContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-15 17:14:43
 * @Desc: 租客审核接口实现类
 */
@Service
public class BusTenantServiceImpl implements BusTenantService {

    @Resource
    private BusTenantMapper busTenantMapper;
    @Autowired
    private OwnerFileMapper ownerFileMapper;

    @Override
    public List<TenantAuditVo> listOwnerAuditQure(BusTenant busTenant) {
        return  busTenantMapper.selectListOwnerAudit(busTenant);
    }

    @Override
    public void deleteConfigByIds(Long[] tenantIds) {
        for (Long tenantId : tenantIds)
        {
            busTenantMapper.deleteByPrimaryKeys(tenantId, SecurityUtils.getUsername());
        }
    }

    @Override
    public TenantAuditVo selectOneOwneAuditVo(Long tenantId) {
        return busTenantMapper.selectoneOwneAuditVo(tenantId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult passAndRejection(Long tenantId, Long roomId, String auditStatus, String rejectReason) {
        // 获取租客详情
        TenantAuditVo tenantAuditVo = busTenantMapper.selectoneOwneAuditVo(tenantId);
        if (StringUtils.isNull(tenantAuditVo)){
            return AjaxResult.error("操作失败，未获取到租客信息");
        }
        // 租客审批
        int num = busTenantMapper.updataByownerIdAndAuditStatus(tenantId, auditStatus, rejectReason, SecurityUtils.getUsername());
        // 租客审核通过改变房间状态（出租）
        if ("1".equals(auditStatus)){
            ownerFileMapper.increaseLive(roomId);
        }
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
