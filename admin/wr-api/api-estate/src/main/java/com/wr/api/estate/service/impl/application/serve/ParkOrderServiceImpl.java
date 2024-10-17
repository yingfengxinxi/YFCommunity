package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.entity.dto.ParkDurationPayDTO;
import com.wr.api.estate.entity.vo.application.serve.ParkOrderVo;
import com.wr.api.estate.mapper.application.serve.ParkOrderMapper;
import com.wr.api.estate.service.application.serve.ParkOrderService;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.estate.financial.ParkOrder;
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

        return baseMapper.getList(userId);
    }

    @Override
    public ParkOrderVo get(String id) {
        return baseMapper.get(id);
    }
}
