package com.wr.api.estate.service.impl.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.entity.application.serve.VenueAppoint;
import com.wr.api.estate.entity.vo.application.serve.VenueAppointApiVo;
import com.wr.api.estate.entity.vo.application.serve.VenueVO;
import com.wr.api.estate.mapper.SensitiveMapper;
import com.wr.api.estate.mapper.application.serve.VenueMapper;
import com.wr.api.estate.service.PayService;
import com.wr.api.estate.service.application.serve.VenueService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:26:03
 * @Desc:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VenueServiceImpl implements VenueService {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    private final VenueMapper venueMapper;

    private final SensitiveMapper sensitiveMapper;
    private final PayService payService;

    @Override
    public List<VenueVO> searchByCommunityId(Long communityId, String state) {
        List<VenueVO> v = venueMapper.searchByCommunityId(communityId, state);
        v.forEach(e -> {
            //场馆预约人
            List<LinkedHashMap<String, Object>> list = venueMapper.searchTodayAppoint(e.getVenueId());
            list.forEach(e1 -> {
                e1.put("planStart", format(e1.get("planStart")));
                e1.put("planEnd", format(e1.get("planEnd")));
            });
            e.setAppointList(list);
        });
        v.sort((o1, o2) -> {

            if (o1.getVenueStatus().equals("0") && o2.getVenueStatus().equals("0")) {

                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
            return 0;
        });
        return v;
    }

    @Override
    public VenueVO detailById(Long venueId) {

        return venueMapper.detailById(venueId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> searchActByCommunityId(Long communityId) {
        return venueMapper.searchActByCommunityId(communityId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> searchRuleByCommunityId(Long communityId) {
        return venueMapper.searchRuleByCommunityId(communityId);
    }

    @Override
    public AjaxResult save(VenueAppoint venueAppoint) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.dateTime(DATE_FORMAT, venueAppoint.getPlanStart()));
        String startDate = calendar.get(Calendar.YEAR) + "-" + getNum((calendar.get(Calendar.MONTH) + 1)) + "-" + getNum(calendar.get(Calendar.DAY_OF_MONTH));
        String startTime = getNum(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                getNum(calendar.get(Calendar.MINUTE)) + ":" + getNum(calendar.get(Calendar.SECOND));

        calendar.setTime(DateUtils.dateTime(DATE_FORMAT, venueAppoint.getPlanEnd()));
        String endDate = calendar.get(Calendar.YEAR) + "-" + getNum((calendar.get(Calendar.MONTH) + 1)) + "-" + getNum(calendar.get(Calendar.DAY_OF_MONTH));
        /**
         * 开始时间不能小于已预约人的结束时间
         */
        if (
                venueMapper.countPlanStart(venueAppoint.getCommunityId(), venueAppoint.getVenueId(), startDate, startTime) > 0 ||
                        venueMapper.dateValid(venueAppoint.getCommunityId(), venueAppoint.getVenueId(), startDate) > 0 ||
                        venueMapper.dateValidCount(venueAppoint.getCommunityId(), venueAppoint.getVenueId(), startDate, endDate) > 0
        ) {

            return AjaxResult.error("该时间段存在预约，请重新选择预约时间");
        }
        venueAppoint.setCreateTime(new Date(System.currentTimeMillis()));
        venueAppoint.setUpdateTime(new Date(System.currentTimeMillis()));
        venueAppoint.setCreateBy(venueAppoint.getCreateBy());
        venueAppoint.setDeposit(0.0);
        venueAppoint.setAppointStatus("0");
        venueAppoint.setPayAmount(0.0);
        venueAppoint.setUpdateBy(venueAppoint.getCreateBy());
        // save
        venueMapper.save(SensitiveUtil.sensitiveFilter(venueAppoint, sensitiveMapper.selectSensitiveList()));

        return AjaxResult.success("预约成功");
    }

    @Override
    public List<LinkedHashMap<String, Object>> searchAppoint(Long venueId, String time) {
        List<LinkedHashMap<String, Object>> list = venueMapper.searchAppoint(venueId, time);
        list.forEach(e -> {
            e.put("planStart", format(e.get("planStart")));
            e.put("planEnd", format(e.get("planEnd")));
        });
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAliPay(Map<String, String> params, JSONObject attach) {

        Long alipayId = attach.getLong(PayConstants.ALIPAY_ID);
        // 根据 communityId 到数据库中查询 publicKey
        CommunityAli communityAli = payService.selectAliById(alipayId);
        if (null == communityAli) {
            log.error("支付宝回调失败，未查询到相关账号");
            return false;
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(communityAli.getPublicKey())) {
            log.error("支付宝回调失败，publicKey为空");
            return false;
        }
        try {
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

            int result = venueMapper.updatePay(attach.getLong("appointId"));
            if (result > 0) {
                return true;
            }
            return false;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝回调失败，code:{},msg:{}", e.getErrCode(), e.getErrMsg());
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWechatPay(HttpServletRequest request, JSONObject attach) {
        //微信返回的请求体
        String body = HttpClientUtil.getRequestBody(request);
        log.info("返回的请求体:{}", body);
        if (org.apache.commons.lang3.StringUtils.isEmpty(body)) {
            log.error("微信支付回调请求体为null");
            return false;
        }
        Long wechatId = attach.getLong(PayConstants.WECHAT_ID);
        //通过证书序列号查询
        List<CommunityWechat> wechatList = payService.selectWechatById(null, wechatId);
        if (CollectionUtils.isEmpty(wechatList)) {
            log.error("未配置支付账号");
            return false;
        }
        CommunityWechat wechat = wechatList.get(0);
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
        int result = venueMapper.updatePay(attach.getLong("appointId"));
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询场馆工单
     *
     * @param communityId
     * @return
     */
    @Override
    public List<VenueAppointApiVo> searchVenueAppoint(Long communityId) {
        List<VenueAppointApiVo> list = venueMapper.searchVenueAppoint(communityId);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPlanStart() != null) {
                //当前时间是否早于开始时间
                Calendar now = Calendar.getInstance();
                Calendar s = (Calendar) now.clone();
                Calendar e = (Calendar) now.clone();
                s.setTime(list.get(i).getPlanStart());
                e.setTime(list.get(i).getPlanEnd());
                // before 之前
                //after 之后
                if (now.before(e) && now.after(s)) {
                    //进行中
                    //进行中终止操作
                    if ("2".equals(list.get(i).getAppointStatus()) || "3".equals(list.get(i).getAppointStatus())) {
                        list.get(i).setStatusName("4");
                    } else {
                        list.get(i).setStatusName("0");
                    }
                } else if (now.before(s) && now.before(e)) {
                    //未开始
                    list.get(i).setStatusName("1");
                } else if (now.after(s) && now.after(e)) {
                    //已结束
                    list.get(i).setStatusName("2");
                } else {
                    list.get(i).setStatusName("3");
                }
            }
        }
        //使用装箱去除终止状态数据    stream +索引 去除
        //过滤单个条件可以，多个条件过虑不了
        IntStream.range(0, list.size()).filter(i -> "4".equals(list.get(i).getStatusName())).boxed().findFirst().map(integer -> list.remove((int) integer));
        return list;
    }

    /**
     * 查看场馆工单详情
     *
     * @param appointId
     * @return
     */
    @Override
    public VenueAppointApiVo detailVenueAppoint(Long appointId) {

        VenueAppointApiVo venueAppointApiVo = venueMapper.detailVenueAppoint(appointId);

        if (venueAppointApiVo.getPlanStart() != null) {
            //当前时间是否早于开始时间
            Calendar now = Calendar.getInstance();
            Calendar s = (Calendar) now.clone();
            Calendar e = (Calendar) now.clone();
            s.setTime(venueAppointApiVo.getPlanStart());
            e.setTime(venueAppointApiVo.getPlanEnd());
            // before 之前
            //after 之后
            if (now.before(e) && now.after(s)) {
                //进行中
                //进行中终止操作
                if ("2".equals(venueAppointApiVo.getAppointStatus()) || "3".equals(venueAppointApiVo.getAppointStatus())) {
                    venueAppointApiVo.setStatusName("4");
                } else {
                    venueAppointApiVo.setStatusName("0");
                }
            } else if (now.before(s) && now.before(e)) {
                //未开始
                venueAppointApiVo.setStatusName("1");
            } else if (now.after(s) && now.after(e)) {
                //已结束
                venueAppointApiVo.setStatusName("2");
            } else {
                venueAppointApiVo.setStatusName("3");
            }
        }

        return venueAppointApiVo;
    }

    /**
     * 场馆工单终止
     *
     * @param appointId
     * @return
     */
    @Override
    public int terVenueAppoint(Long appointId) {
        return venueMapper.terVenueAppoint(SecurityUtils.getUsername(), appointId);
    }

    private static String format(Object time) {

        return DateUtils.parseDateToStr(DATE_FORMAT, DateUtils.toDate((LocalDateTime) time));
    }

    /**
     * 字符填充
     *
     * @param i
     * @return
     */
    private static String getNum(int i) {

        return i < 10 ? "0" + i : "" + i;
    }
}
