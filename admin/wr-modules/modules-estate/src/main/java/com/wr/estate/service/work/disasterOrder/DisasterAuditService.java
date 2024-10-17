package com.wr.estate.service.work.disasterOrder;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.work.vo.BusDisasterAuditVo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-28 14:19:35
 * @Desc:
 */
public interface DisasterAuditService {
    /**
     * 分页查询灾害报事审核
     * @param busDisaster
     * @return
     */
    List<BusDisasterAuditVo> disasterAuditService(BusDisasterAuditVo busDisaster);

    /**
     * 审核通过（业主）
     * @param busDisasterAuditVo
     * @return
     */
    AjaxResult upOwnerAuditStatus(BusDisasterAuditVo busDisasterAuditVo);

    /**
     * 审核通过（员工）
     * @param busDisasterAuditVo
     * @return
     */
    AjaxResult upStaffAuditStatus(BusDisasterAuditVo busDisasterAuditVo);

    /**
     * 驳回
     * @param busDisasterAuditVo
     * @return
     */
    Integer rejectStatus(BusDisasterAuditVo busDisasterAuditVo);

    /**
     * 分页查询灾害报事审核员工申请
     * @param busDisasterAuditVo
     * @return
     */
    List<BusDisasterAuditVo> listPage(BusDisasterAuditVo busDisasterAuditVo);

    /**
     * 查询工单异常
     * @param id
     * @return
     */
    List<LinkedHashMap<String,Object>> queryError(Long id);


}
