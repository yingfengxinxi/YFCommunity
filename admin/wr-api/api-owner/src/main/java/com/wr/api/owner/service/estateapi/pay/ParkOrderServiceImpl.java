package com.wr.api.owner.service.estateapi.pay;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.vo.pay.ParkOrderVo;
import com.wr.api.owner.mapper.estateapi.pay.ParkOrderMapper;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.estate.financial.ParkOrder;
import com.wr.remote.estate.financial.ParkOrderDel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:25
 */
@Service
public class ParkOrderServiceImpl extends ServiceImpl<ParkOrderMapper, ParkOrder> implements ParkOrderService {

    @Autowired
    private RedisService redisService;

    @Override
    public void add(ParkOrder parkOrder) {
        parkOrder.setCreateTime(new Date());
        baseMapper.insert(parkOrder);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<ParkOrderVo> getList(Long userId) {
        List<ParkOrderVo> list = baseMapper.getList(userId);
        return list;
    }

    @Override
    public void deleteByOrderNo(String orderNo) {
        baseMapper.deleteByOrderNo(orderNo);
    }

    @Override
    public ParkOrderDel getByOrderNoInfo(String orderNo) {
        return baseMapper.getByOrderNoInfo(orderNo);
    }
}
