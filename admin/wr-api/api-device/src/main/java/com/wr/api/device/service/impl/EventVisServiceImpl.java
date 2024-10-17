package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.mapper.CommunityMapper;
import com.wr.api.device.mapper.EventVisMapper;
import com.wr.api.device.mapper.WarnEventMapper;
import com.wr.api.device.service.EventService;
import com.wr.api.device.util.HikEventUtil;
import com.wr.common.customize.constant.HikConstants;
import com.wr.remote.aiot.VisData;
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
 * @Date: 2021-12-17 14:02:45
 * @Desc: 可视对讲业务处理层
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventVisServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventVisServiceImpl.class);

    private final EventVisMapper eventVisMapper;
    private final WarnEventMapper warnEventMapper;
    private final CommunityMapper communityMapper;

    @Override
    public Boolean support(Integer eventType) {

        return Arrays.asList(HikConstants.EVENT_VIS).contains(eventType);
    }

    @Override
    @Transactional(readOnly = false)
    public void execute(String sendTime, JSONObject event, JSONObject detail) {
        List<VisData> list = Lists.newArrayList();
        //添加联动事件主体数据
        String indexCode = detail.getString("srcIndex");
        Long communityId = eventVisMapper.selectCommunityIdByIndexCode(indexCode);
        WarnEvent warnEvent  = HikEventUtil.packageWarnEvent(sendTime, event, detail, communityId);
        warnEventMapper.insertWarnEvent(warnEvent);
        Long warnId = warnEvent.getWarnId();
        //事件扩展信息
        JSONObject data = detail.getJSONObject("data");
        Assert.notNull(data, "门禁事件扩展信息为空");

        VisData visData = new VisData();
        visData.setWarnId(warnId);
        visData.setExtAccessChannel(data.getString("ExtAccessChannel"));
        visData.setExtEventCardNo(data.getString("ExtEventCardNo"));
        visData.setExtEventCode(data.getString("ExtEventCode"));
        visData.setExtEventDoorId(data.getString("ExtEventDoorID"));
        visData.setExtEventIdCardPictureUrl(data.getString("ExtEventIDCardPictureURL"));
        String userType = data.getString("UserType");
        String type = StringUtils.isEmpty(userType) ? "0" : userType;
        visData.setUserType(type);
        //人员信息
        JSONObject cardInfo = data.getJSONObject("ExtEventIdentityCardInfo");
        if(null != cardInfo){
            String idNum = cardInfo.getString("IdNum");
            Optional.ofNullable(idNum).ifPresent(visData::setIdNum);

            String name = cardInfo.getString("Name");
            Optional.ofNullable(name).ifPresent(visData::setUserName);

            String sex = cardInfo.getString("Sex");
            String gender = StringUtils.isEmpty(sex) ? "-1" : sex;
            visData.setUserSex(gender);
        }
        visData.setExtEventInOut(data.getString("ExtEventInOut"));
        visData.setExtEventPersonNo(data.getString("ExtEventPersonNo"));
        String svrIndexCode = data.getString("svrIndexCode");
        visData.setSvrIndexCode(svrIndexCode);
        String pictureUrl = data.getString("ExtEventPictureURL");
        visData.setExtEventPictureUrl(pictureUrl);
        visData.setExtEventSwipNum(data.getString("ExtEventSwipNum"));
        visData.setExtReceiveTime(data.getString("ExtReceiveTime"));
        visData.setSeq(data.getString("Seq"));

        //通道事件信息
        JSONObject numInfo = data.getJSONObject("ExtEventCustomerNumInfo");
        if(null != numInfo){
            visData.setEntryTimes(String.valueOf(numInfo.getInteger("EntryTimes")));
            visData.setExitTimes(String.valueOf(numInfo.getInteger("ExitTimes")));
            visData.setTotalTimes(String.valueOf(numInfo.getInteger("TotalTimes")));
        }
        list.add(visData);
        eventVisMapper.batchInsertVisData(list);
    }

}
