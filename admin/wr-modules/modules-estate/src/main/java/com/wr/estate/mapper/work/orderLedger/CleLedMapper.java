package com.wr.estate.mapper.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.CleanLedVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 17:54:26
 * @Desc: 保洁台账
 */
public interface CleLedMapper {
    /**
     * 分页查询
     * @param cleanLedVo
     * @return
     */
    List<CleanLedVo> listCleLed(CleanLedVo cleanLedVo);

    /**
     * 详情查询
     * @param cleanId
     * @return
     */
    CleanLedVo getCleLed(Long cleanId);

    /*协助人员列表*/
    List<CleanLedVo> getCleStaffList(Long cleanId);

}
