package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.HomLedMapper;
import com.wr.estate.service.work.orderLedger.HomLedService;
import com.wr.remote.estate.order.vo.ledgerVo.HomeLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 11:12:51
 * @Desc: 居家维修台账
 */
@Service
public class HomLedServiceImpl implements HomLedService {
    @Autowired
    private HomLedMapper homLedMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    @Override
    public List<HomeLedVo> listHomLed(HomeLedVo homeLedVo) {
        return homLedMapper.listHomLed(homeLedVo);
    }

    @Override
    public HomeLedVo getHomLed(Long orderId) {
        return homLedMapper.getHomLed(orderId);
    }

    @Override
    public List<HomeLedVo> getHomStaffList(Long orderId) {
        return homLedMapper.getHomStaffList(orderId);
    }

    @Override
    public List<OrderRecord> deatilListThree(Long orderId) {
        return orderRecordMapper.deatilListThree(orderId, OrderConstants.HOME_ORDER);
    }

    @Override
    public List<HomeLedVo> orderImg(Long orderId) {
        return homLedMapper.orderImg(orderId);
    }
}
