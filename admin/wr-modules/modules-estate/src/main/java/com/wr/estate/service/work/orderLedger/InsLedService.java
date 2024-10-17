package com.wr.estate.service.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.InspectLedVo;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 20:10:37
 * @Desc: 巡检台账
 */
public interface InsLedService {
    /**
     * 分页查询
     * @param inspectLedVo
     * @return
     */
    List<InspectLedVo> listInspect(InspectLedVo inspectLedVo);

    /**
     * 详情查询
     * @param inspectId
     * @return
     */
    InspectLedVo getInspect(Long inspectId);
    /*协助人员列表*/
    List<InspectLedVo> getInsStaffList(Long inspectId);
    /**
     * 详情内容 -> 设备详情
     * @param inspectId
     * @return
     */
    List<InspectLedVo> detailList(Long inspectId);
    /*巡检类型名字列表*/
    List<InspectLedVo> getTourName(Long communityId);
    /**
     * 详情下 查找执行详情
     * @param inspectId
     * @return
     */
    List<OrderRecord> deatilListThree(Long inspectId);
}
