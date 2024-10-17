package com.wr.api.owner.web.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.owner.entity.enums.WechatPayStatus;
import com.wr.api.owner.entity.vo.pay.ParkDurationPayDTO;
import com.wr.api.owner.entity.vo.pay.ParkDurationVo;
import com.wr.api.owner.service.estateapi.CarportService;
import com.wr.api.owner.service.estateapi.pay.ParkDurationService;
import com.wr.api.owner.service.estateapi.pay.ParkOrderService;
import com.wr.api.owner.service.estateapi.pay.PayService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.constant.DelConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.pay.domain.WeChatPayResultIn;
import com.wr.common.payment.pay.util.WechatPayCallBackUtils;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.remote.estate.financial.ParkOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkDurationController
 * @History: 车位缴费规则配置
 */
@Slf4j
@RestController
@RequestMapping("/estateapi/parkDuration")
@Api(tags = "物业移动端-车位缴费")
public class ParkDurationController extends BaseController {

    @Resource
    private ParkDurationService parkDurationService;
    @Resource
    private PayService payService;
    @Autowired
    private RedisService redisService;
    @Resource
    private ParkOrderService parkOrderService;
    @Resource
    private CarportService carportService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;//事务管理器
    @Autowired
    private TransactionDefinition transactionDefinition;// 事务的一些基础信息，如超时时间、隔离级别、传播属性等

    /**
     * @param payDTO
     * @return
     */
    @PostMapping("findFeeDuration")
    @ApiOperation("租赁购买下拉查询相关价格")
    public AjaxResult findFeeDuration(@RequestBody ParkDurationPayDTO payDTO) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( ParkDurationEntity::getCommunityId, payDTO.getCommunityId() )
                .eq( ParkDurationEntity::getLeasePurchase, payDTO.getLeasePurchase() )
                .eq( StringUtils.isNotEmpty( payDTO.getPurchasePackage() ) && StringUtils.equals( payDTO.getLeasePurchase(), "0" ), ParkDurationEntity::getPurchasePackage, payDTO.getPurchasePackage() )
                .eq( ParkDurationEntity::getDelFlag, "0" );
        List<ParkDurationEntity> list = parkDurationService.list( queryWrapper );

