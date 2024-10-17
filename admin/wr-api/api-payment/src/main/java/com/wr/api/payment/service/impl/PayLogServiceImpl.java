package com.wr.api.payment.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wr.api.payment.mapper.CommunityMapper;
import com.wr.api.payment.mapper.PayLogMapper;
import com.wr.api.payment.mapper.TableMapper;
import com.wr.api.payment.mapper.VehicleLogMapper;
import com.wr.api.payment.service.PayLogService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.remote.domain.CommunityAli;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.log.PayLog;
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
import java.util.Map;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 13:51:54
 * @Desc: 扫码支付业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class PayLogServiceImpl implements PayLogService {
    private static final Logger log = LoggerFactory.getLogger(PayLogServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final TableMapper tableMapper;
    private final VehicleLogMapper vehicleLogMapper;
    private final PayLogMapper payLogMapper;


    @Override
    @Transactional(readOnly = false)
    public Boolean insertAliPayLog(Map<String,String> params, JSONObject attach) {
        String appId = params.get("app_id");
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>appId:{},支付方式:{}",appId,payWay);
        log.info("passback_params==>{}",attach);
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        Long alipayId = attach.getLong(PayConstants.ALIPAY_ID);
        log.info("===>小区id:{},支付宝账号id：{}",communityId,alipayId);

        // 根据 communityId 到数据库中查询 publicKey
        CommunityAli communityAli = communityMapper.selectAliById(alipayId);
        if(null == communityAli){
            log.error("支付宝回调失败，未查询到相关账号");
            return false;
        }
        if(StringUtils.isEmpty(communityAli.getPublicKey())){
            log.error("支付宝回调失败，publicKey为空");
            return false;
        }

        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, communityAli.getPublicKey(),PayConstants.UTF_8, AliConstants.SIGN_TYPE);
            if(!flag){
                log.error("支付宝回调签名认证失败");
                return false;
            }
            String tradeStatus= params.get("trade_status");
            log.info("支付宝回调签名认证成功，tradeStatus={}",tradeStatus);
            if(!AliConstants.TRADE_SUCCESS.equals(tradeStatus)){
                return false;
            }
            log.info("*******交易成功******");
            String outTradeNo= params.get("out_trade_no");
            String amount= params.get("total_amount");
            log.info("outTradeNo：{}，totalAmount：{}",outTradeNo,amount);
            // 设置支付数据
            PayLog payLog = packagePayLog(communityId, new BigDecimal(amount), attach, outTradeNo, "0");
            // 查询海康配置
            CommunityHik communityHik = communityMapper.selectCommunityHikById(communityId);
            Optional.ofNullable(communityHik).ifPresent(hik -> {
                payLog.setCommunityName(hik.getCommunityName());
            });
            //向数据库中加入支付记录
            int result = payLogMapper.insertPayLog(payLog);
            if(result > 0){
                //修改进出时的支付状态
                Long inLogId = attach.getLong("inLogId");
                String inTableName = attach.getString("inTableName");
                vehicleLogMapper.updateInLog(inTableName, inLogId);
                Long outLogId = attach.getLong("outLogId");
                String outTableName = attach.getString("outTableName");
                vehicleLogMapper.updateOutLog(outTableName,outLogId);
                //开门
                String roadwayIndex = attach.getString(PayConstants.ROADWAY_CODE);
                if(StringUtils.isNotEmpty(roadwayIndex)){
                    boolean open = roadwayControl(communityHik, roadwayIndex);
                    String msg = open ? "开门成功" : "开门失败";
                    log.info(msg);
                }
                return true;
            }
            return false;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝回调失败，code:{},msg:{}",e.getErrCode(),e.getErrMsg());
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean insertWechatPayLog(HttpServletRequest request, JSONObject attach) {
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>支付方式:{}",payWay);
        //微信返回的请求体
        String body = HttpClientUtil.getRequestBody(request);
        log.info("返回的请求体:{}",body);
        if(StringUtils.isEmpty(body)) {
            log.error("微信支付回调请求体为null");
            return false;
        }
        //redis中的附加数据
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        Long wechatId = attach.getLong(PayConstants.WECHAT_ID);
        log.info("===>小区id:{},微信账号id：{}",communityId,wechatId);
        //通过证书序列号查询
        CommunityWechat wechat = communityMapper.selectWechatById(wechatId);
        if(!SignUtil.verifiedSign(request, body, wechat.getMchId(), wechat.getSerialNumber(), wechat.getPrivateUrl(), wechat.getApiKey())){
            log.error("验签失败====>");
            log.error("mchId:{},serialNumber:{},ApiV3Key:{}",wechat.getMchId(), wechat.getSerialNumber(),wechat.getApiKey());
            return false;
        }

        JSONObject bodyJo = JsonUtil.parseObject(body);
        if(null == bodyJo){
            log.error("微信支付回调请求体为空");
            return false;
        }
        String resourceType = bodyJo.getString("resource_type");
        String eventType = bodyJo.getString("event_type");
        if(!PayConstants.RESOURCE_TYPE.equals(resourceType) || !PayConstants.EVENT_TYPE.equals(eventType)){
            log.error("通知类型：event_type={}，通知数据类型：resource_type：{}",eventType,resourceType);
            return false;
        }
        JSONObject encrypt = bodyJo.getJSONObject("resource");
        JSONObject resource = WechatPayUtil.decodeWechatData(encrypt,wechat.getApiKey());
        if(null == resource){
            log.warn("微信回调成功，数据未返回，源数据encrypt：{}",encrypt);
            return false;
        }
        String tradeState = resource.getString("trade_state");
        if(!"SUCCESS".equals(tradeState)) {
            log.warn("微信回调成功，交易状态={}，{}",tradeState,resource.getString("trade_state_desc"));
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
        // 设置支付数据
        PayLog payLog = packagePayLog(communityId, payAmount, attach, outTradeNo, "1");
        // 查询海康配置
        CommunityHik communityHik = communityMapper.selectCommunityHikById(communityId);
        Optional.ofNullable(communityHik).ifPresent(hik -> {
            payLog.setCommunityName(hik.getCommunityName());
        });
        //向数据库中加入支付记录
        payLog.setTransactionId(transactionId);
        int result = payLogMapper.insertPayLog(payLog);
        //修改进出时的支付状态
        Long inLogId = attach.getLong("inLogId");
        String inTableName = attach.getString("inTableName");
        vehicleLogMapper.updateInLog(inTableName, inLogId);
        Long outLogId = attach.getLong("outLogId");
        String outTableName = attach.getString("outTableName");
        vehicleLogMapper.updateOutLog(outTableName,outLogId);
        //开门
        String roadwayIndex = attach.getString(PayConstants.ROADWAY_CODE);
        if(StringUtils.isNotEmpty(roadwayIndex)){
            boolean open = roadwayControl(communityHik, roadwayIndex);
            String msg = open ? "开门成功" : "开门失败";
            log.info(msg);
        }
        return result > 0;
    }

    private PayLog packagePayLog(Long communityId, BigDecimal payAmount, JSONObject attach, String outTradeNo, String payWay){
        String userType = attach.getString(PayConstants.USER_TYPE);
        String userName = attach.getString(PayConstants.USER_NAME);
        String userPhone = attach.getString(PayConstants.USER_PHONE);
        String plantNo = attach.getString("plantNo");
        log.info("===>userType：{}，userPhone：{}，plantNo：{}",userType,userPhone,plantNo);
        PayLog payLog = new PayLog();
        payLog.setPayType("0");
        payLog.setPayAmount(payAmount);
        payLog.setUserType(userType);
        payLog.setUserName(userName);
        payLog.setUserPhone(userPhone);
        payLog.setPlateNo(plantNo);
        payLog.setCommunityId(communityId);


        payLog.setOutTradeNo(outTradeNo);
        payLog.setPayWay(payWay);
        //检查表存不存在
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_PAY_LOG,communityId);
        String newTableName = TableConstants.LOG_DB_NAME+"."+tableName;
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            tableMapper.createPayLogTable(newTableName);
        }
        payLog.setTableName(newTableName);
        return payLog;
    }

    /**
     * 根据车道开门
     * @param hik 海康配置
     * @param roadwayIndex 车道
     * @return java.lang.Boolean
     */
    private static Boolean roadwayControl(CommunityHik hik,String roadwayIndex){
        if(null == hik){
            log.error("小区海康配置未配置===>");
            return false;
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        return HikUtils.roadwayDeviceControl(roadwayIndex, 1);
    }

}
