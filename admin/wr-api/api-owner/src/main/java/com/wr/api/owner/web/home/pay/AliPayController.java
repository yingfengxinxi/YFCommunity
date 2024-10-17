package com.wr.api.owner.web.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.PartyDTO;
import com.wr.api.owner.entity.dto.PropertyDTO;
import com.wr.api.owner.service.CommunityService;
import com.wr.api.owner.service.home.pay.ApiPartyService;
import com.wr.api.owner.service.home.pay.LiveService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.pay.remote.RemotePayService;
import com.wr.remote.domain.CommunityAli;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: SJiang
 * @Date: 2023-05-04 15:03:07
 * @Desc: 支付宝支付调用
 */
@RestController
@RequestMapping("/v2/ali_pay")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AliPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);

    private final CommunityService communityService;
    private final RedisService redisService;
    private final RemotePayService remotePayService;
    private final LiveService liveService;
    private final ApiPartyService apiPartyService;

    /**
     * 物业费
     * @param propertyDTO 缴费参数
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/property_pay")
    public AjaxResult transactions(@RequestBody PropertyDTO propertyDTO){
        CommunityAli communityAli = communityService.selectAliById(propertyDTO.getAlipayId());
        if(null == communityAli) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject aliAttach = new JSONObject();
        aliAttach.put(PayConstants.PAY_WAY, propertyDTO.getPayWay());
        aliAttach.put(PayConstants.ALIPAY_ID, propertyDTO.getAlipayId());
        aliAttach.put(PayConstants.COMMUNITY_ID, propertyDTO.getCommunityId());
        aliAttach.put("buildingId", propertyDTO.getBuildingId());
        aliAttach.put("unitId", propertyDTO.getUnitId());
        aliAttach.put("roomId", propertyDTO.getRoomId());
        aliAttach.put("payYear", propertyDTO.getPayYear());
        redisService.setCacheObject("aliAttach",aliAttach);

        String subject = "智慧小区-物业费";
        AliPay aliPay = packageAliPay(propertyDTO, subject);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/notify";
        aliPay.setNotifyUrl(notifyUrl);
        // app支付时，以下参数不需要
        if(PayConstants.WAP.equals(propertyDTO.getPayWay())){
            String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/property_done";
            aliPay.setReturnUrl(returnUrl);
            String quitUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/ali_cancel";
            aliPay.setQuitUrl(quitUrl);
        }

        return remotePayService.aliPayment(aliPay);
    }

    @PostMapping("/property_notify")
    public String notify(HttpServletRequest request) {
        log.info("********** 支付宝支付--物业缴费--回调执行 *************");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = PaymentUtil.getPostParams(request);
        log.info("params：{}",params);
        JSONObject aliAttach = redisService.getCacheObject("aliAttach");
        boolean result = liveService.insertAliPayLog(params, aliAttach);
        if(result){
            redisService.deleteObject("aliAttach");
            return PayConstants.ALI_SUCCESS_RETURN ;
        }
        return PayConstants.ALI_FAILED_RETURN;
    }

    /**
     * 党费
     * @param partyDTO 缴费参数
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/party_pay")
    public AjaxResult transactions(@RequestBody PartyDTO partyDTO) {
        CommunityAli communityAli = communityService.selectAliById(partyDTO.getAlipayId());
        if (null == communityAli) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject aliAttach = new JSONObject();
        aliAttach.put(PayConstants.PAY_WAY, partyDTO.getPayWay());
        aliAttach.put(PayConstants.ALIPAY_ID, partyDTO.getAlipayId());
        aliAttach.put(PayConstants.COMMUNITY_ID, partyDTO.getCommunityId());

        aliAttach.put("partyId", partyDTO.getPartyId());
        aliAttach.put("memberId", partyDTO.getMemberId());
        aliAttach.put("payMonth", partyDTO.getPayMonth());
        aliAttach.put("memberName", partyDTO.getMemberName());
        aliAttach.put("memberPhone", partyDTO.getMemberPhone());
        redisService.setCacheObject("wxAttach", aliAttach);

        String subject = "智慧小区-党费";
        AliPay aliPay = packageAliPay(partyDTO, subject);
        String notifyUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/notify";
        aliPay.setNotifyUrl(notifyUrl);
        // app支付时，以下参数不需要
        if (PayConstants.WAP.equals(partyDTO.getPayWay())) {
            String returnUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/property_done";
            aliPay.setReturnUrl(returnUrl);
            String quitUrl = CommunityConstants.GATEWAY_URL + "/api/owner/v2/ali_pay/ali_cancel";
            aliPay.setQuitUrl(quitUrl);
        }

        return remotePayService.aliPayment(aliPay);
    }

    @PostMapping("/party_notify")
    public String partyNotify(HttpServletRequest request) {
        log.info("********** 支付宝支付--党费--回调执行 *************");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = PaymentUtil.getPostParams(request);
        log.info("params：{}", params);
        JSONObject aliAttach = redisService.getCacheObject("aliAttach");
        Boolean result = apiPartyService.insertAliPayPropertyLog(params, aliAttach);
        if (result) {
            redisService.deleteObject("aliAttach");
            return PayConstants.ALI_SUCCESS_RETURN;
        }
        return PayConstants.ALI_FAILED_RETURN;
    }

    @GetMapping("/ali_success")
    public void aliSuccess(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入成功页面 *************");
        response.sendRedirect(CommunityConstants.OWNER_FEE_SERVER + "/ali_success");
    }

    @GetMapping("/ali_cancel")
    public void aliCancel(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入取消页面 *************");
        response.sendRedirect(CommunityConstants.OWNER_FEE_SERVER + "/ali_cancel");
    }

    private static AliPay packageAliPay(PropertyDTO propertyDTO, String subject){
        AliPay aliPay = new AliPay();
        aliPay.setAlipayId(propertyDTO.getAlipayId());
        aliPay.setMethod(propertyDTO.getPayWay());
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        aliPay.setOutTradeNo(outTradeNo);
        aliPay.setTotalAmount(propertyDTO.getTotalAmount());
        aliPay.setSubject(subject);
        aliPay.setTimeExpire(PaymentUtil.aliTimeExpire(15));
        return aliPay;
    }

    private static AliPay packageAliPay(PartyDTO partyDTO, String subject){
        AliPay aliPay = new AliPay();
        aliPay.setAlipayId(partyDTO.getAlipayId());
        aliPay.setMethod(partyDTO.getPayWay());
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        aliPay.setOutTradeNo(outTradeNo);
        aliPay.setTotalAmount(partyDTO.getPartyFee());
        aliPay.setSubject(subject);
        aliPay.setTimeExpire(PaymentUtil.aliTimeExpire(15));
        return aliPay;
    }

}
