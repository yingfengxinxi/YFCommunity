package com.wr.api.owner.web.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.PartyDTO;
import com.wr.api.owner.entity.dto.PropertyDTO;
import com.wr.api.owner.service.CommunityService;
import com.wr.api.owner.service.home.pay.ApiPartyService;
import com.wr.api.owner.service.home.pay.LiveService;
import com.wr.common.core.business.annotation.AuthRequired;
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
import java.nio.charset.StandardCharsets;

/**
 * @Author: SJiang
 * @Date: 2023-05-04 15:03:07
 * @Desc: 微信支付调用
 */
@RestController
@RequestMapping("/v2/wechat_pay")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WechatPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WechatPayController.class);

    private final CommunityService communityService;
    private final RedisService redisService;
    private final RemotePayService remotePayService;
    private final LiveService liveService;
    private final ApiPartyService apiPartyService;

    /**
     * 物业费
     * @param propertyDTO 缴费参数
     * @param request
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/property_pay")
    public AjaxResult transactions(@RequestBody PropertyDTO propertyDTO, HttpServletRequest request){
        CommunityWechat communityWechat = communityService.selectWechatById(propertyDTO.getWechatId());
        if(null == communityWechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject wxAttach = new JSONObject();
        wxAttach.put(PayConstants.PAY_WAY, propertyDTO.getPayWay());
        wxAttach.put(PayConstants.WECHAT_ID, propertyDTO.getWechatId());
        wxAttach.put(PayConstants.COMMUNITY_ID, propertyDTO.getCommunityId());
        wxAttach.put("buildingId", propertyDTO.getBuildingId());
        wxAttach.put("unitId", propertyDTO.getUnitId());
        wxAttach.put("roomId", propertyDTO.getRoomId());
        wxAttach.put("payYear", propertyDTO.getPayYear());
        redisService.setCacheObject("wxAttach",wxAttach);

        String description = "智慧小区-物业费";
        WechatOpen wechatOpen = packageWechatPay(propertyDTO, description);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        // 默认APP支付
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_app_done";
        if(PayConstants.H5.equals(propertyDTO.getPayWay())){
            wechatOpen.setClientIp(IpUtils.getIpAddr(request));
            returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_done";
        }
        wechatOpen.setReturnUrl(returnUrl);
        return remotePayService.wechatPayment(wechatOpen, SecurityConstants.INNER);
    }

    @PostMapping("/property_notify")
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

    /**
     * 党费
     * @param partyDTO 缴费参数
     * @param request
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/party_pay")
    public AjaxResult transactions(@RequestBody PartyDTO partyDTO, HttpServletRequest request) {
        CommunityWechat communityWechat = communityService.selectWechatById(partyDTO.getWechatId());
        if (null == communityWechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject wxAttach = new JSONObject();
        wxAttach.put(PayConstants.PAY_WAY, partyDTO.getPayWay());
        wxAttach.put(PayConstants.WECHAT_ID, partyDTO.getWechatId());
        wxAttach.put(PayConstants.COMMUNITY_ID, partyDTO.getCommunityId());

        wxAttach.put("partyId", partyDTO.getPartyId());
        wxAttach.put("memberId", partyDTO.getMemberId());
        wxAttach.put("payMonth", partyDTO.getPayMonth());
        wxAttach.put("memberName", partyDTO.getMemberName());
        wxAttach.put("memberPhone", partyDTO.getMemberPhone());
        redisService.setCacheObject("wxAttach", wxAttach);

        String description = "智慧小区-党费";
        WechatOpen wechatOpen = packageWechatPay(partyDTO, description);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        // 默认APP支付
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_app_done";
        if (PayConstants.H5.equals(partyDTO.getPayWay())) {
            wechatOpen.setClientIp(IpUtils.getIpAddr(request));
            returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_done";
        }
        wechatOpen.setReturnUrl(returnUrl);
        return remotePayService.wechatPayment(wechatOpen, SecurityConstants.INNER);
    }

    @PostMapping("/party_notify")
    public void partyNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("********** 微信支付--党费--回调执行 *************");
        response.setContentType("application/json");
        JSONObject wxAttach = redisService.getCacheObject("wxAttach");
        Boolean flag = apiPartyService.insertPropertyLog(request, wxAttach);
        String returnMsg = PayConstants.WECHAT_FAILED_RETURN;
        response.setStatus(500);
        if (flag) {
            redisService.deleteObject("wxAttach");
            returnMsg = PayConstants.WECHAT_SUCCESS_RETURN;
            response.setStatus(200);
        }
        response.getOutputStream().write(returnMsg.getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }

    @GetMapping("/property_done")
    public void propertyDone(HttpServletResponse response) throws IOException {
        log.info("********** 微信进入支付调起后页面 *************");
        response.sendRedirect(CommunityConstants.OWNER_FEE_SERVER + "/wechat_done");
    }

    private static WechatOpen packageWechatPay(PropertyDTO propertyDTO, String description){
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setWechatId(propertyDTO.getWechatId());
        wechatOpen.setMethod(propertyDTO.getPayWay());
        wechatOpen.setDescription(description);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(propertyDTO.getTotalAmount());
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        return wechatOpen;
    }

    private static WechatOpen packageWechatPay(PartyDTO partyDTO, String description) {
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setWechatId(partyDTO.getWechatId());
        wechatOpen.setMethod(partyDTO.getPayWay());
        wechatOpen.setDescription(description);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(partyDTO.getPartyFee());
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        return wechatOpen;
    }

}
