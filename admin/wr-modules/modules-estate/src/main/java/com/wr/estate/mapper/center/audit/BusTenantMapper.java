package com.wr.estate.mapper.center.audit;

import com.wr.estate.entity.BusTenant;
import com.wr.estate.entity.BusTenantExample;

import java.util.List;

import com.wr.estate.entity.vo.TenantAuditVo;
import com.wr.remote.estate.manage.contract.Account;
import org.apache.ibatis.annotations.Param;

public interface BusTenantMapper {
    int countByExample(BusTenantExample example);

    int deleteByExample(BusTenantExample example);

    int deleteByPrimaryKey(Long tenantId);

    int insert(BusTenant record);

    int insertSelective(BusTenant record);

    List<BusTenant> selectByExample(BusTenantExample example);

    BusTenant selectByPrimaryKey(Long tenantId);

    int updateByExampleSelective(@Param("record") BusTenant record, @Param("example") BusTenantExample example);

    int updateByExample(@Param("record") BusTenant record, @Param("example") BusTenantExample example);

    int updateByPrimaryKeySelective(BusTenant record);

    int updateByPrimaryKey(BusTenant record);

    List<TenantAuditVo> selectListOwnerAudit(BusTenant record);

    int deleteByPrimaryKeys(@Param("tenantId") Long tenantId, @Param("updateBy") String updateBy);

    TenantAuditVo selectoneOwneAuditVo(@Param("tenantId") Long tenantId);

    int updataByownerIdAndAuditStatus(@Param("tenantId") Long tenantId, @Param("auditStatus") String auditStatus, @Param("rejectReason") String rejectReason, @Param("updateBy") String updateBy);
}