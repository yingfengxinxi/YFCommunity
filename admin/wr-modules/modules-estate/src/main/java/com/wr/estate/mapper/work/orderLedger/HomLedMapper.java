package com.wr.estate.mapper.work.orderLedger;

import com.wr.remote.estate.order.vo.ledgerVo.CleanLedVo;
import com.wr.remote.estate.order.vo.ledgerVo.HomeLedVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 10:37:26
 * @Desc: 居家维修台账
 */
public interface HomLedMapper {
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

    /*工单图片列表*/
    List<HomeLedVo> orderImg(Long orderId);

}
