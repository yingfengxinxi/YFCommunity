package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.estate.entity.vo.application.serve.ParkOrderVo;
import com.wr.remote.estate.financial.ParkOrder;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:25
 */
public interface ParkOrderService extends IService<ParkOrder> {

    public void add(ParkOrder parkOrder);

    /**
     * @param userId
     * @return
     */
    public List<ParkOrderVo> getList(Long userId);


    /**
     * @param id
     * @return
     */
    public ParkOrderVo get(String id);
}
