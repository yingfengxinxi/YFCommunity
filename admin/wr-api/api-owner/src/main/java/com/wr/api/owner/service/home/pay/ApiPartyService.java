package com.wr.api.owner.service.home.pay;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 党费微信支付调用
 * @Author: lvzy
 * @Date: 2022-11-17 19:34:25
 * @Desc:党费微信支付调用
 */
public interface ApiPartyService {

    /**
     * 微信支付--添加党费缴费记录
     * @param request 请求数据
     * @param wxAttach 微信冗余参数
     * @return boolean
     */
    Boolean insertPropertyLog(HttpServletRequest request, JSONObject wxAttach);

    /**
     * 支付宝支付
     * @param params
     * @param aliAttach
     * @return
     */
    Boolean insertAliPayPropertyLog(Map<String, String> params, JSONObject aliAttach);
}
