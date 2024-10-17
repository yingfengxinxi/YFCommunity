package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.DoorMapper;
import com.wr.aiot.service.DoorService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Door;
import com.wr.common.customize.util.CommunityHik;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:33:41
 * @Desc: 门禁点业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DoorServiceImpl implements DoorService {
    private static final Logger log = LoggerFactory.getLogger(DoorServiceImpl.class);

    private final DoorMapper doorMapper;
    private final CommunityMapper communityMapper;

    @Override
    public List<Door> selectDoorList(Door door) {
        if (null == door.getCommunityId() || 0L == door.getCommunityId()) {
            return Lists.newArrayList();
        }
        return doorMapper.selectDoorList(door);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncDoorData(Long communityId) throws Exception {
        if (null == communityId || 0L == communityId) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String renewalBy = SecurityUtils.getUsername();
        int pageNo = 1;
        int pageSize = 1;
        int num = 0;
        Integer total = HaiKUtils.doorSearchTotal(hik);
        pageNo = (int) Math.ceil((double) total / pageSize);
        for (int i = 0; i < pageNo; i++) {
            JSONArray dataList = HaiKUtils.doorSearch(hik, (i + 1), pageSize);
            log.info("门禁点数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());
            for (int j = 0; j < dataList.size(); j++) {
                String indexCode = String.valueOf(dataList.getJSONObject(j).get("indexCode"));
                String doorName = String.valueOf(dataList.getJSONObject(j).get("name"));
                String resourceType = String.valueOf(dataList.getJSONObject(j).get("resourceType"));
                String doorNo = String.valueOf(dataList.getJSONObject(j).get("doorNo"));
                Integer doorSerial = Integer.valueOf(String.valueOf(dataList.getJSONObject(j).get("doorSerial")));
                String channelType = String.valueOf(dataList.getJSONObject(j).get("channelType"));
                String channelNo = String.valueOf(dataList.getJSONObject(j).get("channelNo"));
                String treatyType = String.valueOf(dataList.getJSONObject(j).get("treatyType"));
                String controlOneId = String.valueOf(dataList.getJSONObject(j).get("controlOneId"));
                String controlTwoId = String.valueOf(dataList.getJSONObject(j).get("controlTwoId"));
                String readerInId = String.valueOf(dataList.getJSONObject(j).get("readerInId"));
                String readerOutId = String.valueOf(dataList.getJSONObject(j).get("readerOutId"));
                Door door = new Door();
                door.setIndexCode(indexCode);
                door.setDoorName(doorName);
                door.setDoorNo(doorNo);
                door.setDoorSerial(doorSerial);
                door.setChannelType(channelType);
                door.setChannelNo(channelNo);
                door.setTreatyType(treatyType);
                door.setControlOneId(controlOneId);
                door.setControlTwoId(controlTwoId);
                door.setReaderInId(readerInId);
                door.setReaderOutId(readerOutId);
                Integer status = HaiKUtils.doorOnline(hik, indexCode);
                int online = Optional.ofNullable(status).orElse(-1);
                door.setOnline(online);
                door.setManufacturer("hikvision");
                door.setRenewalBy(renewalBy);
                door.setUpdateBy(renewalBy);
                door.setOperateTime(new Date());
                door.setCommunityId(communityId);
                door.setInoutType("");
                door.setDelFlag("0");
                Door door1 = doorMapper.getByIndexCodeInfo(indexCode);
                if (door1 != null) {
                    door.setDoorId(door1.getDoorId());
                    doorMapper.update(door);
                } else {
                    doorMapper.insert(door);
                }
            }
            doorMapper.delete();
            num += dataList.size();
        }
        return num > 0 ? AjaxResult.success("已同步" + num + "条门禁点数据") : AjaxResult.error("数据同步失败");
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult checkOnline(Door door) throws Exception {
        if (null == door.getCommunityId() || 0L == door.getCommunityId()) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(door.getCommunityId());
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        List<Door> doorList = Lists.newArrayList();
        for (String indexCode : door.getIndexCodeList()) {
            Integer status = HaiKUtils.doorOnline(hik, indexCode);
            int online = Optional.ofNullable(status).orElse(-1);
            Door temp = new Door();
            temp.setIndexCode(indexCode);
            temp.setOnline(online);
            doorList.add(temp);
        }
        if (!doorList.isEmpty()) {
            doorMapper.batchUpdateOnline(doorList);
        }
        return AjaxResult.success();
    }

    @Override
    public Door selectDoorById(Long doorId) {

        return doorMapper.selectDoorById(doorId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateDoor(Door door) {

        return doorMapper.updateDoor(door);
    }

    @Override
    public List<Door> selectAllDoorList(Long communityId) {
        Door door = new Door();
        door.setCommunityId(communityId);
        return doorMapper.selectDoorList(door);
    }
}
