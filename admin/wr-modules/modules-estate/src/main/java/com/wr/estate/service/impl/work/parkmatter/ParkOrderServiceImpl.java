package com.wr.estate.service.impl.work.parkmatter;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.mapper.work.orderLedger.ParkLedgerBillMapper;
import com.wr.estate.mapper.work.parkmatter.ParkAuditMapper;
import com.wr.estate.mapper.work.parkmatter.ParkOrderMapper;
import com.wr.estate.service.work.parkmatter.ParkOrderService;
import com.wr.remote.estate.financial.ParkOrderDel;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-11-04 11:27:05
 * @Desc:
 */
@Service
public class ParkOrderServiceImpl implements ParkOrderService {

    @Resource
    private ParkOrderMapper parkOrderMapper;

    @Resource
    private ParkLedgerBillMapper parkLedgerBillMapper;

    @Resource
    private ParkAuditMapper parkAuditMapper;

    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Override
    public List<LinkedHashMap<String, Object>> query(Map<String, Object> params) {

        List<LinkedHashMap<String, Object>> list = parkOrderMapper.query(params);
        Map<String, Object> map = new HashMap<>(2);
        list.forEach(e -> {
            map.put("id", Long.valueOf(e.get("audit_id") + ""));
            map.put("type", "1");
            e.put("orderImg", parkLedgerBillMapper.queryOrderImg(map));
        });
        /**
         * //负责人员
         */
        if (StringUtils.isNotNull(params.get("accept_person"))) {
            String acceptPerson = String.valueOf(params.get("accept_person"));
            return list.stream().filter(e -> String.valueOf(e.get("staff_name")).indexOf(acceptPerson) != -1).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public int updateOrder(Map<String, Object> params) {
        return parkOrderMapper.updateOrder(params);
    }

    @Override
    public int remove(Map<String, Object> params) {
        parkAuditMapper.remove(Long.valueOf(params.get("audit_id").toString()));
        return parkOrderMapper.remove(Long.valueOf(params.get("order_id").toString()));
    }

    @Override
    public void deleteByOrderNo(String orderNo) {
        parkOrderMapper.deleteByOrderNo(orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Map<String, Object> params) {
        params.put("by", SecurityUtils.getUsername());
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType(OrderConstants.PARK_ORDER);
        orderRecord.setOrderId(Long.valueOf(params.get("orderId").toString()));
        orderRecord.setDataType("1");
        orderRecord.setOrderResult("分配方式：管理员分配");
        orderRecord.setUserName(SecurityUtils.getUsername());
        orderRecordMapper.insertSelective(orderRecord);
        return parkOrderMapper.update(params);
    }

    @Override
    public ParkOrderDel getByOrderNoInfo(String orderNo) {
        return parkOrderMapper.getByOrderNoInfo(orderNo);
    }

    @Override
    public void clearBusCarport(String carportId) {
        parkOrderMapper.clearBusCarport( carportId );
    }
}
