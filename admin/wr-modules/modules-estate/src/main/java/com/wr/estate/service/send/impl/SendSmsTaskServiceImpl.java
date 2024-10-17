package com.wr.estate.service.send.impl;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sms.SendMsgRequest;
import com.wr.common.core.utils.sms.SendSmsUtils;
import com.wr.common.security.utils.DictUtils;
import com.wr.estate.service.info.NoticeEstateService;
import com.wr.estate.service.manage.contract.OwnerService;
import com.wr.estate.service.send.SendSmsRecordService;
import com.wr.estate.service.send.SendSmsTaskService;
import com.wr.remote.domain.NoticeEstate;
import com.wr.remote.domain.SendSmsRecord;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.manage.vo.contract.SendOwnerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/3/1 15:28
 */
@Service
@Slf4j
public class SendSmsTaskServiceImpl implements SendSmsTaskService {


    @Autowired
    private OwnerService ownerService;

    @Autowired
    private SendSmsRecordService sendSmsRecordService;

    @Autowired
    private NoticeEstateService noticeEstateService;


    @Override
    public void sendBirthdayMessages() {
        final int[] count = {0};
        List<SendOwnerVo> sendOwnerList = ownerService.getAllOwnerInfo();
        List<NoticeEstate> noticeEstateList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(sendOwnerList)) {
            sendOwnerList.stream().forEach(sendOwnerVo -> {
                SendSmsRecord sendSmsRecord = new SendSmsRecord();
                sendSmsRecord.setSendSmsId(sendOwnerVo.getSendSmsId());
                sendSmsRecord.setPhone(sendOwnerVo.getOwnerPhone());
                sendSmsRecord.setUserId(sendOwnerVo.getAccountId());
                sendSmsRecord.setUserType("1");
                //校验身份证号是否正确
                String cardNo = sendOwnerVo.getCardNo();
                String birthday = "";
                if (cardNo.length() == 18) {
                    String year = (String) cardNo.subSequence(6, 10);
                    String month = (String) cardNo.subSequence(10, 12);
                    String day = (String) cardNo.subSequence(12, 14);
                    birthday = month + "-" + day;

                } else if (cardNo.length() == 15) {
                    String year = "19" + cardNo.substring(6, 8);// 年份
                    String month = cardNo.substring(8, 10);// 月份
                    String day = cardNo.substring(10, 12);// 得到日
                    birthday = month + "-" + day;
                } else {
                    birthday = "00-00";
                }
                String date = DateUtils.getDate();
                date = date.substring(5, date.length());
                if (StringUtils.equals(birthday, date)) {
                    count[0] = count[0] + 1;
                    log.info("业主{}今日{}过生日", sendOwnerVo.getOwnerName(), date);
                    try {
                        //发送短信
                        SendMsgRequest sendMsgRequest = new SendMsgRequest();
                        sendMsgRequest.setAccessKeyId(sendOwnerVo.getAccessKeyId());
                        sendMsgRequest.setAccessKeySecret(sendOwnerVo.getAccessKeySecret());
                        sendMsgRequest.setSignName(sendOwnerVo.getSignName());
                        sendMsgRequest.setPhoneNumber(sendOwnerVo.getOwnerPhone());
                        sendMsgRequest.setTemplateCode(sendOwnerVo.getTemplateCode());
                        String templateParam = "{\"name\":" + sendOwnerVo.getOwnerName() + "}";
                        sendMsgRequest.setTemplateParam(templateParam);
                        // String msg = SendSmsUtils.sendMsg(sendMsgRequest);

                        //JSONObject jsBody = JSON.parseObject(msg);
                        // String code = jsBody.get("code").toString();
                        //if (StringUtils.equals("OK", code)) {
                        //待发送
                        sendSmsRecord.setStatus("0");
                        // } else {
                        //     sendSmsRecord.setStatus("1");
                        //失败
                        //    String message = jsBody.get("message").toString();
                        //   sendSmsRecord.setErrorMsg(message);
                        // }

                    } catch (Exception e) {
                        e.printStackTrace();
//                        sendSmsRecord.setStatus("1");
//                        //失败
//                        sendSmsRecord.setErrorMsg(e.getMessage());
                    }
                    sendSmsRecord.setCreateBy("");
                    sendSmsRecord.setCreateTime(new Date());
                    sendSmsRecord.setDelFlag("0");
                    sendSmsRecordService.save(sendSmsRecord);

                    NoticeEstate noticeEstate = new NoticeEstate();
                    noticeEstate.setEstateId(sendOwnerVo.getEstateId());
                    noticeEstate.setCommunityId(sendOwnerVo.getCommunityId());
                    noticeEstate.setNoticeTitle(sendOwnerVo.getOwnerName() + "过生日");
                    noticeEstate.setReadStatus("0");
                    noticeEstate.setFlag("0");
                    noticeEstateList.add(noticeEstate);
                }
            });
        }
        if (CollectionUtils.isNotEmpty(noticeEstateList)) {
            Map<Long, List<NoticeEstate>> listMap = noticeEstateList.stream().collect(Collectors.groupingBy(m -> m.getEstateId()));
            for (Long key : listMap.keySet()) {
                List<NoticeEstate> noticeEstateList1 = listMap.get(key);
                NoticeEstate noticeEstate = new NoticeEstate();
                noticeEstate.setEstateId(key);
                noticeEstate.setReadStatus("0");
                noticeEstate.setFlag("0");
                noticeEstate.setNoticeTitle(DateUtils.getDate() + "过生日人数" + noticeEstateList1.size() + "人");
                noticeEstateService.save(noticeEstate);
            }

        }
        log.info("=======================END执行发送生日祝福短信定时任务=======================");
    }


    /**
     * 节假日短信
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2500))
    @Override
    public void sendFestivalMessages() throws Exception {
        String festivalName = "";
        try {
            log.info("获取节假日API...");
            String date = DateUtils.getDate();
            String url = "https://timor.tech/api/holiday/info/" + date;
            String responseDate = HttpUtil.get(url);
            System.out.println(responseDate);
            JSONObject jsonObject = JSON.parseObject(responseDate);
            String code = String.valueOf(jsonObject.get("code"));
            if (code.equals("0")) {
                String type = String.valueOf(jsonObject.get("type"));
                JSONObject jsonType = JSON.parseObject(type);
                String name = String.valueOf(jsonType.get("name"));
                log.info("今日是:{}", name);
                List<SysDictData> dictCache = DictUtils.getDictCache("send_festival");
                List<SysDictData> dictData = dictCache.stream().filter(sd -> sd.getDictLabel().equals(name)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(dictData)) {
                    festivalName = name;
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        //发送节假日短信
        if (StringUtils.isNotEmpty(festivalName)) {
            List<NoticeEstate> noticeEstateList = Lists.newArrayList();
            List<SendSmsRecord> sendSmsRecordList = Lists.newArrayList();
            List<SendOwnerVo> ownerEstateAllList = ownerService.getOwnerEstateAllList();
            if (CollectionUtils.isNotEmpty(ownerEstateAllList)) {

                String finalFestivalName = festivalName;
                ownerEstateAllList.stream().forEach(sendOwnerVo -> {
                    SendSmsRecord sendSmsRecord = new SendSmsRecord();
                    try {
                        sendSmsRecord.setSendSmsId(sendOwnerVo.getSendSmsId());
                        sendSmsRecord.setPhone(sendOwnerVo.getOwnerPhone());
                        sendSmsRecord.setUserId(sendOwnerVo.getAccountId());
                        sendSmsRecord.setUserType(sendOwnerVo.getUserType());
                        sendSmsRecord.setFestivalName(finalFestivalName);

                        //发送短信
                        SendMsgRequest sendMsgRequest = new SendMsgRequest();
                        sendMsgRequest.setAccessKeyId(sendOwnerVo.getAccessKeyId());
                        sendMsgRequest.setAccessKeySecret(sendOwnerVo.getAccessKeySecret());
                        sendMsgRequest.setSignName(sendOwnerVo.getSignName());
                        sendMsgRequest.setPhoneNumber(sendOwnerVo.getOwnerPhone());
                        sendMsgRequest.setTemplateCode(sendOwnerVo.getTemplateCode());
                        String templateParam = "{\"name\":" + sendOwnerVo.getOwnerName() + "\"jrName\":" + sendSmsRecord.getFestivalName() + "}";
                        sendMsgRequest.setTemplateParam(templateParam);
                        //String msg = SendSmsUtils.sendMsg(sendMsgRequest);

                        //JSONObject jsBody = JSON.parseObject(msg);
                        //String code = jsBody.get("code").toString();
                        // if (StringUtils.equals("OK", code)) {
                        //待发送发送
                        sendSmsRecord.setStatus("0");
                        // } else {
                        //    sendSmsRecord.setStatus("1");
                        //失败
                        //    String message = jsBody.get("message").toString();
                        //    sendSmsRecord.setErrorMsg(message);
                        //}

                    } catch (Exception e) {
                        e.printStackTrace();
//                        sendSmsRecord.setStatus("1");
//                        //失败
//                        sendSmsRecord.setErrorMsg(e.getMessage());
                    }
                    sendSmsRecord.setCreateBy("");
                    sendSmsRecord.setCreateTime(new Date());
                    sendSmsRecord.setDelFlag("0");
                    sendSmsRecordList.add(sendSmsRecord);

                    NoticeEstate noticeEstate = new NoticeEstate();
                    noticeEstate.setEstateId(sendOwnerVo.getEstateId());
                    noticeEstate.setReadStatus("0");
                    noticeEstate.setFlag("0");
                    noticeEstate.setNoticeTitle(sendSmsRecord.getFestivalName());
                    noticeEstateList.add(noticeEstate);


                });
            }

            //根据当年和节假日名称查询是否存在
            Integer count = sendSmsRecordService.getByYearFestivalNameCount(festivalName);
            if (count <= 0) {
                if (CollectionUtils.isNotEmpty(sendSmsRecordList)) {
                    sendSmsRecordList.stream().forEach(sendSmsRecord -> {
                        sendSmsRecordService.save(sendSmsRecord);
                    });
                }

                if (CollectionUtils.isNotEmpty(noticeEstateList)) {
                    Map<Long, List<NoticeEstate>> listMap = noticeEstateList.stream().collect(Collectors.groupingBy(m -> m.getEstateId()));
                    for (Long key : listMap.keySet()) {
                        NoticeEstate noticeEstate = new NoticeEstate();
                        noticeEstate.setEstateId(key);
                        noticeEstate.setReadStatus("0");
                        noticeEstate.setFlag("0");
                        noticeEstate.setNoticeTitle("今日" + DateUtils.getDate() + festivalName);
                        noticeEstateService.save(noticeEstate);
                    }
                }
            }

        }

    }

    public static void main(String[] args) {
        List<SysDictData> dictCache = Lists.newArrayList();
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictValue("元旦");
        sysDictData.setDictLabel("元旦");
        dictCache.add(sysDictData);

        String responseData = "{\"code\":0,\"type\":{\"type\":2,\"name\":\"劳动节\",\"week\":3},\"holiday\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":3,\"date\":\"2024-05-01\",\"rest\":24}}";

    }


    @Recover
    public String recover(Exception e) {
        System.out.println("service retry after Recover => " + e.getMessage());
        return "error";
    }

}
