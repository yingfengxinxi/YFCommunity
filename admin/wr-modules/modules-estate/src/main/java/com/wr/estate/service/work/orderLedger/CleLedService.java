package com.wr.estate.service.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.CleanLedVo;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 13:52:48
 * @Desc: 保洁台账
 */
public interface CleLedService {
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
    /**
     * 详情下 查找执行详情
     * @param cleanId
     * @return
     */
    List<OrderRecord> deatilListThree(Long cleanId);
}
