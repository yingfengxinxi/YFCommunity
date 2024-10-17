package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.VisitingSecordMapper;
import com.wr.aiot.service.DoorService;
import com.wr.aiot.service.VisitingSecordService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.DoorEvent;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.vo.VisitingSecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/7 19:31
 */
@Slf4j
@Service
public class VisitingSecordServiceImpl extends ServiceImpl<VisitingSecordMapper, VisitingSecord> implements VisitingSecordService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private DoorService doorService;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public VisitingSecordVo getByIdVisitingSecordInfo(String id) {
        return baseMapper.getByIdVisitingSecordInfo(id);
    }

    /**
     * @param type
     * @throws Exception
     */
    @Override
    public void visitingSecord(String type) throws Exception {
        List<CommunityHik> hik = communityMapper.selectHikByCommunityIdAllList();
        if (CollectionUtils.isNotEmpty(hik)) {
            hik.stream().forEach(communityHik -> {
                Door door = new Door();
                door.setCommunityId(communityHik.getCommunityId());
                List<Door> doorList = doorService.selectDoorList(door);
                if (CollectionUtils.isNotEmpty(doorList)) {
                    for (Door door1 : doorList) {
                        try {
                            String renewalBy = SecurityUtils.getUsername();
                            int pageNo = 1;
                            int pageSize = 1000;
                            String startTime = "";
                            String endTime = "";
                            if (type.equals("1")) {
                                startTime = DateUtils.getDate() + "T00:00:00+08:00";
                                endTime = DateUtils.getDate() + "T23:59:59+08:00";
                            }
                            Integer total = HaiKUtils.visitingSecordsTotal(communityHik, startTime, endTime, door1.getIndexCode());
                            pageNo = (int) Math.ceil((double) total / pageSize);
                            for (int i = 0; i < pageNo; i++) {
                                JSONArray dataList = HaiKUtils.visitingSecords(communityHik, (i+1), pageSize, startTime, endTime, door1.getIndexCode());

                                log.info("访客数据同步：第{}页，共：{}条数据",  (i+1), dataList.size());

                                for (int j = 0; j < dataList.size(); j++) {
                                    JSONObject jsonObject = dataList.getJSONObject(j);
                                    if (jsonObject != null) {
                                        extracted(jsonObject);
                                        VisitingSecord visitingSecord = JSONObject.parseObject(jsonObject.toJSONString(), VisitingSecord.class);
                                        visitingSecord.setDoorIndexCode(door1.getIndexCode());
                                        visitingSecord.setCommunityId(communityHik.getCommunityId());
                                        LambdaQueryWrapper<VisitingSecord> queryWrapper = new LambdaQueryWrapper<>();
                                        queryWrapper.eq(VisitingSecord::getEventId, visitingSecord.getEventId());
                                        queryWrapper.eq(VisitingSecord::getDoorIndexCode, door1.getIndexCode());
                                        VisitingSecord doorEvent = baseMapper.selectOne(queryWrapper);
                                        if (doorEvent != null) {
                                           // visitingSecord.setId(doorEvent.getId());
                                           // visitingSecord.setUpdateTime(new Date());
                                           // visitingSecord.setUpdateBy(renewalBy);
                                           // baseMapper.updateById(visitingSecord);
                                        } else {
                                            visitingSecord.setId(visitingSecord.getEventId());
                                            visitingSecord.setCreateTime(new Date());
                                            visitingSecord.setCreateBy(renewalBy);
                                            baseMapper.insert(visitingSecord);
                                        }
                                    }

                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    private static void extracted(JSONObject jsonObject) {

        String eventTime = String.valueOf(jsonObject.get("eventTime"));
        eventTime = eventTime.replace("T", " ").replace("+08:00", "");
        eventTime = eventTime.substring(0, 19);
        jsonObject.put("eventTime", eventTime);
        String visitStartTime = String.valueOf(jsonObject.get("visitStartTime"));
        visitStartTime = visitStartTime.replace("T", " ").replace("+08:00", "");
        visitStartTime = visitStartTime.substring(0, 19);
        jsonObject.put("visitStartTime", visitStartTime);
        String visitEndTime = String.valueOf(jsonObject.get("visitEndTime"));
        visitEndTime = visitEndTime.replace("T", " ").replace("+08:00", "");
        visitEndTime = visitEndTime.substring(0, 19);
        jsonObject.put("visitEndTime", visitEndTime);
        String visitRegisterTime = String.valueOf(jsonObject.get("visitRegisterTime"));
        visitRegisterTime = visitRegisterTime.replace("T", " ").replace("+08:00", "");
        visitRegisterTime = visitRegisterTime.substring(0, 19);
        jsonObject.put("visitRegisterTime", visitRegisterTime);
        String visitRealEndTime = String.valueOf(jsonObject.get("visitRealEndTime"));
        visitRealEndTime = visitRealEndTime.replace("T", " ").replace("+08:00", "");
        visitRealEndTime = visitRealEndTime.substring(0, 19);
        jsonObject.put("visitRealEndTime", visitRealEndTime);
    }
}
