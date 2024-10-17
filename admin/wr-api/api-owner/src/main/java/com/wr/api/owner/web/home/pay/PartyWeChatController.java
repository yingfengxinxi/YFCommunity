package com.wr.api.owner.web.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.PartyDTO;
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
 * 党费微信支付调用
 * @Author: lvzy
 * @Date: 2022-11-17 17:03:08
 * @Desc:微信支付调用
 */
@RestController
@RequestMapping("/v2/weChat_part")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PartyWeChatController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PartyWeChatController.class);

    private final CommunityService communityService;
    private final RedisService redisService;
    private final RemotePayService remotePayService;
    private final LiveService liveService;
    // 党费记录
    private final ApiPartyService apiPartyService;

    /**
     * 党费
     * @param propertyDTO 缴费参数
     * @param request
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/party_pay")
    public AjaxResult transactions(@RequestBody PartyDTO propertyDTO, HttpServletRequest request) {
        CommunityWechat communityWechat = communityService.selectWechatById(propertyDTO.getWechatId());
        if (null == communityWechat) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject wxAttach = new JSONObject();
        wxAttach.put(PayConstants.PAY_WAY, propertyDTO.getPayWay());
        wxAttach.put(PayConstants.WECHAT_ID, propertyDTO.getWechatId());
        wxAttach.put(PayConstants.COMMUNITY_ID, propertyDTO.getCommunityId());

        wxAttach.put("partyId", propertyDTO.getPartyId());
        wxAttach.put("memberId", propertyDTO.getMemberId());
        wxAttach.put("payMonth", propertyDTO.getPayMonth());
        wxAttach.put("memberName", propertyDTO.getMemberName());
        wxAttach.put("memberPhone", propertyDTO.getMemberPhone());
        redisService.setCacheObject("wxAttach", wxAttach);

        String description = "智慧小区-党费";
        WechatOpen wechatOpen = packageWechatPay(propertyDTO, description);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        // 默认APP支付
        String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/wechat_pay/property_app_done";
        if (PayConstants.H5.equals(propertyDTO.getPayWay())) {
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

//        boolean flag = liveService.insertPropertyLog(request, wxAttach);
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
