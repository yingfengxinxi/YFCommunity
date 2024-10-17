package com.wr.aiot.callback.service.impl;

import com.alibaba.fastjson2.JSONObject;

import com.wr.aiot.callback.mapper.EventAcsMapper;
import com.wr.aiot.callback.service.EventService;
import com.wr.aiot.callback.util.HikEventUtil;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.WarnEventMapper;
import com.wr.common.customize.constant.HikConstants;
import com.wr.remote.aiot.AcsData;
import com.wr.remote.aiot.WarnEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2021-11-25 16:24:28
 * @Desc: 门禁事件业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventAcsServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(EventAcsServiceImpl.class);

    private final EventAcsMapper eventAcsMapper;
    private final WarnEventMapper warnEventMapper;


//
//    @Override
//    @Transactional(readOnly = false)
//    public void execute(JSONObject params, JSONObject event, JSONObject detail) {
//        List<AcsData> list = Lists.newArrayList();
//        //添加联动事件主体数据
//        String indexCode = detail.getString("srcIndex");
//        Long communityId = eventAcsMapper.selectCommunityIdByIndexCode(indexCode);
//        WarnEvent warnEvent  = HikEventUtil.packageWarnEvent(params, event, detail, communityId);
//        warnEventMapper.insertWarnEvent(warnEvent);
//        Long warnId = warnEvent.getWarnId();
//        //事件扩展信息
//        JSONObject data = detail.getJSONObject("data");
//        Assert.notNull(data, "门禁事件扩展信息为空");
//        AcsData acsData = new AcsData();
//        acsData.setWarnId(warnId);
//        String userType = data.getString("UserType");
//        String type = StringUtils.isEmpty(userType) ? "0" : userType;
//        acsData.setUserType(type);
//
//        //人员信息
//        JSONObject cardInfo = data.getJSONObject("ExtEventIdentityCardInfo");
//        if(null != cardInfo){
//            String idNum = cardInfo.getString("IdNum");
//            Optional.ofNullable(idNum).ifPresent(acsData::setIdNum);
//
//            String name = cardInfo.getString("Name");
//            Optional.ofNullable(name).ifPresent(acsData::setUserName);
//
//            Integer sex = cardInfo.getInteger("Sex");
//            int gender = null == sex ? -1 : sex;
//            acsData.setUserSex(gender);
//        }
//        acsData.setExtAccessChannel(data.getString("ExtAccessChannel"));
//        acsData.setExtEventCardNo(data.getString("ExtEventCardNo"));
//        acsData.setExtEventCaseId(data.getString("ExtEventCaseID"));
//        acsData.setExtEventCode(data.getString("ExtEventCode"));
//        acsData.setExtEventDoorId(data.getString("ExtEventDoorID"));
//        acsData.setExtEventIdCardPictureUrl(data.getString("ExtEventIDCardPictureURL"));
//        acsData.setExtEventInOut(data.getString("ExtEventInOut"));
//        acsData.setExtEventPersonNo(data.getString("ExtEventPersonNo"));
//
//        acsData.setExtEventSwipNum(data.getString("ExtEventSwipNum"));
//        acsData.setExtReceiveTime(data.getString("ExtReceiveTime"));
//        acsData.setSeq(data.getString("Seq"));
//        String svrIndexCode = data.getString("svrIndexCode");
//        acsData.setSvrIndexCode(svrIndexCode);
//        String pictureUrl = data.getString("ExtEventPictureURL");
//        acsData.setExtEventPictureUrl(pictureUrl);
//        //通道事件信息
//        JSONObject numInfo = data.getJSONObject("ExtEventCustomerNumInfo");
//        if(null != numInfo){
//            acsData.setEntryTimes(String.valueOf(numInfo.getInteger("EntryTimes")));
//            acsData.setExitTimes(String.valueOf(numInfo.getInteger("ExitTimes")));
//            acsData.setTotalTimes(String.valueOf(numInfo.getInteger("TotalTimes")));
//        }
//        list.add(acsData);
//        eventAcsMapper.batchInsertAcsData(list);
//    }

}
