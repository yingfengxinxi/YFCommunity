package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.CrossRecordMapper;
import com.wr.aiot.service.CrossRecordService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.CrossRecord;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.VisitingSecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/8 18:16
 */
@Slf4j
@Service
public class CrossRecordServiceImpl extends ServiceImpl<CrossRecordMapper, CrossRecord> implements CrossRecordService {

    @Autowired
    private CommunityMapper communityMapper;

    /**
     * @param type
     * @throws Exception
     */
    @Override
    public void crossRecord(String type) throws Exception {
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
//                        LambdaQueryWrapper<CrossRecord> queryWrapper = new LambdaQueryWrapper<>();
//                        queryWrapper.eq(CrossRecord::getCommunityId, communityHik.getCommunityId());
//                        queryWrapper.orderByDesc(CrossRecord::getCrossTime);
//                        CrossRecord crossRecord1 = baseMapper.selectList(queryWrapper).get(0);
//                        if (crossRecord1 == null) {
                        startTime = DateUtils.getDate() + "T00:00:00+08:00";
                        entTime = DateUtils.getDate() + "T23:59:59+08:00";
//                        } else {
//                            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date crossTime = crossRecord1.getCrossTime();
//                            String format = sim.format(crossTime);
//                            format = format.replace(" ", "T") + "+08:00";
//                            String date = DateUtils.getTime();
//                            date = date.replace(" ", "T") + "+08:00";
//
//                            startTime = format;
//                            entTime = date;
//                        }
                    }

                    Integer total = HaiKUtils.crossRecordsTotal(communityHik, startTime, entTime);
                    pageNo = (int) Math.ceil((double) total / pageSize);
                    for (int i = 0; i < pageNo; i++) {
                        JSONArray dataList = HaiKUtils.crossRecords(communityHik, (i + 1), pageSize, startTime, entTime);
                        log.info("车辆出行数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());
                        for (int j = 0; j < dataList.size(); j++) {
                            JSONObject jsonObject = dataList.getJSONObject(j);
                            if (jsonObject != null) {
                                extracted(jsonObject);
                                CrossRecord crossRecord = JSONObject.parseObject(jsonObject.toJSONString(), CrossRecord.class);
                                //根据车牌号查询车主信息
                                JSONArray dataVehicleList = HaiKUtils.vehicleList(communityHik, crossRecord.getPlateNo());
                                if (CollectionUtils.isNotEmpty(dataVehicleList)) {
                                    JSONObject json = (JSONObject) dataVehicleList.get(0);
                                    String personId = String.valueOf(json.get("personId"));
                                    crossRecord.setPersonId(personId);
                                    JSONArray jsonArray = HaiKUtils.personInfo(communityHik, personId);
                                    if (CollectionUtils.isNotEmpty(jsonArray)) {
                                        JSONObject personJson = (JSONObject) jsonArray.get(0);
                                        //证件类型
                                        String certificateType = String.valueOf(personJson.get("certificateType"));
                                        crossRecord.setCertificateType(certificateType);
                                        //证件号
                                        String certificateNo = String.valueOf(personJson.get("certificateNo"));
                                        crossRecord.setCertificateNo(certificateNo);
                                        String phoneNo = String.valueOf(personJson.get("phoneNo"));
                                        if (StringUtils.equals(phoneNo, "null")) {
                                            phoneNo = "";
                                        }
                                        crossRecord.setPhoneNo(phoneNo);
                                        String gender = String.valueOf(personJson.get("gender"));
                                        crossRecord.setGender(gender);
                                    }
                                    String personName = String.valueOf(json.get("personName"));
                                    if (StringUtils.equals(personName, "null")) {
                                        personName = "";
                                    }
                                    crossRecord.setPersonName(personName);
                                    String description = String.valueOf(json.get("description"));
                                    if (StringUtils.equals(description, "null")) {
                                        description = "";
                                    }
                                    crossRecord.setDescription(description);
                                }
                                crossRecord.setCommunityId(communityHik.getCommunityId());
                                LambdaQueryWrapper<CrossRecord> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(CrossRecord::getCommunityId, crossRecord.getCommunityId());
                                queryWrapper.eq(CrossRecord::getCrossRecordSyscode, crossRecord.getCrossRecordSyscode());
                                CrossRecord crossRecord1 = baseMapper.selectOne(queryWrapper);
                                if (crossRecord1 != null) {
                                    //crossRecord.setId(crossRecord1.getId());
                                    //crossRecord.setUpdateTime(new Date());
                                    //crossRecord.setUpdateBy(renewalBy);
                                    //baseMapper.updateById(crossRecord);
                                } else {
                                    crossRecord.setId(crossRecord.getCrossRecordSyscode());
                                    crossRecord.setCreateBy(renewalBy);
                                    crossRecord.setUpdateTime(new Date());
                                    baseMapper.insert(crossRecord);
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

        String crossTime = String.valueOf(jsonObject.get("crossTime"));
        crossTime = crossTime.replace("T", " ").replace("+08:00", "");
        crossTime = crossTime.substring(0, 19);
        jsonObject.put("crossTime", crossTime);
        String createTime = String.valueOf(jsonObject.get("createTime"));
        createTime = createTime.replace("T", " ").replace("+08:00", "");
        createTime = createTime.substring(0, 19);
        jsonObject.put("createTime", createTime);
    }

    public static void main(String[] args) {
        //startTime = DateUtils.getDate() + "T00:00:00+08:00";
        String crossTime = "2024-04-08 23:39:42";
        crossTime = crossTime.replace(" ", "T") + "+08:00";
        System.out.println(crossTime);
        String date = DateUtils.getTime();
        date = date.replace(" ", "T") + "+08:00";
        System.out.println(date);
    }
}
