//package com.wr.api.owner.expired;
//
//import com.wr.api.owner.service.estateapi.CarportService;
//import com.wr.api.owner.service.estateapi.pay.ParkOrderDelService;
//import com.wr.api.owner.service.estateapi.pay.ParkOrderService;
//import com.wr.common.core.constant.CacheConstants;
//import com.wr.common.core.utils.StringUtils;
//import com.wr.remote.estate.financial.ParkOrderDel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @ClassName InspectionSettingExpired
// * @Author lvzy
// * @Date 2023/6/10 0010 18:25
// * 物业车位费缴费redis到期执行
// **/
//
//@Component
//@Slf4j
//public class OwnerParkDurationExpired extends KeyExpirationEventMessageListener {
//
//    @Resource
//    private ParkOrderService parkOrderService;
//    @Resource
//    private ParkOrderDelService parkOrderDelService;
//    @Resource
//    private CarportService carportService;
//
//    public OwnerParkDurationExpired(RedisMessageListenerContainer listenerContainer) {
//        super( listenerContainer );
//    }
//
//    /**
//     * 过期redisKey
//     *
//     * @param message Key
//     * @param pattern 不知道是个什么东东 父类也没有使用这个参数
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        super.onMessage( message, pattern );
//        String redisKey = message.toString();
//        String[] redisKeys = redisKey.split( ":" );
//        if (redisKey.contains( CacheConstants.OWNER_ORDER )) {
//            String orderNo = redisKeys[1];
//            log.info( "开始删除{}订单", orderNo );
//            ParkOrderDel parkOrderDel = parkOrderService.getByOrderNoInfo( orderNo );
//            if (parkOrderDel != null) {
//                parkOrderDelService.save( parkOrderDel );
//                //获取车位管理ID然后恢复初始状态
//                if (StringUtils.isNotEmpty( parkOrderDel.getCarportId() )) {
//                    carportService.clearBusCarport( parkOrderDel.getCarportId());
//                }
//                //删除订单
//                parkOrderService.deleteByOrderNo( orderNo );
//            }
//        }
//    }
//}
