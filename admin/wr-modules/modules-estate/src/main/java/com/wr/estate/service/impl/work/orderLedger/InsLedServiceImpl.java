package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.InspectLedgerMapper;
import com.wr.estate.service.work.orderLedger.InsLedService;
import com.wr.remote.estate.order.vo.ledgerVo.InspectLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 20:11:00
 * @Desc:
 */
@Service
public class InsLedServiceImpl implements InsLedService {

    @Autowired
    private InspectLedgerMapper inspectLedgerMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    /*分页查询*/
    @Override
    public List<InspectLedVo> listInspect(InspectLedVo inspectLedVo) {
        return inspectLedgerMapper.listInspect(inspectLedVo);
    }

    /*详情查询*/
    @Override
    public InspectLedVo getInspect(Long inspectId) {
        return inspectLedgerMapper.getInspect(inspectId);
    }

    /*协助人员列表*/
    @Override
    public List<InspectLedVo> getInsStaffList(Long inspectId) {
        return inspectLedgerMapper.getInsStaffList(inspectId);
    }

    @Override
    public List<InspectLedVo> detailList(Long inspectId) {
        return inspectLedgerMapper.detailList(inspectId);
    }

    @Override
    public List<InspectLedVo> getTourName(Long communityId) {
        return inspectLedgerMapper.getTourName(communityId);
    }

    @Override
    public List<OrderRecord> deatilListThree(Long inspectId) {
        return orderRecordMapper.deatilListThree(inspectId, OrderConstants.INSPECT);
    }
}
