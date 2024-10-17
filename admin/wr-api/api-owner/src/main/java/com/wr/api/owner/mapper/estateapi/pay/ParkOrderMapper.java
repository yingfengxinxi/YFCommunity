package com.wr.api.owner.mapper.estateapi.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.api.owner.entity.vo.pay.ParkOrderVo;
import com.wr.remote.estate.financial.ParkOrder;
import com.wr.remote.estate.financial.ParkOrderDel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:24
 */
public interface ParkOrderMapper extends BaseMapper<ParkOrder> {

    /**
     *
     * @param userId
     * @return
     */
    List<ParkOrderVo> getList(@Param("userId") Long userId);

    /**
     * @param orderNo
     */
    void deleteByOrderNo(@Param("orderNo") String orderNo);

    /**
     *
     * @param orderNo
     * @return
     */
    ParkOrderDel getByOrderNoInfo(@Param("orderNo") String orderNo);
}
