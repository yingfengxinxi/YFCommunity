package com.wr.api.payment.service;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 13:51:45
 * @Desc: 扫码支付业务处理
 */
public interface PayLogService {

    /**
     * 添加缴费记录
     * @param params 支付宝POST过来反馈信息
     * @param attach 附加参数
     * @return
     */
    Boolean insertAliPayLog(Map<String,String> params, JSONObject attach);

    /**
     * 添加缴费记录
     * @param request  微信POST请求
     * @param attach 附加参数
     * @return
     */
    Boolean insertWechatPayLog(HttpServletRequest request, JSONObject attach);
}
