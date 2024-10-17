package com.wr.api.estate.service.impl.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.entity.dto.ParkDurationPayDTO;
import com.wr.api.estate.mapper.application.CommunityCareMapper;
import com.wr.api.estate.mapper.application.CommunityMapper;
import com.wr.api.estate.mapper.application.property.StaffPropertyMapper;
import com.wr.api.estate.mapper.application.serve.ParkDurationMapper;
import com.wr.api.estate.mapper.application.serve.ParkOrderMapper;
import com.wr.api.estate.mapper.application.serve.PayBillingRecordMapper;
import com.wr.api.estate.service.PayService;
import com.wr.api.estate.service.application.serve.ParkDurationService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.pay.util.WXPaySignatureCertificateUtil;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.domain.PayBillingRecord;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.remote.estate.financial.ParkOrder;
import com.wr.remote.estate.property.StaffProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/1/20 17:31
 */
@Slf4j
@Service
public class ParkDurationServiceImpl extends ServiceImpl<ParkDurationMapper, ParkDurationEntity> implements ParkDurationService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ParkOrderMapper parkOrderMapper;

    @Autowired
    private StaffPropertyMapper staffPropertyMapper;

    @Autowired
    private PayBillingRecordMapper payBillingRecordMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWechatPay(JSONObject jsonObject) throws ParseException {
        String tradeState = String.valueOf(jsonObject.get("trade_state"));
        if (tradeState.equals("SUCCESS")) {
            String orderNo = String.valueOf(jsonObject.get("out_trade_no"));
            String successTime = String.valueOf(jsonObject.get("success_time"));
            Date date = getPaySuccessTime(successTime);
            LambdaQueryWrapper<ParkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ParkOrder::getOrderNo, orderNo);
            ParkOrder order = parkOrderMapper.selectOne(lambdaQueryWrapper);
            order.setStatus("1");
            order.setFeeSuccessTime(date);
            parkOrderMapper.updateById(order);
            //更新个人资产
            StaffProperty staffProperty = staffPropertyMapper.selectById(order.getStaffPropertyId());
            System.out.println("parkOrder中内容:" + order.toString());
            if (order.getEndTime() != null) {
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endTime = sim.format(order.getEndTime());
                endTime = endTime.substring(0, 10);
                System.out.println(endTime);
                sim = new SimpleDateFormat("yyyy-MM-dd");
                staffProperty.setEndTime(sim.parse(endTime));
            } else {
                staffProperty.setEndTime(null);
            }
            staffProperty.setLeasePurchase(order.getLeasePurchase());
            if (StringUtils.isNotEmpty(order.getPurchasePackage())) {
                staffProperty.setPurchasePackage(order.getPurchasePackage());
            } else {
                staffProperty.setPurchasePackage("");
            }
            if (order.getYearMonth() != null) {
                staffProperty.setYearMonth(order.getYearMonth());
            } else {
                staffProperty.setYearMonth(null);
            }
            staffPropertyMapper.updateByIdStaffProperty(staffProperty);

            PayBillingRecord payBillingRecord = new PayBillingRecord();
            payBillingRecord.setId(UUID.randomUUID().toString().replace("-", ""));
            Long estateId = communityMapper.getCommunityIdEstateId(staffProperty.getCommunityId());
            payBillingRecord.setEstateId(estateId);
            payBillingRecord.setCommunityId(staffProperty.getCommunityId());
            payBillingRecord.setOwnerId(null);
            payBillingRecord.setType("1");
            payBillingRecord.setPayAmount(order.getFee());
            payBillingRecord.setDiscountAmount(new BigDecimal("0.00"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startTime = simpleDateFormat.parse(DateUtils.getDate());
            payBillingRecord.setStartTime(startTime);
            payBillingRecord.setEndTime(staffProperty.getEndTime());
            payBillingRecord.setPayType("1");
            payBillingRecord.setPayTime(new Date());
            payBillingRecord.setPayBy(staffProperty.getCreateBy());
            payBillingRecord.setOrderNo(orderNo);
            payBillingRecord.setFlag("0");
            payBillingRecord.setCreateTime(new Date());
            payBillingRecord.setCreateBy(staffProperty.getCreateBy());
            payBillingRecord.setDelFlag("0");

            //记录缴费记录
            payBillingRecordMapper.insert(payBillingRecord);
            redisService.deleteObject(orderNo);
        }

    }

    private Date getPaySuccessTime(String paySuccessTime) throws ParseException {
        //String paysuccess="2024-02-28T13:40:39+08:00";
        String replace = paySuccessTime.replace("T", " ");
        String[] split = replace.split("\\+");
        String date = split[0];
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sim.parse(date);
    }
}
