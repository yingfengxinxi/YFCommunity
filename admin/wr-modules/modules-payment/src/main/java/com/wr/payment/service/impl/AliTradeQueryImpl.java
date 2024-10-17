package com.wr.payment.service.impl;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.dto.AliPayDTO;
import com.wr.common.payment.util.AliPayUtil;
import com.wr.payment.service.AliTradeAfterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 18:03:40
 * @Desc: 支付宝订单查询
 */
@Service
public class AliTradeQueryImpl implements AliTradeAfterService {

    @Override
    public Boolean support(String method) {

        return PayConstants.QUERY.equals(method);
    }

    @Override
    public AjaxResult execute(AliPayDTO aliPayDto) {
        String tradeStatus = AliPayUtil.tradeQuery(aliPayDto);
        if(StringUtils.isEmpty(tradeStatus)){
            return AjaxResult.error("订单查询失败");
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("tradeStatus",tradeStatus);
        return ajaxResult;
    }
}
