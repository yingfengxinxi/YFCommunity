package com.wr.api.owner.service.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wr.api.owner.entity.dto.LiveDTO;
import com.wr.api.owner.entity.vo.home.PropertyFeeVO;
import com.wr.api.owner.entity.vo.home.PropertyLogVO;
import com.wr.api.owner.entity.vo.pay.PropertyLogPageVO;
import com.wr.api.owner.mapper.CommunitysMapper;
import com.wr.api.owner.mapper.home.pay.LiveMapper;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.domain.PropertyLog;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:48:20
 * @Desc: 生活缴费业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LiveServiceImpl implements LiveService {
    private static final Logger log = LoggerFactory.getLogger(LiveServiceImpl.class);

    private final LiveMapper liveMapper;
    private final CommunitysMapper communitysMapper;

    @Override
    public AjaxResult getLiveFee(LiveDTO liveDTO) {
        JSONObject fee = new JSONObject();
        fee.put("waterFee", BigDecimal.ZERO);
        fee.put("eleFee", BigDecimal.ZERO);
        fee.put("gasFee", BigDecimal.ZERO);
        // 查询物业费规则
        PropertyFeeVO feeVO = liveMapper.getPropertyFee(liveDTO.getCommunityId());
        // 不存在或者停用
        if(null == feeVO || "1".equals(feeVO.getFeeStatus())){
            fee.put("propertyFee", BigDecimal.ZERO);
            return AjaxResult.success(fee);
        }
        BigDecimal freeAmount = Optional.ofNullable(feeVO.getFeeAmount()).orElse(BigDecimal.ZERO);
        // 统一收费
        if("1".equals(feeVO.getFeeType())){
            fee.put("propertyFee", freeAmount);
            return AjaxResult.success(fee);
        }
        // 按住宅面积收 -- 查询房间面积
        BigDecimal area = liveMapper.getRoomAreaByRoomId(liveDTO.getRoomId());
        BigDecimal totalArea = Optional.ofNullable(area).orElse(BigDecimal.ZERO);
        BigDecimal propertyFee = freeAmount.multiply(totalArea);
        fee.put("propertyFee", propertyFee);
        return AjaxResult.success(fee);
    }

    @Override
    public AjaxResult getPropertyLog(LiveDTO liveDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        // 查询物业名称
        String estateName = liveMapper.getEstateNameById(liveDTO.getEstateId());
        ajaxResult.put("estateName",estateName);
        // 查询楼栋名称
        String buildName = liveMapper.getBuildNameById(liveDTO.getBuildingId());
        ajaxResult.put("buildName",buildName);
        // 查询单元名称
        String unitName = liveMapper.getUnitNameById(liveDTO.getUnitId());
        ajaxResult.put("unitName",unitName);
        // 查询房间名称
        String roomName = liveMapper.getRoomNameById(liveDTO.getRoomId());
        ajaxResult.put("roomName",roomName);
        // 查询物业缴费记录
        List<PropertyLogVO> logList = liveMapper.selectPropertyLogByRoom(liveDTO.getRoomId());
        ajaxResult.put("logList",logList);
        return ajaxResult;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean insertPropertyLog(HttpServletRequest request, JSONObject attach) {
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
        Long buildingId = attach.getLong("buildingId");
        Long unitId = attach.getLong("unitId");
        Long roomId = attach.getLong("roomId");
        Long wechatId = attach.getLong(PayConstants.WECHAT_ID);
        log.info("===>小区id:{}，楼栋id：{}，单元id：{}，房间id：{}，微信账号id：{}",
                communityId,buildingId,unitId,roomId,wechatId);
        //通过证书序列号查询
        CommunityWechat wechat = communitysMapper.selectWechatById(wechatId);
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
        // 封装数据
        PropertyLog propertyLog = packagePropertyLog(communityId, buildingId, unitId, roomId, payAmount);
        int payYear = attach.getIntValue("payYear");
        propertyLog.setPayYear(payYear);
        propertyLog.setPayWay("0");
        // 记录物业费缴费记录
        int result = liveMapper.insertPropertyLog(propertyLog);
        return result > 0;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean insertAliPayLog(Map<String, String> params, JSONObject attach) {
        String appId = params.get("app_id");
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>appId:{},支付方式:{}",appId,payWay);
        log.info("passback_params==>{}",attach);
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        Long buildingId = attach.getLong("buildingId");
        Long unitId = attach.getLong("unitId");
        Long roomId = attach.getLong("roomId");
        Long alipayId = attach.getLong(PayConstants.ALIPAY_ID);
        log.info("===>小区id:{}，楼栋id：{}，单元id：{}，房间id：{}，支付宝账号id：{}",
                communityId,buildingId,unitId,roomId,alipayId);

        // 根据 communityId 到数据库中查询 publicKey
        CommunityAli communityAli = communitysMapper.selectAliById(alipayId);
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
            PropertyLog propertyLog = packagePropertyLog(communityId, buildingId, unitId, roomId, new BigDecimal(amount));
            int payYear = attach.getIntValue("payYear");
            propertyLog.setPayYear(payYear);
            propertyLog.setPayWay("1");
            // 记录物业费缴费记录
            int result = liveMapper.insertPropertyLog(propertyLog);
            return result > 0;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝回调失败，code:{},msg:{}",e.getErrCode(),e.getErrMsg());
            return false;
        }
    }

    private PropertyLog packagePropertyLog(Long communityId, Long buildingId, Long unitId, Long roomId, BigDecimal payAmount){
        PropertyLog propertyLog = new PropertyLog();
        propertyLog.setCommunityId(communityId);
        propertyLog.setBuildingId(buildingId);
        propertyLog.setUnitId(unitId);
        propertyLog.setRoomId(roomId);
        BigDecimal area = liveMapper.getRoomAreaByRoomId(roomId);
        BigDecimal totalArea = Optional.ofNullable(area).orElse(BigDecimal.ZERO);
        propertyLog.setRoomArea(totalArea);

        PropertyFeeVO feeVO = liveMapper.getPropertyFee(communityId);
        Optional.ofNullable(feeVO).ifPresent(vo-> {
            propertyLog.setFeeType(vo.getFeeType());
            propertyLog.setFeeAmount(vo.getFeeAmount());
        });
        propertyLog.setPayAmount(payAmount);
        return propertyLog;
    }

    @Override
    public AjaxResult findDetilProperty(Long logId) {
        return AjaxResult.success(liveMapper.findDetilProperty( logId ));
    }

    @Override
    public List<PropertyLogPageVO> findListPropertyLog(LiveDTO liveDTO) {
        List<PropertyLogPageVO> listPropertyLog = liveMapper.findListPropertyLog(liveDTO);
        for (PropertyLogPageVO propertyLogPageVO : listPropertyLog) {
            //实际缴费金额 = 应交费用 -优惠金额
            BigDecimal paidAmount = propertyLogPageVO.getYjAmount().subtract(propertyLogPageVO.getDiscountAmount());
            propertyLogPageVO.setPaidAmount(paidAmount);
        }
        return listPropertyLog;
    }
}
