package com.wr.api.owner.service.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wr.api.owner.entity.serve.ApiMember;
import com.wr.api.owner.entity.serve.ApiPartyPay;
import com.wr.api.owner.mapper.CommunitysMapper;
import com.wr.api.owner.mapper.serve.MemberMapper;
import com.wr.api.owner.mapper.serve.PartyPayMapper;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 党费微信支付调用
 * @Author: lvzy
 * @Date: 2022-11-17 19:34:59
 * @Desc:党费微信支付调用
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiPartyServiceImpl implements ApiPartyService {

    private static final Logger log = LoggerFactory.getLogger(ApiPartyServiceImpl.class);

    private final CommunitysMapper communityMapper;

    // 党员管理
    private final MemberMapper memberMapper;

    // 党费缴费记录
    private final PartyPayMapper partyPayMapper;

    @Override
    @Transactional(readOnly = false)
    public Boolean insertPropertyLog(HttpServletRequest request, JSONObject attach) {
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>支付方式:{}", payWay);
        //微信返回的请求体
        String body = HttpClientUtil.getRequestBody(request);
        log.info("返回的请求体:{}", body);
        if (StringUtils.isEmpty(body)) {
            log.error("微信支付回调请求体为null");
            return false;
        }
        //redis中的附加数据
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        Long partyId = attach.getLong("partyId");
        Long memberId = attach.getLong("memberId");
        String payMonth = attach.getString("payMonth");
        String memberName = attach.getString("memberName");
        String memberPhone = attach.getString("memberPhone");

        Long wechatId = attach.getLong(PayConstants.WECHAT_ID);
        log.info("===>小区id:{}，党组织id：{}，党员id：{}，缴费月份：{}, 党员姓名：{}，党员手机号：{},微信账号id：{}",
                communityId, partyId, memberId, payMonth, memberName, memberPhone, wechatId);
        //通过证书序列号查询
        CommunityWechat wechat = communityMapper.selectWechatById(wechatId);
        if (!SignUtil.verifiedSign(request, body, wechat.getMchId(), wechat.getSerialNumber(), wechat.getPrivateUrl(), wechat.getApiKey())) {
            log.error("验签失败====>");
            log.error("mchId:{},serialNumber:{},ApiV3Key:{}", wechat.getMchId(), wechat.getSerialNumber(), wechat.getApiKey());
            return false;
        }

        JSONObject bodyJo = JsonUtil.parseObject(body);
        if (null == bodyJo) {
            log.error("微信支付回调请求体为空");
            return false;
        }
        String resourceType = bodyJo.getString("resource_type");
        String eventType = bodyJo.getString("event_type");
        if (!PayConstants.RESOURCE_TYPE.equals(resourceType) || !PayConstants.EVENT_TYPE.equals(eventType)) {
            log.error("通知类型：event_type={}，通知数据类型：resource_type：{}", eventType, resourceType);
            return false;
        }
        JSONObject encrypt = bodyJo.getJSONObject("resource");
        JSONObject resource = WechatPayUtil.decodeWechatData(encrypt, wechat.getApiKey());
        if (null == resource) {
            log.warn("微信回调成功，数据未返回，源数据encrypt：{}", encrypt);
            return false;
        }
        String tradeState = resource.getString("trade_state");
        if (!"SUCCESS".equals(tradeState)) {
            log.warn("微信回调成功，交易状态={}，{}", tradeState, resource.getString("trade_state_desc"));
            return false;
        }
        //订单号
        String outTradeNo = resource.getString("out_trade_no");
        //微信支付订单号
        String transactionId = resource.getString("transaction_id");
        //金额
        JSONObject amount = resource.getJSONObject("amount");
        int payerTotal = amount.getInteger("payer_total");
        BigDecimal payAmount = new BigDecimal(payerTotal).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        // 封装数据
        ApiPartyPay apiPartyPay = packagePartyPay(communityId, partyId, memberId, payMonth, memberName, memberPhone, payAmount);
        apiPartyPay.setPayWay("2");
        // 记录物业费缴费记录
        int result = partyPayMapper.insertSelective(apiPartyPay);
        return result > 0;
    }


    @Override
    @Transactional(readOnly = false)
    public Boolean insertAliPayPropertyLog(Map<String, String> params, JSONObject attach) {
        String appId = params.get("app_id");
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>appId:{},支付方式:{}", appId, payWay);
        log.info("passback_params==>{}", attach);
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        Long alipayId = attach.getLong(PayConstants.ALIPAY_ID);
        Long partyId = attach.getLong("partyId");
        Long memberId = attach.getLong("memberId");
        String payMonth = attach.getString("payMonth");
        String memberName = attach.getString("memberName");
        String memberPhone = attach.getString("memberPhone");
        log.info("===>小区id:{}，党组织id：{}，党员id：{}，缴费月份：{}, 党员姓名：{}，党员手机号：{},支付宝账号id：{}",
                communityId, partyId, memberId, payMonth, memberName, memberPhone, alipayId);
        // 根据 communityId 到数据库中查询 publicKey
        CommunityAli communityAli = communityMapper.selectAliById(alipayId);
        if (null == communityAli) {
            log.error("支付宝回调失败，未查询到相关账号");
            return false;
        }
        if (StringUtils.isEmpty(communityAli.getPublicKey())) {
            log.error("支付宝回调失败，publicKey为空");
            return false;
        }

        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, communityAli.getPublicKey(), PayConstants.UTF_8, AliConstants.SIGN_TYPE);
            if (!flag) {
                log.error("支付宝回调签名认证失败");
                return false;
            }
            String tradeStatus = params.get("trade_status");
            log.info("支付宝回调签名认证成功，tradeStatus={}", tradeStatus);
            if (!AliConstants.TRADE_SUCCESS.equals(tradeStatus)) {
                return false;
            }
            log.info("*******交易成功******");
            String outTradeNo = params.get("out_trade_no");
            String amount = params.get("total_amount");
            log.info("outTradeNo：{}，totalAmount：{}", outTradeNo, amount);
            // 设置支付数据
//            PropertyLog propertyLog = packagePropertyLog(communityId, buildingId, unitId, roomId, new BigDecimal(amount));
            // 封装数据
            ApiPartyPay apiPartyPay = packagePartyPay(communityId, partyId, memberId, payMonth, memberName, memberPhone, new BigDecimal(amount));
            apiPartyPay.setPayWay("1");
            // 记录物业费缴费记录
            int result = partyPayMapper.insertSelective(apiPartyPay);

            return result > 0;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝回调失败，code:{},msg:{}", e.getErrCode(), e.getErrMsg());
            return false;
        }
    }

    // 党费对象整理
    private ApiPartyPay packagePartyPay(Long communityId, Long partyId, Long memberId, String payMonth, String memberName, String memberPhone, BigDecimal payAmount) {
        ApiPartyPay apiPartyPay = new ApiPartyPay();
        apiPartyPay.setCommunityId(communityId);
        apiPartyPay.setPartyId(partyId);
        apiPartyPay.setMemberId(memberId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM");
        try {
            Date parse = simpleDateFormat1.parse(payMonth);
            long nowDate = Long.parseLong(simpleDateFormat.format(new Date()));
            long parseLong = Long.parseLong(simpleDateFormat.format(parse));
            if (nowDate == parseLong) {
                apiPartyPay.setPayType("0");
            } else if (parseLong > nowDate) { // 预缴
                apiPartyPay.setPayType("1");
            } else { // 补缴
                apiPartyPay.setPayType("2");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        apiPartyPay.setPayMonth(payMonth);
        apiPartyPay.setMemberName(memberName);
        apiPartyPay.setMemberPhone(memberPhone);
        ApiMember apiMember = memberMapper.selectByPrimaryKey(memberId);
        apiPartyPay.setPartyFee(apiMember.getPartyFee());
        return apiPartyPay;
    }
}
