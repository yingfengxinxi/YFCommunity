package com.wr.estate.service.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.HomeLedVo;
import com.wr.remote.work.inspect.OrderRecord;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 11:11:28
 * @Desc:  居家维修台账
 */
public interface HomLedService {

    /**
     * 分页查询
     * @param homeLedVo
     * @return
     */
    List<HomeLedVo> listHomLed(HomeLedVo homeLedVo);

    /**
     * 详情查询
     * @param orderId
     * @return
     */
    HomeLedVo getHomLed(Long orderId);

    /*协助人员列表*/
    List<HomeLedVo> getHomStaffList(Long orderId);
    /**
     * 详情下 查找执行详情
     * @param orderId
     * @return
     */
    List<OrderRecord> deatilListThree(Long orderId);

    /*工单图片列表*/
    List<HomeLedVo> orderImg(Long orderId);
}
