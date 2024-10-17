package com.wr.api.owner.service.estateapi.pay;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.owner.entity.vo.pay.ParkOrderVo;
import com.wr.remote.estate.financial.ParkOrder;
import com.wr.remote.estate.financial.ParkOrderDel;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:25
 */
public interface ParkOrderService extends IService<ParkOrder> {

    public void add(ParkOrder parkOrder);

    /**
     *
     * @param userId
     * @return
     */
    public List<ParkOrderVo> getList(Long userId);

    /**
     *
     * @param orderNo
     */
    void deleteByOrderNo(String orderNo);

    /**
     *
     * @param orderNo
     * @return
     */
    ParkOrderDel getByOrderNoInfo(String orderNo);
}
