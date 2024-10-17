package com.wr.estate.service.center.audit;

import com.wr.estate.entity.vo.BusVehicleAuditvo;
import com.wr.estate.entity.vo.BusVehicleImportVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 16:27:13
 * @Desc:
 */
public interface BusVehicleAuditService {

    /**
     * 查询审核数据集合
     *
     * @param busVehicleAuditvo
     * @return
     */
    List<BusVehicleAuditvo> listOwnerAuditQure(BusVehicleAuditvo busVehicleAuditvo);


    /**
     * 批量删除业主审批信息
     *
     * @param vehicleId 需要删除的参数ID
     */
    void deleteConfigByIds(Long vehicleId);


    /**
     * 查询单个业主审核信息
     *
     * @param vehicleId
     * @return
     */
    BusVehicleAuditvo selectOneOwneAuditVo(Long vehicleId);

    /**
     * 审核通过或驳回
     *
     * @param vehicleId
     * @param auditStatus
     * @param rejectReason
     * @return
     */
    int passAndRejection(Long vehicleId, String auditStatus, String rejectReason);

    /**
     * @param vehicleList
     * @param estateId
     * @return
     */
    String importVehicle(List<BusVehicleImportVo> vehicleList, int estateId);
}
