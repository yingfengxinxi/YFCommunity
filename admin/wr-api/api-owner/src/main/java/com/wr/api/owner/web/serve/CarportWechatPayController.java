package com.wr.api.owner.web.serve;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.myinfo.PayDTO;
import com.wr.api.owner.service.CommunityService;
import com.wr.api.owner.service.home.pay.LiveService;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.pay.remote.RemotePayService;
import com.wr.remote.domain.CommunityWechat;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 15:03:07
 * @Desc: 微信支付调用
 */
@RestController
@RequestMapping("/wechat_pay")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CarportWechatPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CarportWechatPayController.class);

    private final CommunityService communityService;
    private final RedisService redisService;
    private final RemotePayService remotePayService;
    private final LiveService liveService;

    /**
     * 车位费
     * @param PayDTO 缴费参数
     * @param request
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @PostMapping("/carport_pay")
    public AjaxResult transactions(@RequestBody PayDTO PayDTO, HttpServletRequest request){
        CommunityWechat communityWechat = communityService.selectWechatById(PayDTO.getWechatId());
        if(null == communityWechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject wxAttach = new JSONObject();
        wxAttach.put(PayConstants.PAY_WAY, PayDTO.getPayWay());
        wxAttach.put(PayConstants.WECHAT_ID, PayDTO.getWechatId());
        wxAttach.put(PayConstants.COMMUNITY_ID, PayDTO.getCommunityId());
        wxAttach.put("userName",PayDTO.getUserName());
        wxAttach.put("carportId",PayDTO.getCarportId());
        wxAttach.put("userPhone",PayDTO.getUserPhone());
        wxAttach.put("cardNo",PayDTO.getCardNo());
        wxAttach.put("plantNo",PayDTO.getPlantNo());
        wxAttach.put("rentalStart",PayDTO.getRentalStart());
        wxAttach.put("rentalEnd",PayDTO.getRentalEnd());
        wxAttach.put("accountId",PayDTO.getAccountId());
        wxAttach.put("vehicleId",PayDTO.getVehicleId());
        redisService.setCacheObject("wxAttach",wxAttach);

        String description = "智慧小区-车位费";
        WechatOpen wechatOpen = packageWechatPay(PayDTO.getPayWay(),PayDTO.getTotalAmount(), description);
        wechatOpen.setAppId(communityWechat.getAppId());
        wechatOpen.setMchId(communityWechat.getMchId());
        wechatOpen.setSerialNumber(communityWechat.getSerialNumber());
        wechatOpen.setApiKey(communityWechat.getApiKey());
        wechatOpen.setPrivateKeyUrl(communityWechat.getPrivateUrl());
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/wechat_pay/property_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/wechat_pay/property_done";
        if(PayConstants.APP.equals(PayDTO.getPayWay())){
            returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/wechat_pay/property_APP_done";
        }
        wechatOpen.setReturnUrl(returnUrl);
        wechatOpen.setClientIp(IpUtils.getIpAddr(request));

        return remotePayService.wechatPayment(wechatOpen, SecurityConstants.INNER);
    }

    @PostMapping("/carport_notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("********** 微信支付--物业缴费--回调执行 *************");
        response.setContentType("application/json");
        JSONObject wxAttach = redisService.getCacheObject("wxAttach");
        boolean flag = liveService.insertPropertyLog(request, wxAttach);
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

    @GetMapping("/carport_done")
    public void propertyDone(HttpServletResponse response) throws IOException {
        log.info("********** 微信进入支付调起后页面 *************");
        response.sendRedirect(CommunityConstants.OWNER_FEE_SERVER + "/wechat_done");
    }

    private static WechatOpen packageWechatPay(String payWay, BigDecimal totalAmount, String description){
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setMethod(payWay);
        wechatOpen.setDescription(description);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(totalAmount);
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        return wechatOpen;
    }

}
