package com.wr.api.estate.web.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.entity.application.serve.VenueAppoint;
import com.wr.api.estate.entity.dto.PayDTO;
import com.wr.api.estate.entity.vo.application.serve.VenueAppointApiVo;
import com.wr.api.estate.service.PayService;
import com.wr.api.estate.service.application.serve.VenueService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.pay.remote.RemotePayService;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:35:12
 * @Desc:
 */
@Slf4j
@RestController
@RequestMapping("v2/venue")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VenueController extends BaseController {

    public static final String GATEWAY_URL = "http://localhost:9100";

    public static final String SERVER_URL = "http://localhost:8888";

    private final VenueService venueService;

    private final RemotePayService remotePayService;

    private final PayService payService;
    private final RedisService redisService;

    @GetMapping("search")
    @AuthRequired
    public TableDataInfo search(Long communityId, String state) {
        startPage();
        return getDataTable(venueService.searchByCommunityId(communityId, state));
    }

    /**
     * 查询场馆工单
     *
     * @param communityId
     * @return
     */
    @GetMapping("/searchVenueAppoint/{communityId}")
    @AuthRequired
    public TableDataInfo searchVenueAppoint(@PathVariable Long communityId) {
        startPage();
        List<VenueAppointApiVo> venueAppointList = venueService.searchVenueAppoint(communityId);
        return getDataTable(venueAppointList);
    }

    /**
     * 查看场馆工单详情
     *
     * @param appointId
     * @return
     */
    @GetMapping("/detailVenueAppoint/{appointId}")
    @AuthRequired
    public AjaxResult detailVenueAppoint(@PathVariable Long appointId) {
        VenueAppointApiVo venueAppointApiVo = venueService.detailVenueAppoint(appointId);
        return AjaxResult.success(venueAppointApiVo);
    }

    /**
     * 场馆工单终止
     *
     * @param appointId
     * @return
     */
    @PostMapping("/terVenueAppoint/{appointId}")
    @AuthRequired
    public AjaxResult terVenueAppoint(@PathVariable Long appointId) {
        int num = venueService.terVenueAppoint(appointId);
        return AjaxResult.success(num);
    }

    @GetMapping("detail")
    @AuthRequired
    public AjaxResult detail(Long venueId) {
        return AjaxResult.success(venueService.detailById(venueId));
    }

    @GetMapping("searchAppoint")
    @AuthRequired
    public AjaxResult searchAppoint(@RequestParam(name = "venueId") Long venueId, @RequestParam(required = false, defaultValue = "", name = "time") String time) {
        return AjaxResult.success(venueService.searchAppoint(venueId, time));
    }

    @GetMapping("search/act")
    @AuthRequired
    public AjaxResult searchAct(Long communityId) {

        return AjaxResult.success(venueService.searchActByCommunityId(communityId));
    }

    @GetMapping("search/rule")
    @AuthRequired
    public AjaxResult searchRule(Long communityId) {

        return AjaxResult.success(venueService.searchRuleByCommunityId(communityId));
    }

    @ApiLog(title = "物业端-新增场馆预约", businessType = BusinessType.INSERT)
    @PostMapping("save")
    @AuthRequired
    public AjaxResult save(@RequestBody VenueAppoint venueAppoint) {

        return venueService.save(venueAppoint);
    }

    /**
     * 支付宝支付
     *
     * @param payDTO
     * @return
     */
    @PostMapping("ali/pay")
    public AjaxResult aliPayment(@RequestBody PayDTO payDTO) {

        log.info("支付宝支付调用===>{}", payDTO.toString());
        CommunityAli communityAli = payService.selectAliById(payDTO.getAlipayId());
        if (null == communityAli) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        JSONObject aliAppoint = new JSONObject();
        aliAppoint.put("appointId", payDTO.getAppointId());
        aliAppoint.put(PayConstants.ALIPAY_ID, payDTO.getAlipayId());
        redisService.setCacheObject("aliAppoint", aliAppoint);

        AliPay aliPay = packageAliPay(payDTO);
        aliPay.setAppId(communityAli.getAppId());
        aliPay.setPublicKey(communityAli.getPublicKey());
        aliPay.setPrivateKey(communityAli.getPrivateKey());
        String notifyUrl = GATEWAY_URL + "/api/estate/v2/venue/notify";
        aliPay.setNotifyUrl(notifyUrl);
        String returnUrl = GATEWAY_URL + "/api/estate/v2/venue/ali_success";
        aliPay.setReturnUrl(returnUrl);
        String quitUrl = GATEWAY_URL + "/api/estate/v2/venue/ali_cancel";
        aliPay.setQuitUrl(quitUrl);
        return remotePayService.aliPayment(aliPay);
    }

    private static AliPay packageAliPay(PayDTO payDTO) {
        AliPay aliPay = new AliPay();
        aliPay.setMethod(PayConstants.WAP);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        aliPay.setOutTradeNo(outTradeNo);
        aliPay.setTotalAmount(payDTO.getTotalAmount());
        aliPay.setSubject(PayConstants.VENUE_APPOINT);
        aliPay.setTimeExpire(PaymentUtil.aliTimeExpire(15));
        return aliPay;
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        log.info("********** 支付宝支付回调执行 *************");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = PaymentUtil.getPostParams(request);
        log.info("params：{}", params);
        JSONObject aliAppoint = redisService.getCacheObject("aliAppoint");
        boolean result = venueService.updateAliPay(params, aliAppoint);
        if (result) {
            redisService.deleteObject("appoint");
            return PayConstants.ALI_SUCCESS_RETURN;
        }
        return PayConstants.ALI_FAILED_RETURN;
    }

    @GetMapping("/ali_success")
    public void aliSuccess(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入成功页面 *************");
        response.sendRedirect(SERVER_URL + "/ali_success");
    }

    @GetMapping("/ali_cancel")
    public void aliCancel(HttpServletResponse response) throws IOException {
        log.info("********** 支付宝进入取消页面 *************");
        response.sendRedirect(SERVER_URL + "/ali_cancel");
    }

    /**
     * 微信支付
     *
     * @param payDTO
     * @return
     */
    @PostMapping("wechat/pay")
    public AjaxResult wechatPayment(@RequestBody PayDTO payDTO, HttpServletRequest request) {

        log.info("微信调用===>{}", payDTO.toString());
        List<CommunityWechat> communityWechatList = payService.selectWechatById(null, payDTO.getWechatId());
        if (CollectionUtils.isEmpty(communityWechatList)) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        CommunityWechat communityWechat = communityWechatList.get(0);
        JSONObject wxAppoint = new JSONObject();
        wxAppoint.put("appointId", payDTO.getWechatId());
        wxAppoint.put(PayConstants.WECHAT_ID, payDTO.getWechatId());
        redisService.setCacheObject("wxAppoint", wxAppoint);

        WechatOpen wechatOpen = packageWechatPay(payDTO);
        wechatOpen.setAppId(communityWechat.getAppId());
        wechatOpen.setMchId(communityWechat.getMchId());
        wechatOpen.setSerialNumber(communityWechat.getSerialNumber());
        wechatOpen.setApiKey(communityWechat.getApiKey());
        wechatOpen.setPrivateKeyUrl(communityWechat.getPrivateUrl());
        String notifyUrl = GATEWAY_URL + "/api/estate/v2/venue/wechat_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        String returnUrl = GATEWAY_URL + "/api/estate/v2/venue/wechat_done";
        wechatOpen.setReturnUrl(returnUrl);
        wechatOpen.setReturnUrl(returnUrl);
        wechatOpen.setClientIp(IpUtils.getIpAddr(request));

        return remotePayService.wechatPayment(wechatOpen, SecurityConstants.INNER);
    }

    private static WechatOpen packageWechatPay(PayDTO payDTO) {
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setMethod(PayConstants.H5);
        wechatOpen.setDescription(PayConstants.VENUE_APPOINT);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(payDTO.getTotalAmount());
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        return wechatOpen;
    }

    @PostMapping("wechat_notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("********** 微信支付回调执行 *************");
        response.setContentType("application/json");
        JSONObject wxAttach = redisService.getCacheObject("wxAppoint");
        boolean flag = venueService.updateWechatPay(request, wxAttach);
        String returnMsg = PayConstants.WECHAT_FAILED_RETURN;
        response.setStatus(500);
        if (flag) {
            redisService.deleteObject("wxAppoint");
            returnMsg = PayConstants.WECHAT_SUCCESS_RETURN;
            response.setStatus(200);
        }
        response.getOutputStream().write(returnMsg.getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }

    @GetMapping("/wechat_done")
    public void wechatDone(HttpServletResponse response) throws IOException {
        log.info("********** 微信进入支付调起后页面 *************");
        response.sendRedirect(SERVER_URL + "/wechat_done");
    }
}
