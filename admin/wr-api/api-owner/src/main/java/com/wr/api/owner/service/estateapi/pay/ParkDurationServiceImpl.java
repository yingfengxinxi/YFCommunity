package com.wr.api.owner.service.estateapi.pay;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.pay.CommunityMapper;
import com.wr.api.owner.mapper.estateapi.pay.ParkDurationMapper;
import com.wr.api.owner.mapper.estateapi.pay.ParkOrderMapper;
import com.wr.api.owner.mapper.estateapi.pay.PayBillingRecordMapper;
import com.wr.api.owner.mapper.home.pay.LiveMapper;
import com.wr.api.owner.service.estateapi.CarportService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.PayBillingRecord;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.remote.estate.financial.ParkOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/1/20 17:31
 */
@Slf4j
@Service
public class ParkDurationServiceImpl extends ServiceImpl<ParkDurationMapper, ParkDurationEntity> implements ParkDurationService {

    @Resource
    private RedisService redisService;
    @Resource
    private CarportService carportService;
    @Resource
    private ParkOrderMapper parkOrderMapper;
    @Resource
    private PayBillingRecordMapper payBillingRecordMapper;
    @Resource
    private CommunityMapper communityMapper;
    @Resource
    private LiveMapper liveMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWechatPay(JSONObject jsonObject) throws ParseException {

//{"mchid":"1613501627",
// "appid":"wx4b8eca064618b374",
// "out_trade_no":"1709098834126",
// "transaction_id":"4200002111202402289041489664",
// "trade_type":"JSAPI",
// "trade_state":"SUCCESS",
// "trade_state_desc":"支付成功",
// "bank_type":"OTHERS",
// "attach":"",
// "success_time":"2024-02-28T13:40:39+08:00",
// "payer":{"openid":"oOMPw5HQiF_QWjxekLQpd5xELssA"},
// "amount":{"total":10,"payer_total":10,"currency":"CNY","payer_currency":"CNY"}}

        String tradeState = String.valueOf( jsonObject.get( "trade_state" ) );
        if (tradeState.equals( "SUCCESS" )) {
            String orderNo = String.valueOf( jsonObject.get( "out_trade_no" ) );
            String successTime = String.valueOf( jsonObject.get( "success_time" ) );
            Date date = getPaySuccessTime( successTime );
            LambdaQueryWrapper<ParkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq( ParkOrder::getOrderNo, orderNo );
            ParkOrder order = parkOrderMapper.selectOne( lambdaQueryWrapper );
            order.setStatus( "1" );
            order.setFeeSuccessTime( date );
            parkOrderMapper.updateById( order );


            BusCarportVo detail = carportService.detailBusCarport( Long.parseLong( order.getCarportId() ) );
            //如果all_status = 0 rental_type = 0则all_status=2
            //如果all_status = 0 rental_type ！= 0则all_status=1
            //如果all_status = 1 || 3 rental_type ！= 0则all_status=1 并且将rental_over更新至rental_end，rental_over置空
            if (detail != null) {
                BusCarportVo busCarportVo = new BusCarportVo();
                busCarportVo.setCarportId( detail.getCarportId() );
                String allStatus = detail.getAllStatus();
                String rentalType = detail.getRentalType();

                // 更新 all_status 和 rental_over 字段的逻辑
                if ("0".equals( allStatus )) {
                    if ("0".equals( rentalType )) {
                        detail.setAllStatus( "2" );
                    } else {
                        detail.setAllStatus( "1" );
                    }
                } else if ("1".equals( allStatus ) || "3".equals( allStatus )) {
                    if (!"0".equals( rentalType )) {
                        detail.setAllStatus( "1" );
                        detail.setRentalEnd( detail.getRentalOver() );
                        detail.setRentalOver( null );
                    } else {
                        detail.setAllStatus( "2" );
                    }
                }
                carportService.updateByBusCarport( detail );
            }

            //支付成功更新bus_house表
//            carportService.updateCarportId( carport.getCarportId(), carport.getCommunityId(), carport.getRoomId() );

            redisService.deleteObject( orderNo );

            PayBillingRecord payBillingRecord = new PayBillingRecord();
            payBillingRecord.setId( UUID.randomUUID().toString().replace( "-", "" ) );
            Long estateId = communityMapper.getCommunityIdEstateId( detail.getCommunityId() );
            payBillingRecord.setEstateId( estateId );
            payBillingRecord.setCommunityId( detail.getCommunityId() );
            payBillingRecord.setOwnerId( null );
            payBillingRecord.setType( "1" );
            payBillingRecord.setPayAmount( order.getFee() );
            payBillingRecord.setDiscountAmount( new BigDecimal( "0.00" ) );
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            Date startTime = simpleDateFormat.parse( DateUtils.getDate() );
            payBillingRecord.setStartTime( startTime );
            payBillingRecord.setEndTime( DateUtils.dateTime( "yyyy-MM-dd", detail.getRentalEnd() ) );
            payBillingRecord.setPayType( "1" );
            payBillingRecord.setPayTime( new Date() );
            payBillingRecord.setPayBy( String.valueOf( detail.getAccountId() ) );
            payBillingRecord.setOrderNo( orderNo );
            payBillingRecord.setFlag( "1" );
            payBillingRecord.setCreateTime( new Date() );
            payBillingRecord.setCreateBy( String.valueOf( detail.getAccountId() ) );
            payBillingRecord.setDelFlag( "0" );

            //记录缴费记录
            payBillingRecordMapper.insert( payBillingRecord );
        }

    }

    private Date getPaySuccessTime(String paySuccessTime) throws ParseException {
        //String paysuccess="2024-02-28T13:40:39+08:00";
        String replace = paySuccessTime.replace( "T", " " );
        String[] split = replace.split( "\\+" );
        String date = split[0];
        SimpleDateFormat sim = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return sim.parse( date );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWechatPayProperty(JSONObject jsonObject) throws ParseException {
        String tradeState = String.valueOf( jsonObject.get( "trade_state" ) );
        if (tradeState.equals( "SUCCESS" )) {
            String orderNo = String.valueOf( jsonObject.get( "out_trade_no" ) );
            String successTime = String.valueOf( jsonObject.get( "success_time" ) );
            Date date = getPaySuccessTime( successTime );
            //获取订单号查询订单相关信息并更新为已缴费完成状态
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put( "orderNo",orderNo );
            liveMapper.updateStatusPropertyByNo( resultMap );
        }

    }

    @Override
    public String findFeeByOrderNo(String orderNo) {
        return liveMapper.findFeeByOrderNo( orderNo );
    }

    @Override
    public Integer updateOrderNoByNo(Map map) {
        return liveMapper.updateOrderNoByNo( map );
    }
}
