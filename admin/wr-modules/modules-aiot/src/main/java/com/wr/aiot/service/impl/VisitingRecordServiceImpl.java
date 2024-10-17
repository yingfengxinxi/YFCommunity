package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.VisitingRecordMapper;
import com.wr.aiot.service.VisitingRecordService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.VisitingRecord;
import com.wr.remote.aiot.VisitingSecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/10 16:48
 */
@Service
@Slf4j
public class VisitingRecordServiceImpl extends ServiceImpl<VisitingRecordMapper, VisitingRecord> implements VisitingRecordService {


    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 同步数据
     *
     * @param type
     * @throws Exception
     */
    @Override
    public void visitingRecord(String type) throws Exception {
        List<CommunityHik> hik = communityMapper.selectHikByCommunityIdAllList();
        if (CollectionUtils.isNotEmpty(hik)) {
            hik.stream().forEach(communityHik -> {
                try {
                    String renewalBy = SecurityUtils.getUsername();
                    int pageNo = 1;
                    int pageSize = 1000;
                    String startTime = "";
                    String entTime = "";
                    if (type.equals("1")) {
                        startTime = DateUtils.getDate() + "T00:00:00+08:00";
                        entTime = DateUtils.getDate() + "T23:59:59+08:00";
                    }
                    Integer total = HaiKUtils.visitingRecordsTotal(communityHik, startTime, entTime);
                    pageNo = (int) Math.ceil((double) total / pageSize);
                    for (int i = 0; i < pageNo; i++) {
                        JSONArray dataList = HaiKUtils.visitingRecords(communityHik, (i + 1), pageSize, startTime, entTime);

                        log.info("访客数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());

                        for (int j = 0; j < dataList.size(); j++) {
                            JSONObject jsonObject = dataList.getJSONObject(j);
                            if (jsonObject != null) {
                                extracted(jsonObject);
                                VisitingRecord visitingRecord = JSONObject.parseObject(jsonObject.toJSONString(), VisitingRecord.class);
                                visitingRecord.setCommunityId(communityHik.getCommunityId());
                                JSONArray jsonArray = HaiKUtils.personInfo(communityHik, visitingRecord.getReceptionistId());
                                if (CollectionUtils.isNotEmpty(jsonArray)) {
                                    JSONObject personJson = (JSONObject) jsonArray.get(0);
                                    //证件类型
                                    String certificateType = String.valueOf(personJson.get("certificateType"));
                                    visitingRecord.setReceptionistCertificateType(certificateType);
                                    //证件号
                                    String certificateNo = String.valueOf(personJson.get("certificateNo"));
                                    visitingRecord.setReceptionistCertificateNo(certificateNo);

                                    String phoneNo = String.valueOf(personJson.get("phoneNo"));
                                    if (StringUtils.equals(phoneNo, "null")) {
                                        phoneNo = "";
                                    }
                                    visitingRecord.setReceptionistPhoneNo(phoneNo);
                                    String gender = String.valueOf(personJson.get("gender"));
                                    visitingRecord.setReceptionistGender(gender);
                                }
                                LambdaQueryWrapper<VisitingRecord> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(VisitingRecord::getOrderId, visitingRecord.getOrderId());
                                VisitingRecord visiting = baseMapper.selectOne(queryWrapper);
                                if (visiting != null) {
                                    // visitingSecord.setId(doorEvent.getId());
                                    // visitingSecord.setUpdateTime(new Date());
                                    // visitingSecord.setUpdateBy(renewalBy);
                                    // baseMapper.updateById(visitingSecord);
                                } else {
                                    visitingRecord.setId(visitingRecord.getOrderId());
                                    visitingRecord.setCreateTime(new Date());
                                    visitingRecord.setCreateBy(renewalBy);
                                    baseMapper.insert(visitingRecord);
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

    private static void extracted (JSONObject jsonObject){

        String visitStartTime = String.valueOf(jsonObject.get("visitStartTime"));
        visitStartTime = visitStartTime.replace("T", " ").replace("+08:00", "");
        visitStartTime = visitStartTime.substring(0, 19);
        jsonObject.put("visitStartTime", visitStartTime);
        String visitEndTime = String.valueOf(jsonObject.get("visitEndTime"));
        if(StringUtils.isNotEmpty(visitEndTime) && !StringUtils.equals(visitEndTime,"null")){
            visitEndTime = visitEndTime.replace("T", " ").replace("+08:00", "");
            visitEndTime = visitEndTime.substring(0, 19);
            jsonObject.put("visitEndTime", visitEndTime);
        }
        String plannedStartTime = String.valueOf(jsonObject.get("plannedStartTime"));
        plannedStartTime = plannedStartTime.replace("T", " ").replace("+08:00", "");
        plannedStartTime = plannedStartTime.substring(0, 19);
        jsonObject.put("plannedStartTime", plannedStartTime);
        String plannedEndTime = String.valueOf(jsonObject.get("plannedEndTime"));
        plannedEndTime = plannedEndTime.replace("T", " ").replace("+08:00", "");
        plannedEndTime = plannedEndTime.substring(0, 19);
        jsonObject.put("plannedEndTime", plannedEndTime);
    }
}
