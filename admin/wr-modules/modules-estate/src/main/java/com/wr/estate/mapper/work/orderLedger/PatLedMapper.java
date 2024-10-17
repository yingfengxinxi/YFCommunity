package com.wr.estate.mapper.work.orderLedger;

import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.order.vo.ledgerVo.PatrolLedVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 16:40:29
 * @Desc: 巡查台账
 */
public interface PatLedMapper {
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

    /*巡检类型名字列表*/
    List<PatrolLedVo> getTourName(Long communityId);
}
