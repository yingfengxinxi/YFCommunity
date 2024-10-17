package com.wr.estate.service.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.WatchmanLedVo;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-28 17:56:45
 * @Desc: 巡更台账
 */
public interface WatLedService {
    /**
     * 分页查询
     * @param watchmanLedVo
     * @return
     */
    List<WatchmanLedVo> listWatLed(WatchmanLedVo watchmanLedVo);

    /**
     * 详情查询
     * @param watchmanId
     * @return
     */
    WatchmanLedVo getWatLed(Long watchmanId);

    /*协助人员列表*/
    List<WatchmanLedVo> getWatStaffList(Long watchmanId);

    /*巡更类型名字列表*/
    List<WatchmanLedVo> getTourName(Long communityId);

    /**
     * 路线类型名字列表
     * @param communityId
     * @return
     */
    List<WatchmanLedVo> getRouteName(Long communityId);
    /**
     * 详情下 查找执行详情
     * @param watchmanId
     * @return
     */
    List<OrderRecord> deatilListThree(Long watchmanId);
}
