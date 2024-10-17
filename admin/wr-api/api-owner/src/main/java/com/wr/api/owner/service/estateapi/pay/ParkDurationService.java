package com.wr.api.owner.service.estateapi.pay;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.estate.financial.ParkDurationEntity;
import io.lettuce.core.dynamic.annotation.Param;

import java.text.ParseException;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/1/20 17:30
 */
public interface ParkDurationService extends IService<ParkDurationEntity> {

    /**
     * @param jsonObject
     */
    void updateWechatPay(JSONObject jsonObject) throws ParseException;
    
    /**
    * @Author SUNk
    * @Description 支付物业费用
    * @Date 17:53 2024/3/30
    * @Param [jsonObject]
    * @return void
    **/
    void updateWechatPayProperty(JSONObject jsonObject) throws ParseException;

    /**
     * @Author SUNk
     * @Description 查询物业费金额
     * @Date 17:46 2024/6/12
     * @Param [orderNo]
     * @return java.lang.String
     **/
    String findFeeByOrderNo(@Param( "orderNo" ) String orderNo);

    /*
     * @Author SUNk
     * @Description 更新订单号
     * @Date 17:47 2024/6/12
     * @Param [map]
     * @return java.lang.Integer
     **/
    Integer updateOrderNoByNo(Map map);
}
