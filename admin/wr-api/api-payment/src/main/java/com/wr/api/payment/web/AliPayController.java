package com.wr.api.payment.web;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.payment.entity.dto.PayDTO;
import com.wr.api.payment.service.PayLogService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.pay.remote.RemotePayService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 09:40:37
 * @Desc: 支付宝支付调用
 */
@RestController
@RequestMapping("/ali_pay")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AliPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);

    private final RemotePayService remotePayService;
    private final RedisService redisService;
    private final PayLogService payLogService;

    @PostMapping("/do_pay")
    public AjaxResult doPay(@RequestBody PayDTO payDTO){
        log.info("支付宝支付调用===>{}",payDTO.toString());

        JSONObject aliAttach = new JSONObject();
        aliAttach.put(PayConstants.PAY_WAY, PayConstants.H5);
        aliAttach.put(PayConstants.COMMUNITY_ID, payDTO.getCommunityId());
        aliAttach.put(PayConstants.ALIPAY_ID, payDTO.getAlipayId());
        aliAttach.put(PayConstants.USER_TYPE, payDTO.getUserType());
        aliAttach.put(PayConstants.USER_NAME, payDTO.getUserName());
        aliAttach.put(PayConstants.USER_PHONE, payDTO.getUserPhone());
        aliAttach.put("plantNo", payDTO.getPlantNo());
        if(StringUtils.isNotEmpty(payDTO.getRoadwayCode())){
            aliAttach.put(PayConstants.ROADWAY_CODE,payDTO.getRoadwayCode());
        }
        aliAttach.put("inLogId",payDTO.getInLogId());
        aliAttach.put("inTableName",payDTO.getInTableName());
        aliAttach.put("outLogId",payDTO.getOutLogId());
        aliAttach.put("outTableName",payDTO.getOutTableName());
        redisService.setCacheObject("aliAttach",aliAttach);

        AliPay aliPay = packageAliPay(payDTO);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/payment/ali_pay/notify";
        aliPay.setNotifyUrl(notifyUrl);
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/payment/ali_pay/ali_success";
        aliPay.setReturnUrl(returnUrl);
        String quitUrl = CommunityConstants.GATEWAY_URL + "/api/payment/ali_pay/ali_cancel";
        aliPay.setQuitUrl(quitUrl);
        log.info(aliPay.toString());
        return remotePayService.aliPayment(aliPay);

    }

    private static AliPay packageAliPay(PayDTO payDTO){
        AliPay aliPay = new AliPay();
        aliPay.setAlipayId(payDTO.getAlipayId());
        aliPay.setMethod(PayConstants.WAP);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        aliPay.setOutTradeNo(outTradeNo);
        aliPay.setTotalAmount(payDTO.getTotalAmount());
        aliPay.setSubject(PayConstants.SUBJECT_PARK);
        aliPay.setTimeExpire(PaymentUtil.aliTimeExpire(15));
        return aliPay;
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        log.info("********** 支付宝支付回调执行 *************");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = PaymentUtil.getPostParams(request);
        log.info("params：{}",params);
        JSONObject aliAttach = redisService.getCacheObject("aliAttach");
        boolean result = payLogService.insertAliPayLog(params, aliAttach);
        if(result){
            redisService.deleteObject("aliAttach");
            return PayConstants.ALI_SUCCESS_RETURN ;
        }
        return PayConstants.ALI_FAILED_RETURN;
    }

    @GetMapping("/ali_success")
    public void aliSuccess(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入成功页面 *************");
        response.sendRedirect(CommunityConstants.PARK_FEE_SERVER + "/ali_success");
    }

    @GetMapping("/ali_cancel")
    public void aliCancel(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入取消页面 *************");
        response.sendRedirect(CommunityConstants.PARK_FEE_SERVER + "/ali_cancel");
    }

}
