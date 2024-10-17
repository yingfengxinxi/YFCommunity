package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.VisualSecordMapper;
import com.wr.aiot.service.VisualSecordService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.VisitingRecord;
import com.wr.remote.aiot.VisualSecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/14 23:20
 */
@Service
@Slf4j
public class VisualSecordServiceImpl extends ServiceImpl<VisualSecordMapper, VisualSecord> implements VisualSecordService {


    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 同步数据
     *
     * @param type
     * @throws Exception
     */
    @Override
    public void visualSecord(String type, String startTime, String endTime) throws Exception {
        List<CommunityHik> hik = communityMapper.selectHikByCommunityIdAllList();
        if (CollectionUtils.isNotEmpty(hik)) {
            if (type.equals("1")) {
                startTime = DateUtils.getDate() + "T00:00:00+08:00";
                endTime = DateUtils.getDate() + "T23:59:59+08:00";
            } else {
                startTime = startTime.replace(" ", "T") + "+08:00";
                endTime = endTime.replace(" ", "T") + "+08:00";
            }
            String finalStartTime = startTime;
            String finalEndTime = endTime;
            hik.stream().forEach(communityHik -> {
                try {
                    String renewalBy = SecurityUtils.getUsername();
                    int pageNo = 1;
                    int pageSize = 1000;
                    Integer total = HaiKUtils.accessEventTotal(communityHik, finalStartTime, finalEndTime);
                    pageNo = (int) Math.ceil((double) total / pageSize);
                    for (int i = 0; i < pageNo; i++) {
                        JSONArray dataList = HaiKUtils.accessEvent(communityHik, (i + 1), pageSize, finalStartTime, finalEndTime);
                        log.info("可视对讲设备数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());
                        for (int j = 0; j < dataList.size(); j++) {
                            JSONObject jsonObject = dataList.getJSONObject(j);
                            if (jsonObject != null) {
                                extracted(jsonObject);
                                VisualSecord visualSecord = JSONObject.parseObject(jsonObject.toJSONString(), VisualSecord.class);
                                visualSecord.setCommunityId(communityHik.getCommunityId());
                                JSONArray jsonArray = HaiKUtils.personInfo(communityHik, visualSecord.getPersonId());
                                if (CollectionUtils.isNotEmpty(jsonArray)) {
                                    JSONObject personJson = (JSONObject) jsonArray.get(0);
                                    //证件类型
                                    String certificateType = String.valueOf(personJson.get("certificateType"));
                                    visualSecord.setCertificateType(certificateType);
                                    //证件号
                                    String certificateNo = String.valueOf(personJson.get("certificateNo"));
                                    visualSecord.setCertificateNo(certificateNo);

                                    String phoneNo = String.valueOf(personJson.get("phoneNo"));
                                    if (StringUtils.equals(phoneNo, "null")) {
                                        phoneNo = "";
                                    }
                                    visualSecord.setPhoneNo(phoneNo);
                                    String gender = String.valueOf(personJson.get("gender"));
                                    visualSecord.setGender(gender);
                                }
                                LambdaQueryWrapper<VisualSecord> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(VisualSecord::getEventId, visualSecord.getEventId());
                                queryWrapper.eq(VisualSecord::getCommunityId, visualSecord.getCommunityId());
                                VisualSecord visualSecord1 = baseMapper.selectOne(queryWrapper);
                                if (visualSecord1 != null) {

                                } else {
                                    visualSecord.setCreateTime(new Date());
                                    visualSecord.setCreateBy(renewalBy);
                                    visualSecord.setDelFlag("0");
                                    baseMapper.insert(visualSecord);
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
        String eventTime = String.valueOf(jsonObject.get("eventTime"));
        eventTime = eventTime.replace("T", " ").replace("+08:00", "");
        eventTime = eventTime.substring(0, 19);
        jsonObject.put("eventTime", eventTime);
    }
}
