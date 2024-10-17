package com.wr.estate.mapper.center.audit;

import com.wr.estate.entity.vo.BusVehicleAuditvo;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 16:16:50
 * @Desc:
 */
public interface BusVehicleAuditMapper {

    void insertvehicle(BusVehicleImportVo busVehicleImportVo);

    List<BusVehicleAuditvo> selectListVehicleAudit(BusVehicleAuditvo record);

    int deleteByPrimaryKeys(@Param("vehicleId") Long vehicleId);

    /**
     * @param vehicleId
     * @param estateId
     * @param accountType 0=业主;1=租客
     * @return
     */
    BusVehicleAuditvo selectoneOwneAuditVo(@Param("vehicleId") Long vehicleId, @Param("estateId") Long estateId, @Param("accountType") String accountType);

    int updataByownerIdAndAuditStatus(@Param("vehicleId") Long vehicleId, @Param("auditStatus") String auditStatus, @Param("rejectReason") String rejectReason, @Param("updateBy") String updateBy);

    String getVehicleIdAccountType(@Param("vehicleId") Long vehicleId);
}
