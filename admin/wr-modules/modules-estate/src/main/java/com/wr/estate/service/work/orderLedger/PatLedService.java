package com.wr.estate.service.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.PatrolLedVo;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 17:32:52
 * @Desc:
 */
public interface PatLedService {
    /**
     * 分页查询
     * @param patrolLedVo
     * @return
     */
    List<PatrolLedVo> listPatLed(PatrolLedVo patrolLedVo);

    /**
     * 详情查询
     * @param patrolId
     * @return
     */
    PatrolLedVo getPatLed(Long patrolId);

    /*协助人员列表*/
    List<PatrolLedVo> getPatStaffList(Long patrolId);

    /*巡查类型名字列表*/
    List<PatrolLedVo> getTourName(Long communityId);

    /**
     * 详情下 查找执行详情
     * @param patrolId
     * @return
     */
    List<OrderRecord> deatilListThree(Long patrolId);
}
