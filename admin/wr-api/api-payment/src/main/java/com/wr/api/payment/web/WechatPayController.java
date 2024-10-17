package com.wr.api.payment.web;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.payment.entity.dto.PayDTO;
import com.wr.api.payment.entity.dto.QueryDTO;
import com.wr.api.payment.service.CommunityService;
import com.wr.api.payment.service.PayLogService;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.domain.dto.WechatDTO;
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
import java.nio.charset.StandardCharsets;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 15:03:07
 * @Desc: 微信支付调用
 */
@RestController
@RequestMapping("/wechat_pay")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WechatPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WechatPayController.class);

    private final CommunityService communityService;
    private final RedisService redisService;
    private final RemotePayService remotePayService;
    private final PayLogService payLogService;

    @GetMapping("/orderInfo")
    public AjaxResult wechatOrderInfo(QueryDTO queryDto) {
        log.info("订单查询===>{}",queryDto.toString());

        WechatDTO wechatDto = new WechatDTO();
        wechatDto.setOutTradeNo(queryDto.getOutTradeNo());
        wechatDto.setMethod(PayConstants.QUERY);
        return remotePayService.wechatAfter(wechatDto);
    }

    @PostMapping("/do_pay")
    public AjaxResult transactions(@RequestBody PayDTO payDTO, HttpServletRequest request){
        JSONObject wxAttach = new JSONObject();
        wxAttach.put(PayConstants.PAY_WAY, PayConstants.H5);
        wxAttach.put(PayConstants.COMMUNITY_ID, payDTO.getCommunityId());
        wxAttach.put(PayConstants.WECHAT_ID, payDTO.getWechatId());
        wxAttach.put(PayConstants.USER_TYPE, payDTO.getUserType());
        wxAttach.put(PayConstants.USER_NAME, payDTO.getUserName());
        wxAttach.put(PayConstants.USER_PHONE, payDTO.getUserPhone());
        wxAttach.put("plantNo", payDTO.getPlantNo());
        if(StringUtils.isNotEmpty(payDTO.getRoadwayCode())){
            wxAttach.put(PayConstants.ROADWAY_CODE,payDTO.getRoadwayCode());
        }
        wxAttach.put("inLogId",payDTO.getInLogId());
        wxAttach.put("inTableName",payDTO.getInTableName());
        wxAttach.put("outLogId",payDTO.getOutLogId());
        wxAttach.put("outTableName",payDTO.getOutTableName());
        redisService.setCacheObject("wxAttach",wxAttach);

        WechatOpen wechatOpen = packageWechatPay(payDTO);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/payment/wechat_pay/notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/payment/wechat_pay/wechat_done";
        wechatOpen.setReturnUrl(returnUrl);
        wechatOpen.setClientIp(IpUtils.getIpAddr(request));

        return remotePayService.wechatPayment(wechatOpen, SecurityConstants.INNER);
    }

    private static WechatOpen packageWechatPay(PayDTO payDTO){
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setWechatId(payDTO.getWechatId());
        wechatOpen.setMethod(PayConstants.H5);
        wechatOpen.setDescription(PayConstants.DESC_PARK);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(payDTO.getTotalAmount());
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        return wechatOpen;
    }

    @PostMapping("/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("********** 微信支付回调执行 *************");
        response.setContentType("application/json");
        JSONObject wxAttach = redisService.getCacheObject("wxAttach");
        boolean flag = payLogService.insertWechatPayLog(request, wxAttach);
        String returnMsg = PayConstants.WECHAT_FAILED_RETURN;
        response.setStatus(500);
        if(flag){
            redisService.deleteObject("wxAttach");
            returnMsg = PayConstants.WECHAT_SUCCESS_RETURN;
            response.setStatus(200);
        }
        response.getOutputStream().write(returnMsg.getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }

    @GetMapping("/wechat_done")
    public void wechatDone(HttpServletResponse response) throws IOException {
        log.info("********** 微信进入支付调起后页面 *************");
        response.sendRedirect(CommunityConstants.PARK_FEE_SERVER + "/wechat_done");
    }

}
