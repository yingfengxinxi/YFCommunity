package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.WatLedMapper;
import com.wr.estate.service.work.orderLedger.WatLedService;
import com.wr.remote.estate.order.vo.ledgerVo.WatchmanLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-28 17:57:04
 * @Desc: 巡更台账
 */
@Service
public class WatLedServiceImpl implements WatLedService {
    @Autowired
    private WatLedMapper watLedMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    @Override
    public List<WatchmanLedVo> listWatLed(WatchmanLedVo watchmanLedVo) {
        return watLedMapper.listWatLed(watchmanLedVo);
    }

    @Override
    public WatchmanLedVo getWatLed(Long watchmanId) {
        return watLedMapper.getWatLed(watchmanId);
    }

    @Override
    public List<WatchmanLedVo> getWatStaffList(Long watchmanId) {
        return watLedMapper.getWatStaffList(watchmanId);
    }

    @Override
    public List<WatchmanLedVo> getTourName(Long communityId) {
        return watLedMapper.getTourName(communityId);
    }

    @Override
    public List<WatchmanLedVo> getRouteName(Long communityId) {
        return watLedMapper.getRouteName(communityId);
    }

    @Override
    public List<OrderRecord> deatilListThree(Long watchmanId) {
        return orderRecordMapper.deatilListThree(watchmanId, OrderConstants.WATCHMAN);
    }
}
