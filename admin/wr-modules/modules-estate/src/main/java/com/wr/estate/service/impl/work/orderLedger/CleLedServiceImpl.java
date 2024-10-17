package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.CleLedMapper;
import com.wr.estate.service.work.orderLedger.CleLedService;
import com.wr.remote.estate.order.vo.ledgerVo.CleanLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 13:53:55
 * @Desc:
 */
@Service
public class CleLedServiceImpl implements CleLedService {
    @Autowired
    private CleLedMapper cleLedMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    @Override
    public List<CleanLedVo> listCleLed(CleanLedVo cleanLedVo) {
        return cleLedMapper.listCleLed(cleanLedVo);
    }

    @Override
    public CleanLedVo getCleLed(Long cleanId) {
        return cleLedMapper.getCleLed(cleanId);
    }

    @Override
    public List<CleanLedVo> getCleStaffList(Long cleanId) {
        return cleLedMapper.getCleStaffList(cleanId);
    }

    @Override
    public List<OrderRecord> deatilListThree(Long cleanId) {
        return orderRecordMapper.deatilListThree(cleanId, OrderConstants.CLEAN);
    }
}
