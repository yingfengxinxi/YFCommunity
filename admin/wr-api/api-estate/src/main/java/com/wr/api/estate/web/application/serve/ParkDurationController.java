package com.wr.api.estate.web.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.entity.dto.ParkDurationPayDTO;
import com.wr.api.estate.service.application.serve.CarportNoService;
import com.wr.remote.domain.vo.ParkDurationVo;
import com.wr.api.estate.service.PayService;
import com.wr.api.estate.service.application.property.StaffPropertyService;
import com.wr.api.estate.service.application.serve.ParkDurationService;
import com.wr.api.estate.service.application.serve.ParkOrderService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.pay.domain.WeChatPayResultIn;
import com.wr.common.payment.pay.util.WechatPayCallBackUtils;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.common.payment.util.PaymentUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.remote.estate.financial.ParkOrder;
import com.wr.remote.estate.property.StaffProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
@RequestMapping("v2/parkDuration")
@Api(tags = "物业移动端-车位缴费")
public class ParkDurationController extends BaseController {

    @Autowired
    private ParkDurationService parkDurationService;

    @Autowired
    private PayService payService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ParkOrderService parkOrderService;

    @Autowired
    private StaffPropertyService staffPropertyService;

    @Autowired
    private CarportNoService carportNoService;


    /**
     * @param communityId
     * @return
     */
    @GetMapping("getLeasePurchaseList")
    @ApiOperation("租赁购买下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = true, paramType = "query"),
    })
    public AjaxResult getLeasePurchaseList(@RequestParam("communityId") Long communityId, @RequestParam("carportNature") String carportNature) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, carportNature);
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);
        List<ParkDurationVo> parkDurationVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> collect = list.stream().map(ParkDurationEntity::getLeasePurchase).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                collect.stream().forEach(le -> {
                    ParkDurationVo parkDurationVo = new ParkDurationVo();
                    parkDurationVo.setId(le);
                    parkDurationVo.setName(le.equals("0") ? "租" : "买");
                    parkDurationVoList.add(parkDurationVo);
                });
            }
        }
        return AjaxResult.success(parkDurationVoList);
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
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, leasePurchase);
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);
        List<ParkDurationVo> parkDurationVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> collect = list.stream().map(ParkDurationEntity::getPurchasePackage).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                collect.stream().forEach(le -> {
                    ParkDurationVo parkDurationVo = new ParkDurationVo();
                    parkDurationVo.setId(le);
                    parkDurationVo.setName(le.equals("0") ? "包年" : "包月");
                    parkDurationVoList.add(parkDurationVo);
                });
            }
        }
        return AjaxResult.success(parkDurationVoList);
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
            @RequestParam(value = "purchasePackage", required = false) String purchasePackage
    ) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, leasePurchase);
        if (StringUtils.isNotEmpty(purchasePackage)) {
            queryWrapper.eq(ParkDurationEntity::getPurchasePackage, purchasePackage);
        }
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);

        return AjaxResult.success(list);
    }


    /**
     * 微信支付
     *
     * @param parkDurationPayDTO
     * @return
     */
    @ApiOperation("支付")
    @PostMapping("wechat/pay")
    public AjaxResult wechatPayment(@RequestBody ParkDurationPayDTO parkDurationPayDTO, HttpServletRequest request) {

        try {
            log.info("微信调用===>{}", parkDurationPayDTO.toString());
            //查询微信支付参数
            List<CommunityWechat> communityWechatList = payService.selectWechatById(parkDurationPayDTO.getCommunityId(), null);
            if (CollectionUtils.isEmpty(communityWechatList)) {
                log.error("未查询到账号信息");
                return AjaxResult.error("未查询到账号信息");
            }

            if (StringUtils.isEmpty(parkDurationPayDTO.getParkDurationId())) {

                return AjaxResult.error("个人资产认证id【parkDurationId】不能为空");
            }

            CommunityWechat communityWechat = communityWechatList.get(0);
            //费用
            getFee(parkDurationPayDTO);
            WechatOpen wechatOpen = packageWechatPay(parkDurationPayDTO, communityWechat);
            if (StringUtils.isNotEmpty(parkDurationPayDTO.getOrderNo())) {
                wechatOpen.setOutTradeNo(parkDurationPayDTO.getOrderNo());
            }
            wechatOpen.setClientIp(IpUtils.getIpAddr(request));
            if (StringUtils.isNotEmpty(parkDurationPayDTO.getOrderNo())) {
                //查询订单
                Map<String, String> payOrderResult = WechatPayUtils.getPayOrderResult(wechatOpen);
                //未支付
                if (StringUtils.equals(payOrderResult.get("code"), "502")) {
                    //关闭订单
                    WechatPayUtils.closeOrder(wechatOpen);

                    //删除订单表数据
                    LambdaQueryWrapper<ParkOrder> queryWrapper1 = new LambdaQueryWrapper<>();
                    queryWrapper1.eq(ParkOrder::getOrderNo, parkDurationPayDTO.getOrderNo());
                    parkOrderService.remove(queryWrapper1);
                }
                String outTradeNo = String.valueOf(System.currentTimeMillis());
                wechatOpen.setOutTradeNo(outTradeNo);
                if (StringUtils.equals(payOrderResult.get("code"), "200")) {
                    //删除订单表数据
                    return AjaxResult.error("当前订单已支付请勿重复支付");
                }

            }

            StaffProperty staffProperty = staffPropertyService.getById(parkDurationPayDTO.getStaffPropertyId());
            //租赁
            if (StringUtils.equals(staffProperty.getLeasePurchase(), "0")) {
                if (StringUtils.equals(parkDurationPayDTO.getIsRenew(), "0")) {
                    if (staffProperty.getEndTime() != null) {
                        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                        Long dayCount = DateUtils.getDayCount(sim.parse(DateUtils.getDate()), staffProperty.getEndTime());
                        if (dayCount <= 0) {
                            System.out.println("可以缴费");

                        } else {
                            return AjaxResult.error("当前车牌号【" + staffProperty.getPlateNo() + "】已存在缴费,车位到期时间剩余时长" + dayCount + "天");
                        }
                    }
                }
            } else {
                //判断车位是否已购买
                Integer carportCount = carportNoService.getByCarportNoCount(staffProperty.getVehicleNo());
                if (carportCount <= 0) {
                    return AjaxResult.error("当前车位已购买请勿支付");
                }
            }


            Map<String, Object> map = WechatPayUtils.payment(wechatOpen);
            log.error(map.toString());

            ParkOrder parkOrder = new ParkOrder();
            parkOrder.setOrderNo(wechatOpen.getOutTradeNo());
            parkOrder.setStaffPropertyId(parkDurationPayDTO.getStaffPropertyId());
            parkOrder.setFee(wechatOpen.getTotalAmount());
            parkOrder.setStatus("0");
            if (parkDurationPayDTO.getYearMonth() != null) {
                parkOrder.setYearMonth(parkDurationPayDTO.getYearMonth());
            }
            if (StringUtils.isNotEmpty(parkDurationPayDTO.getPurchasePackage())) {
                parkOrder.setPurchasePackage(parkDurationPayDTO.getPurchasePackage());
            }
            if (StringUtils.isNotEmpty(parkDurationPayDTO.getLeasePurchase())) {
                parkOrder.setLeasePurchase(parkDurationPayDTO.getLeasePurchase());
            }
            if (parkDurationPayDTO.getEndDate() != null) {
                parkOrder.setEndTime(parkDurationPayDTO.getEndDate());
            }
            parkOrder.setIsRenew(parkDurationPayDTO.getIsRenew());
            parkOrder.setUpdateTime(new Date());
            parkOrder.setCreateTime(new Date());
            LambdaQueryWrapper<ParkOrder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ParkOrder::getStaffPropertyId, parkDurationPayDTO.getStaffPropertyId());
            queryWrapper.eq(ParkOrder::getStatus, "0");
            ParkOrder parkOrder1 = parkOrderService.getOne(queryWrapper);
            if (parkOrder1 != null) {
                parkOrder.setId(parkOrder1.getId());

                parkOrderService.updateById(parkOrder);
            } else {

                parkOrderService.add(parkOrder);
            }

            String key = CacheConstants.PARK_ORDER + wechatOpen.getOutTradeNo();
            redisService.setCacheObject(key, parkDurationPayDTO, 10L, TimeUnit.MINUTES);
            return AjaxResult.success(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param parkDurationPayDTO
     * @param communityWechat
     * @return
     */
    private WechatOpen packageWechatPay(ParkDurationPayDTO parkDurationPayDTO, CommunityWechat communityWechat) {
        WechatOpen wechatOpen = new WechatOpen();
        wechatOpen.setMethod(PayConstants.JSAPI);
        wechatOpen.setDescription(PayConstants.SPACE_PARK);
        String outTradeNo = String.valueOf(System.currentTimeMillis());
        wechatOpen.setOutTradeNo(outTradeNo);
        wechatOpen.setTotalAmount(parkDurationPayDTO.getFee());
        wechatOpen.setTimeExpire(PaymentUtil.wechatTimeExpire(5));
        wechatOpen.setOpenId(parkDurationPayDTO.getOpenId());
        //回调地址
        String estateServerApp = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "ESTATE_SERVER_APP");
        String notifyUrl = estateServerApp + "/v2/parkDuration/wechat_notify";
        wechatOpen.setNotifyUrl(notifyUrl);
        wechatOpen.setWechatId(communityWechat.getWechatId());
        wechatOpen.setAppId(communityWechat.getAppId());
        wechatOpen.setMchId(communityWechat.getMchId());
        wechatOpen.setSerialNumber(communityWechat.getSerialNumber());
        wechatOpen.setApiKey(communityWechat.getApiKey());
        wechatOpen.setPrivateKeyUrl(communityWechat.getPrivateUrl());
        return wechatOpen;
    }

    /**
     * 计算费用
     *
     * @param parkDurationPayDTO
     * @return
     */
    private void getFee(ParkDurationPayDTO parkDurationPayDTO) {
        ParkDurationEntity durationEntity = parkDurationService.getById(parkDurationPayDTO.getParkDurationId());
        BigDecimal hourFee = durationEntity.getHourFee();
        if (parkDurationPayDTO.getYearMonth() == null) {
            parkDurationPayDTO.setFee(hourFee);
        } else {
            parkDurationPayDTO.setFee(hourFee.multiply(new BigDecimal(parkDurationPayDTO.getYearMonth())));
        }

    }


    @PostMapping("wechat_notify")
    public String notify(@RequestBody WeChatPayResultIn weChatPayResultIn) {

        try {
            log.info("********** START物业车位缴费微信支付回调执行 *************");

            List<CommunityWechat> wechatList = payService.selectWechatById(null, null);
            for (CommunityWechat wechat : wechatList) {
                JSONObject jsonObject = WechatPayCallBackUtils.wechatPayCallBack(wechat.getApiKey(), weChatPayResultIn);
                log.info("微信支付回调参数:{}", jsonObject);
                parkDurationService.updateWechatPay(jsonObject);
            }

            log.info("********** END物业车位缴费微信支付回调执行 *************");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "SUCCESS";
    }


    @PostMapping("/test")
    public AjaxResult test(HttpServletRequest request, @RequestBody String jsonBody) {

        try {
            String text = "{\"mchid\":\"1613501627\",\"appid\":\"wx4b8eca064618b374\",\"out_trade_no\":\"1711872830666\",\"transaction_id\":\"4200002213202403312517355898\",\"trade_type\":\"JSAPI\",\"trade_state\":\"SUCCESS\",\"trade_state_desc\":\"支付成功\",\"bank_type\":\"OTHERS\",\"attach\":\"\",\"success_time\":\"2024-03-31T16:14:01+08:00\",\"payer\":{\"openid\":\"oOMPw5HQiF_QWjxekLQpd5xELssA\"},\"amount\":{\"total\":1,\"payer_total\":1,\"currency\":\"CNY\",\"payer_currency\":\"CNY\"}}\n";
            JSONObject jsonObject = JSONObject.parseObject(jsonBody);
            parkDurationService.updateWechatPay(jsonObject);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
