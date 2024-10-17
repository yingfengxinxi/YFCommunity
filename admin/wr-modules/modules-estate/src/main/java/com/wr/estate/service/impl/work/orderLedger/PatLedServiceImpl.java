package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.PatLedMapper;
import com.wr.estate.service.work.orderLedger.PatLedService;
import com.wr.remote.estate.order.vo.ledgerVo.PatrolLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 17:33:48
 * @Desc:
 */
@Service
public class PatLedServiceImpl implements PatLedService {
    @Autowired
    private PatLedMapper patLedMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public List<PatrolLedVo> listPatLed(PatrolLedVo patrolLedVo) {
        return patLedMapper.listPatLed(patrolLedVo);
    }

    @Override
    public PatrolLedVo getPatLed(Long patrolId) {
        return patLedMapper.getPatLed(patrolId);
    }

    @Override
    public List<PatrolLedVo> getPatStaffList(Long patrolId) {
        return patLedMapper.getPatStaffList(patrolId);
    }

    @Override
    public List<PatrolLedVo> getTourName(Long communityId) {
        return patLedMapper.getTourName(communityId);
    }

    @Override
    public List<OrderRecord> deatilListThree(Long patrolId) {
        return orderRecordMapper.deatilListThree(patrolId, OrderConstants.PATROL);
    }
}
