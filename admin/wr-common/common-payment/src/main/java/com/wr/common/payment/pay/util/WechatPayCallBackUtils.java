package com.wr.common.payment.pay.util;

import java.security.GeneralSecurityException;

import com.alibaba.fastjson2.JSONObject;

import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wr.common.payment.pay.domain.WeChatPayResultIn;


public class WechatPayCallBackUtils {

    public static JSONObject wechatPayCallBack(String apiV3Key,WeChatPayResultIn weChatPayResultIn) {
        //info("微信结果通知字符串:{}", JSON.toJSONString(weChatPayResultIn));
        String nonce = weChatPayResultIn.getResource().getNonce();
        String ciphertext = weChatPayResultIn.getResource().getCiphertext();
        String associated_data = weChatPayResultIn.getResource().getAssociated_data();

        AesUtil aesUtil = new AesUtil(apiV3Key.getBytes());
        try {
            //验签
            String s = aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), ciphertext);
            return JSONObject.parseObject(s);
//            //订单号
//            String orderId = jsonObject.getString("out_trade_no");
//            //微信支付订单号
//            String transactionId = jsonObject.getString("transaction_id");
//
//            String tradeState = jsonObject.getString("trade_state");
//
//            if (StringUtils.equals(tradeState, "USERPAYING")) {
//                //还在支付中，不做处理
//                System.out.println("还在支付中，不做处理");
//                return;
//            }

            //TODO 结果直接落地
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}
