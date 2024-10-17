package com.wr.payment.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.domain.dto.AliPayDTO;
import com.wr.common.payment.util.AliPayUtil;
import com.wr.payment.domain.CommunityAli;
import com.wr.payment.service.AliPayService;
import com.wr.payment.service.AliTradeAfterService;
import com.wr.payment.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:04:37
 * @Desc: 支付宝支付调用
 */
@RestController
@RequestMapping("/ali")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AliPayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);

    private final CommunityService communityService;
    private final List<AliPayService> aliPayServices;
    private final List<AliTradeAfterService> aliTradeAfterServices;

    @PostMapping("/payment")
    public AjaxResult aliPay(@RequestBody AliPay aliPay) {
        CommunityAli communityAli = communityService.selectAliById(aliPay.getAlipayId());
        if(null == communityAli) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        aliPay.setAppId(communityAli.getAppId());
        aliPay.setPublicKey(communityAli.getPublicKey());
        aliPay.setPrivateKey(communityAli.getPrivateKey());
        log.info("********** 支付宝支付==> *************");
        AliPayService aliPayService = aliPayServices.stream()
                .filter(service -> service.support(aliPay.getMethod()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("支付模式："+aliPay.getMethod()+"不支持"));
        return aliPayService.execute(aliPay);
    }

    @PostMapping("/after")
    public AjaxResult aliAfter(@RequestBody AliPayDTO aliPayDto) {
        CommunityAli communityAli = communityService.selectAliById(aliPayDto.getAlipayId());
        if(null == communityAli) {
            log.error("未查询到账号信息");
            return AjaxResult.error("未查询到账号信息");
        }
        aliPayDto.setAppId(communityAli.getAppId());
        aliPayDto.setPublicKey(communityAli.getPublicKey());
        aliPayDto.setPrivateKey(communityAli.getPrivateKey());
        log.info("********** 支付宝支付后其他操作==> *************");
        log.info("请求参数:{}",aliPayDto.toString());
        AliTradeAfterService tradeAfterService = aliTradeAfterServices.stream()
                .filter(service -> service.support(aliPayDto.getMethod()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("查询模式："+aliPayDto.getMethod()+"不支持"));
        return tradeAfterService.execute(aliPayDto);
    }

    /**
     * 示例代码
     * @param request
     * @return {@link String}
     * @ver v1.0.0
     */
    @PostMapping("/ali_notify")
    public String aliNotify(HttpServletRequest request) {
        log.info("********** 支付宝支付回调执行 *************");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = AliPayUtil.getPostParams(request);
        log.info("params：{}",params);
        String appId = params.get("app_id");
        log.info("异步通知==>appId:{}",appId);
        String pbp = params.get("passback_params");
        JSONObject passBackParams= JSON.parseObject(pbp);
        String payWay = passBackParams.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>支付方式:{}",payWay);
        // TODO: 2021/11/1 示例：根据 appId 到数据库中查询
        AliPay aliPay = new AliPay();
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, aliPay.getPublicKey(),
                    PayConstants.UTF_8, AliConstants.SIGN_TYPE);
            if(flag){
                log.info("支付宝回调签名认证成功");
                String tradeStatus= params.get("trade_status");
                if(AliConstants.TRADE_SUCCESS.equals(tradeStatus)){
                    log.info("*******交易成功******");
                    String outTradeNo= params.get("out_trade_no");
                    // TODO: 2021/11/1 向数据库中加入支付记录
                    return "success";
                }
            }
            return "failure";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.info("支付宝{}方式回调失败，code:{},msg:{}",payWay,e.getErrCode(),e.getErrMsg());
            return "failure";
        }
    }

}
