package com.wr.estate.service.impl.work.disasterOrder;

import com.wr.estate.mapper.work.disasterOrder.OrderRecordBusMapper;
import com.wr.estate.service.work.disasterOrder.OrderRecordService;
import com.wr.remote.work.vo.OrderRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xuShu
 * @Date: 2022-10-31 16:31:31
 * @Desc:
 */
@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Autowired
    private OrderRecordBusMapper orderRecordBusMapper;


    /**
     *  工单执行情况表添加数据
     * @param orderRecordVo
     * @return
     */
    @Override
    public int insertRecord(OrderRecordVo orderRecordVo) {
        return orderRecordBusMapper.insertBusRecord(orderRecordVo);
    }
}
