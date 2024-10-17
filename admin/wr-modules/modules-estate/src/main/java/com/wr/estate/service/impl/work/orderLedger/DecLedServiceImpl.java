package com.wr.estate.service.impl.work.orderLedger;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.mapper.work.orderLedger.DecLedMapper;
import com.wr.estate.service.work.orderLedger.DecLedService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.fitment.DecorateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-07 11:43:49
 * @Desc:
 */
@Service
public class DecLedServiceImpl implements DecLedService {

    @Autowired
    private DecLedMapper decLedMapper;


    @Override
    public List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder) {
        return decLedMapper.getDecorateOrderList(decorateOrder);
    }

    @Override
    public AjaxResult getDecorateOrderDetail(Long orderId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修评定工单详情
        DecorateOrder decorateOrder = decLedMapper.getDecorateOrderDetail(orderId);

        //查询工单协助人信息
        List<Staff> staffList = decLedMapper.getStaffList(orderId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorateOrder);
        ajaxResult.put("staffList", staffList);

        return ajaxResult;
    }
}