        return AjaxResult.success( list );
    }


    /**
     * @param communityId
     * @param leasePurchase
     * @return
     */
    @GetMapping("getPurchasePackageList")
    @ApiOperation("选择套餐下拉")
    public AjaxResult getPurchasePackageList(@RequestParam("communityId") Long communityId, @RequestParam("leasePurchase") String leasePurchase) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( ParkDurationEntity::getCommunityId, communityId );
        queryWrapper.eq( ParkDurationEntity::getLeasePurchase, leasePurchase );
        queryWrapper.eq( ParkDurationEntity::getDelFlag, "0" );
        List<ParkDurationEntity> list = parkDurationService.list( queryWrapper );
        List<ParkDurationVo> parkDurationVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty( list )) {
            List<String> collect = list.stream().map( ParkDurationEntity::getPurchasePackage ).distinct().collect( Collectors.toList() );
            if (CollectionUtils.isNotEmpty( collect )) {
                collect.stream().forEach( le -> {
                    ParkDurationVo parkDurationVo = new ParkDurationVo();
                    parkDurationVo.setId( le );
                    parkDurationVo.setName( le.equals( "0" ) ? "包年" : "包月" );
                    parkDurationVoList.add( parkDurationVo );
                } );
            }
        }
        return AjaxResult.success( parkDurationVoList );
    }

    /**
     * @param communityId
     * @param leasePurchase
     * @return
     */
    @GetMapping("getFee")
    @ApiOperation("查询费用")
    public AjaxResult getFee(
            @RequestParam("communityId") Long communityId,
            @RequestParam("leasePurchase") String leasePurchase,
            @RequestParam("purchasePackage") String purchasePackage
    ) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( ParkDurationEntity::getCommunityId, communityId );
        queryWrapper.eq( ParkDurationEntity::getLeasePurchase, leasePurchase );
        queryWrapper.eq( ParkDurationEntity::getPurchasePackage, purchasePackage );
        queryWrapper.eq( ParkDurationEntity::getDelFlag, "0" );
        List<ParkDurationEntity> list = parkDurationService.list( queryWrapper );

        return AjaxResult.success( list );
    }


    /**
     * 微信支付
     * 费用从前端传入
     *
     * @param parkDurationPayDTO
     * @return
     */
    @ApiOperation("支付")
    @PostMapping("wechat/pay")
    // @Transactional
    public AjaxResult wechatPayment(@RequestBody ParkDurationPayDTO parkDurationPayDTO, HttpServletRequest request) {
        TransactionStatus transaction = platformTransactionManager.getTransaction( transactionDefinition );//TransactionStatus ： 事务的一些状态信息，如是否是一个新的事务、是否已被标记为回滚
        try {
            log.info( "微信调用===>{}", parkDurationPayDTO.toString() );
            //查询微信支付参数
            CommunityWechat communityWechat = getCommunityWechatById( parkDurationPayDTO.getCommunityId() );
            if (communityWechat == null) {
                log.error( "未查询到账号信息" );
                throw new ServiceException( "未查询到账号信息" );
            }

            // 封装微信支付参数
            WechatOpen wechatOpen = packageWechatPay( parkDurationPayDTO, communityWechat );
            if (StringUtils.isNotEmpty( parkDurationPayDTO.getOrderNo() )) {
                wechatOpen.setOutTradeNo( parkDurationPayDTO.getOrderNo() );
            }
            wechatOpen.setClientIp( IpUtils.getIpAddr( request ) );


            if (StringUtils.isNotEmpty( parkDurationPayDTO.getOrderNo() )) {
                //查询订单
                Map<String, String> payOrderResult = WechatPayUtils.getPayOrderResult( wechatOpen );
                //未支付
                if (WechatPayStatus.NOT_PAID.equals( payOrderResult.get( "code" ) )) {
                    //关闭订单
                    WechatPayUtils.closeOrder( wechatOpen );

                    //删除订单表数据
                    LambdaQueryWrapper<ParkOrder> queryWrapper1 = new LambdaQueryWrapper<>();
                    queryWrapper1.eq( ParkOrder::getOrderNo, parkDurationPayDTO.getOrderNo() );
                    parkOrderService.remove( queryWrapper1 );
                }
//                String outTradeNo = String.valueOf( System.currentTimeMillis() );
                wechatOpen.setOutTradeNo( parkDurationPayDTO.getOrderNo() );
                if (WechatPayStatus.PAID.equals( payOrderResult.get( "code" ) )) {
                    //删除订单表数据
                    return AjaxResult.error( "当前订单已支付请勿重复支付" );
                }
            }

            //当前车位如果已经购买完成不允许再次支付
            // 校验车位状态
            validateCarportStatus( parkDurationPayDTO.getCarportId() );

            //微信支付
            Map<String, Object> map = WechatPayUtils.payment( wechatOpen );
            log.error( map.toString() );

            // 保存订单信息到数据库和缓存
            saveParkOrder( parkDurationPayDTO, wechatOpen );
            platformTransactionManager.commit( transaction );
            return AjaxResult.success( map );
        } catch (Exception e) {
            platformTransactionManager.rollback( transaction );
            //订单申城失败，删除对应的车位订单
            carportService.update( new LambdaUpdateWrapper<BusCarport>().set( BusCarport::getDelFlag, DelConstants.IS_DELETE )
                    .eq( BusCarport::getCarportId, parkDurationPayDTO.getCarportId() ) );
            throw new RuntimeException( e );
        }
    }

    // 查询微信支付参数
    private CommunityWechat getCommunityWechatById(Long communityId) {
        List<CommunityWechat> communityWechatList = payService.selectWechatById( communityId, null );
        return CollectionUtils.isNotEmpty( communityWechatList ) ? communityWechatList.get( 0 ) : null;
    }

    // 校验车位状态
    private void validateCarportStatus(String carportId) {
        BusCarportVo detail = carportService.detail( Long.parseLong( carportId ) );
        if (detail == null) {
            throw new ServiceException( "获取车位信息失败，请稍后重试！" );
        }
        if (StringUtils.equals( detail.getAllStatus(), "2" )) {
            throw new ServiceException( "当前车位已出售，不允许再次缴费！" );
        }
    }

    // 保存订单信息到数据库和缓存
    private void saveParkOrder(ParkDurationPayDTO parkDurationPayDTO, WechatOpen wechatOpen) {
        // 构建订单对象
        ParkOrder parkOrder = new ParkOrder();
        parkOrder.setOrderNo( wechatOpen.getOutTradeNo() );
        parkOrder.setCarportId( parkDurationPayDTO.getCarportId() );
        parkOrder.setFee( wechatOpen.getTotalAmount() );
        parkOrder.setStatus( "0" ); // 设置订单状态为未支付
        parkOrder.setYearMonth( parkDurationPayDTO.getYearMonth() );
        parkOrder.setPurchasePackage( parkDurationPayDTO.getPurchasePackage() );
        parkOrder.setLeasePurchase( parkDurationPayDTO.getLeasePurchase() );
        parkOrder.setEndTime( parkDurationPayDTO.getEndDate() );
        parkOrder.setUpdateTime( new Date() );
        parkOrder.setCreateTime( new Date() );

        // 保存订单到数据库
        parkOrderService.saveOrUpdate( parkOrder );

        // 保存订单到缓存
        String key = CacheConstants.PARK_ORDER + wechatOpen.getOutTradeNo();
        redisService.setCacheObject( key, parkOrder, 10L, TimeUnit.MINUTES );
    }

    /**
     * @param parkDurationPayDTO
     * @param communityWechat
     * @return
     */
    private WechatOpen packageWechatPay(ParkDurationPayDTO parkDurationPayDTO, CommunityWechat communityWechat) {
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setMethod( PayConstants.JSAPI );
        wechatOpen.setDescription( PayConstants.SPACE_PARK );
        String outTradeNo = String.valueOf( System.currentTimeMillis() );
        wechatOpen.setOutTradeNo( outTradeNo );
        wechatOpen.setTotalAmount( parkDurationPayDTO.getFee() );
        wechatOpen.setTimeExpire( PaymentUtil.wechatTimeExpire( 5 ) );
        wechatOpen.setOpenId( parkDurationPayDTO.getOpenId() );
        //回调地址
        String ownerServerApp = redisService.getCacheObject( CacheConstants.SYS_CONFIG_KEY + "OWNER_SERVER_APP" );
        String notifyUrl = ownerServerApp + "/estateapi/parkDuration/wechat_notify";
        wechatOpen.setNotifyUrl( notifyUrl );
        wechatOpen.setWechatId( communityWechat.getWechatId() );
        wechatOpen.setAppId( communityWechat.getOwnerAppId() );
        wechatOpen.setMchId( communityWechat.getMchId() );
        wechatOpen.setSerialNumber( communityWechat.getSerialNumber() );
        wechatOpen.setApiKey( communityWechat.getApiKey() );
        wechatOpen.setPrivateKeyUrl( communityWechat.getPrivateUrl() );
        return wechatOpen;
    }

    /**
     * 计算费用
     *
     * @param parkDurationPayDTO
     * @return
     */
    private void getFee(ParkDurationPayDTO parkDurationPayDTO) {
        ParkDurationEntity durationEntity = parkDurationService.getById( parkDurationPayDTO.getParkDurationId() );
        BigDecimal hourFee = durationEntity.getHourFee();
        parkDurationPayDTO.setFee( hourFee.multiply( new BigDecimal( parkDurationPayDTO.getYearMonth() ) ) );
    }


    @PostMapping("wechat_notify")
    public String notify(@RequestBody WeChatPayResultIn weChatPayResultIn) {

        try {
            log.info( "********** START车位缴费微信支付回调执行 *************" );

            List<CommunityWechat> wechatList = payService.selectWechatById( null, null );
            for (CommunityWechat wechat : wechatList) {
                JSONObject jsonObject = WechatPayCallBackUtils.wechatPayCallBack( wechat.getApiKey(), weChatPayResultIn );
                log.info( "微信支付回调参数:{}", jsonObject );
                parkDurationService.updateWechatPay( jsonObject );
            }

            log.info( "********** END车位缴费微信支付回调执行 *************" );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
        return "SUCCESS";
    }


    @PostMapping("/test")
    public AjaxResult test(HttpServletRequest request,
                           @RequestBody WeChatPayResultIn weChatPayResultIn) {
        String body = "{\"id\":\"cae8d800-a52d-5072-911f-fb436b1e54cd\",\"create_time\":\"2024-02-28T12:17:57+08:00\",\"resource_type\":\"encrypt-resource\",\"event_type\":\"TRANSACTION.SUCCESS\",\"summary\":\"支付成功\",\"resource\":{\"original_type\":\"transaction\",\"algorithm\":\"AEAD_AES_256_GCM\",\"ciphertext\":\"TVd/Gl8wNYIIWRk3WHVaNL9mLj1h0pPUpLxP7fFoOj2USvEUC8LnuIsnhKbbs133QsX5GZWJuDujIPpMX8Nv83krBvrluk7mBVXtpJfthWWj8DxpzR4C8kHsN7x05tawSOwNQx3SFJdIUFDr46lzSGCFMgDbB587kARLFlZLFTiLHcjSsD8yzCaIy392hA1HNBtlyAIdDhxC366nhlt6kKMQZrC4jbIDlzEzLitYf1SxOCFWyJ7nF0TDBRHS8ZHfkMmDfeyARzI8SP5YjgO3LmpWGgoXk3mN3Meg55Br5CMiOmqSu9uJw80nxpICDJRO+M9vVZKByqPIs+cWTniCLJmgsIwPEL7Un6u4u/WPPw5NJqWfAUgxKFAsO4C7FTU3Vj9VEvtxVPkGtgMOzCeQKpkIs7F2I9VdTnU+2NyXQxt15EVah1Wb2+C50/CayjQTIYTYFG8/IbCexEJbtgu5U3jbN0Ih9KfGAs7Aiz92A/uCT68SqLXu20Q48r1cW1rIY3WTY4YfF3X+OUjRyfQI9N34XADlwQJDTYQr5wALcDeADbksLyutU7yekBw+ug==\",\"associated_data\":\"transaction\",\"nonce\":\"TsvbGdYZUaH5\"}}";
        List<CommunityWechat> wechatList = payService.selectWechatById( null, null );
        for (CommunityWechat wechat : wechatList) {
            JSONObject jsonObject = WechatPayCallBackUtils.wechatPayCallBack( wechat.getApiKey(), weChatPayResultIn );
            System.out.println( wechat.getAppId() + ">>>>>>" + jsonObject );
        }

        return AjaxResult.success();
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Author SUNk
     * @Description 物业费支付
     * @Date 15:07 2024/3/30
     * @Param [parkDurationPayDTO, request]
     **/
    @ApiOperation("物业费支付")
    @PostMapping("wechat/payProperty")
    public AjaxResult payProperty(@RequestBody ParkDurationPayDTO parkDurationPayDTO, HttpServletRequest request) {
        if (StringUtils.isBlank( parkDurationPayDTO.getOrderNo() )) {
            throw new ServiceException( "获取物业费用订单号失败，请稍后重试！" );
        }

        //根据订单号获取金额
        String fee = parkDurationService.findFeeByOrderNo( parkDurationPayDTO.getOrderNo() );
        parkDurationPayDTO.setFee( new BigDecimal( fee ) );

        try {
            log.info( "微信调用===>{}", JSONObject.toJSONString( parkDurationPayDTO ) );

            //查询微信支付参数
            List<CommunityWechat> communityWechatList = payService.selectWechatById( parkDurationPayDTO.getCommunityId(), null );
            if (CollectionUtils.isEmpty( communityWechatList )) {
                log.error( "未查询到账号信息" );
                return AjaxResult.error( "未查询到账号信息" );
            }
            CommunityWechat communityWechat = communityWechatList.get( 0 );

            WechatOpen wechatOpen = packageWechatPayProperty( parkDurationPayDTO, communityWechat );
            wechatOpen.setClientIp( IpUtils.getIpAddr( request ) );

            if (StringUtils.isNotEmpty( parkDurationPayDTO.getOrderNo() )) {
                //查询订单
                Map<String, String> payOrderResult = WechatPayUtils.getPayOrderResult( wechatOpen );
                //未支付
                if (StringUtils.equals( payOrderResult.get( "code" ), "502" )) {
                    //关闭订单
                    WechatPayUtils.closeOrder( wechatOpen );
                }

                //重新生成订单号并进行更新
                log.info( "订单号更换，原订单号===>{}", JSONObject.toJSONString( parkDurationPayDTO.getOrderNo() ) );
                String orderNoOld = parkDurationPayDTO.getOrderNo();
                String timestampStr = Long.toString( Instant.now().toEpochMilli() );
                String lastSixTimestamp = timestampStr.substring( timestampStr.length() - 6 );
                String newOrderNo = orderNoOld.substring( 0, orderNoOld.length() - 6 ) + lastSixTimestamp;
                //更新订单号
                Map<String, Object> map = new HashMap<>();
                map.put( "orderNo", newOrderNo );
                map.put( "orderNoOld", orderNoOld );
                parkDurationService.updateOrderNoByNo( map );
                parkDurationPayDTO.setOrderNo( newOrderNo );
                log.info( "订单号更换，新订单号===>{}", JSONObject.toJSONString( newOrderNo ) );

                wechatOpen.setOutTradeNo( parkDurationPayDTO.getOrderNo() );

                if (StringUtils.equals( payOrderResult.get( "code" ), "200" )) {
                    //删除订单表数据
                    return AjaxResult.error( "当前订单已支付请勿重复支付" );
                }

            }

            //微信支付
            Map<String, Object> map = WechatPayUtils.payment( wechatOpen );
            log.error( map.toString() );

            return AjaxResult.success( map );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
    }

    /**
     * @param parkDurationPayDTO
     * @param communityWechat
     * @return
     */
    private WechatOpen packageWechatPayProperty(ParkDurationPayDTO parkDurationPayDTO, CommunityWechat communityWechat) {
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setMethod( PayConstants.JSAPI );
        wechatOpen.setDescription( PayConstants.DESC_PROPERTY );
        wechatOpen.setOutTradeNo( parkDurationPayDTO.getOrderNo() );
        wechatOpen.setTotalAmount( parkDurationPayDTO.getFee() );
        wechatOpen.setTimeExpire( PaymentUtil.wechatTimeExpire( 5 ) );
        wechatOpen.setOpenId( parkDurationPayDTO.getOpenId() );
        //回调地址
        String ownerServerApp = redisService.getCacheObject( CacheConstants.SYS_CONFIG_KEY + "OWNER_SERVER_APP" );
        String notifyUrl = ownerServerApp + "/estateapi/parkDuration/wechat_notify_property";
        wechatOpen.setNotifyUrl( notifyUrl );
        wechatOpen.setWechatId( communityWechat.getWechatId() );
        wechatOpen.setAppId( communityWechat.getOwnerAppId() );
        wechatOpen.setMchId( communityWechat.getMchId() );
        wechatOpen.setSerialNumber( communityWechat.getSerialNumber() );
        wechatOpen.setApiKey( communityWechat.getApiKey() );
        wechatOpen.setPrivateKeyUrl( communityWechat.getPrivateUrl() );
        return wechatOpen;
    }

    @PostMapping("wechat_notify_property")
    public String wechatNotifyProperty(@RequestBody WeChatPayResultIn weChatPayResultIn) {

        try {
            log.info( "------------- START物业缴费微信支付回调执行 ------------------" );

            List<CommunityWechat> wechatList = payService.selectWechatById( null, null );
            for (CommunityWechat wechat : wechatList) {
                JSONObject jsonObject = WechatPayCallBackUtils.wechatPayCallBack( wechat.getApiKey(), weChatPayResultIn );
                log.info( "微信支付回调参数:{}", jsonObject );
                parkDurationService.updateWechatPayProperty( jsonObject );
            }

            log.info( "----------------- END物业缴费微信支付回调执行 ----------------" );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
        return "SUCCESS";
    }
}
