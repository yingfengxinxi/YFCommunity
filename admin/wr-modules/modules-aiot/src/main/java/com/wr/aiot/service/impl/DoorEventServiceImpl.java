package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.DoorEventMapper;
import com.wr.aiot.service.DoorEventService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.domain.vo.DoorEventVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:18
 */
@Slf4j
@Service
public class DoorEventServiceImpl extends ServiceImpl<DoorEventMapper, DoorEvent> implements DoorEventService {

    @Autowired
    private CommunityMapper communityMapper;

    /**
     * @param id
     * @return
     */
    @Override
    public DoorEventVo getByIdEventInfo(String id) {
        return baseMapper.getByIdEventInfo(id);
    }

    /**
     * 【刷卡和刷码都是合法卡比对通过[198914]，刷脸人脸认证通过[196893]】
     *
     * @param type
     * @param startTime
     * @param endTime
     * @throws Exception
     */
    @Override
    public void doorEvents(String type, String startTime, String endTime) throws Exception {
        List<CommunityHik> hik = communityMapper.selectHikByCommunityIdAllList();
        if (CollectionUtils.isNotEmpty(hik)) {
            if (type.equals("1")) {
                startTime = DateUtils.getDate() + "T00:00:00+08:00";
                endTime = DateUtils.getDate() + "T23:59:59+08:00";
            }else{
                startTime = startTime.replace(" ", "T") + "+08:00";
                endTime = endTime.replace(" ", "T") + "+08:00";
            }
            String finalStartTime = startTime;
            String finalEndTime = endTime;
            hik.stream().forEach(communityHik -> {

                try {
                    String renewalBy = SecurityUtils.getUsername();
                    int pageNo = 1;
                    int pageSize=1000;
                    Integer total = HaiKUtils.doorEventsTotal(communityHik, finalStartTime, finalEndTime);
                    pageNo = (int) Math.ceil((double) total / pageSize);
                    for (int i = 0; i < pageNo; i++) {
                        JSONArray dataList = HaiKUtils.doorEvents(communityHik, (i + 1), pageSize, finalStartTime, finalEndTime);

                        log.info("门禁数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());

                        for (int j = 0; j < dataList.size(); j++) {
                            JSONObject jsonObject = dataList.getJSONObject(j);
                            if (jsonObject != null) {
                                extracted(jsonObject);
                                DoorEvent doorEvent = JSONObject.parseObject(jsonObject.toJSONString(), DoorEvent.class);
                                LambdaQueryWrapper<DoorEvent> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(DoorEvent::getEventId, doorEvent.getEventId());
                                DoorEvent event = baseMapper.selectOne(queryWrapper);
                                if (event != null) {
                                    //doorEvent.setId(event.getId());
                                    //doorEvent.setUpdateTime(new Date());
                                    //doorEvent.setUpdateBy(renewalBy);
                                    //baseMapper.updateById(doorEvent);
                                } else {
                                    doorEvent.setId(doorEvent.getEventId());
                                    doorEvent.setCreateTime(new Date());
                                    doorEvent.setCreateBy(renewalBy);
                                    baseMapper.insert(doorEvent);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static void extracted(JSONObject jsonObject) {
        String receiveTime = String.valueOf(jsonObject.get("receiveTime"));
        receiveTime = receiveTime.replace("T", " ").replace("+08:00", "");
        receiveTime = receiveTime.substring(0, 19);
        jsonObject.put("receiveTime", receiveTime);
        String eventTime = String.valueOf(jsonObject.get("eventTime"));
        eventTime = eventTime.replace("T", " ").replace("+08:00", "");
        eventTime = eventTime.substring(0, 19);
        jsonObject.put("eventTime", eventTime);
    }
}
