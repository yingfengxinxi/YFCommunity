package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.BarrierMapper;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.EntranceMapper;
import com.wr.aiot.mapper.ParkMapper;
import com.wr.aiot.mapper.RoadWayMapper;
import com.wr.aiot.service.ParkService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Entrance;
import com.wr.remote.aiot.Park;
import com.wr.remote.aiot.Roadway;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-26 17:27:02
 * @Desc: 停车场业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ParkServiceImpl implements ParkService {
    private static final Logger log = LoggerFactory.getLogger(ParkServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final ParkMapper parkMapper;
    private final EntranceMapper entranceMapper;
    private final RoadWayMapper roadWayMapper;
    private final RemoteFileService remoteFileService;
    private final BarrierMapper barrierMapper;

    @Override
    public List<Park> selectParkList(Park park) {

        return parkMapper.selectParkList(park);
    }

    // @Override
    // @Transactional(readOnly = false)
    // public AjaxResult syncParkData(Long communityId) {
    //     if(null == communityId || 0L == communityId){
    //         return AjaxResult.error("请选择小区！");
    //     }
    //     CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
    //     if(null == hik){
    //         return AjaxResult.error("请检查小区海康配置后重试！");
    //     }
    //     String renewalBy = SecurityUtils.getUsername();
    //     HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
    //     JSONArray parks = HikUtils.parkList();
    //     JSONArray parkList = packageParkList(parks);
    //     log.info("停车场数据同步：共：{}条数据", parkList.size());
    //     if(parkList.isEmpty()){
    //         return AjaxResult.success();
    //     }
    //     int num = parkMapper.insertOrUpdate(parkList, "hikvision", renewalBy, communityId);
    //     for (int i = 0; i < parkList.size(); i++) {
    //         JSONObject park = parkList.getJSONObject(i);
    //         JSONArray entrances = HikUtils.entranceList(park.getString("parkIndexCode"));
    //         if(entrances.isEmpty()){
    //             continue;
    //         }
    //         log.info("停车场：{} 出入口数据同步：共：{}条数据", park.getString("parkName"), entrances.size());
    //         entranceMapper.insertOrUpdate(entrances);
    //         for (int j = 0; j < entrances.size(); j++) {
    //             JSONObject entrance = entrances.getJSONObject(j);
    //             String entranceIndexCode = entrance.getString("entranceIndexCode");
    //             JSONArray roadways = HikUtils.roadwayList(entranceIndexCode);
    //             if(roadways.isEmpty()){
    //                 continue;
    //             }
    //             log.info("停车场：{}出入口：{} 车道数据同步：共：{}条数据", park.getString("parkName"), entrance.getString("entranceName"), roadways.size());
    //             roadWayMapper.insertOrUpdate(roadways);
    //         }
    //     }
    //     return num > 0 ? AjaxResult.success("已同步"+parkList.size()+"条停车场相关数据") : AjaxResult.error("数据同步失败");
    //
    // }
    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncParkData(Long communityId) throws Exception {
        if(null == communityId || 0L == communityId){
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String renewalBy = SecurityUtils.getUsername();

        JSONArray parks = HaiKUtils.parkList(hik,"");
        JSONArray parkList = packageParkList(parks);
        log.info("停车场数据同步：共：{}条数据", parkList.size());
        if(parkList.isEmpty()){
            return AjaxResult.success();
        }
        int num = parkMapper.insertOrUpdate(parkList, "hikvision", renewalBy, communityId);
        for (int i = 0; i < parkList.size(); i++) {
            JSONObject park = parkList.getJSONObject(i);
            JSONArray entrances = HaiKUtils.entranceList(hik, park.getString("parkIndexCode"));
            if(entrances.isEmpty()){
                continue;
            }
            log.info("停车场：{} 出入口数据同步：共：{}条数据", park.getString("parkName"), entrances.size());
            entranceMapper.insertOrUpdate(entrances);
            for (int j = 0; j < entrances.size(); j++) {
                JSONObject entrance = entrances.getJSONObject(j);
                String entranceIndexCode = entrance.getString("entranceIndexCode");
                JSONArray roadways = HaiKUtils.roadwayList(hik, entranceIndexCode);
                if(roadways.isEmpty()){
                    continue;
                }
                log.info("停车场：{}出入口：{} 车道数据同步：共：{}条数据", park.getString("parkName"), entrance.getString("entranceName"), roadways.size());
                roadWayMapper.insertOrUpdate(roadways);
            }
        }
        return num > 0 ? AjaxResult.success("已同步"+parkList.size()+"条停车场相关数据") : AjaxResult.error("数据同步失败");

    }
    private static JSONArray packageParkList(JSONArray parks){
        if(parks.isEmpty()){
            return new JSONArray();
        }
        JSONArray parkList = new JSONArray();
        for (int i = 0; i < parks.size(); i++) {
            JSONObject park = parks.getJSONObject(i);
            if("0".equals(park.getString("parentParkIndexCode"))){
                String createTime = String.valueOf(park.get("createTime"));
                createTime = createTime.replace("T", " ").replace("+08:00", "");
                createTime = createTime.substring(0, 19);
                park.put("createTime", createTime);
                String updateTime = String.valueOf(park.get("updateTime"));
                updateTime = updateTime.replace("T", " ").replace("+08:00", "");
                updateTime = updateTime.substring(0, 19);
                park.put("updateTime", updateTime);
                parkList.add(park);
            }
        }
        return parkList;
    }

    @Override
    public Park selectParkById(Long parkId) {

        return parkMapper.selectParkById(parkId);
    }

    @Override
    public List<Entrance> selectEntranceByPark(String parkIndexCode) {

        return entranceMapper.selectEntranceByPark(parkIndexCode);
    }

    @Override
    public List<Roadway> selectRoadwayByEntrance(String entranceIndexCode) {

        return roadWayMapper.selectRoadwayByEntrance(entranceIndexCode);
    }

    @Override
    public List<Barrier> selectBarrierByRoadway(String roadwayIndexCode) {
        return barrierMapper.selectBarrierByRoadway(roadwayIndexCode);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updatePark(Park park) {

        return parkMapper.updatePark(park);
    }

    @Override
    public AjaxResult subscribeEvent(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        //入场压线事件
        String eventDestIn = CommunityConstants.GATEWAY_URL+"/api/device/barrier/in_crimping";
        boolean inRes = HikUtils.eventSubscription(HikConstants.EVENT_IN_CRIMPING, eventDestIn,0);
        //出场压线事件
        String eventDestOut = CommunityConstants.GATEWAY_URL+"/api/device/barrier/out_crimping";
        boolean outRes = HikUtils.eventSubscription(HikConstants.EVENT_OUT_CRIMPING, eventDestOut,0);
        if(!inRes && !outRes){
            AjaxResult.error("订阅失败！");
        }
        String inMsg = inRes ? "入场压线事件订阅成功！" : "入场压线事件订阅失败！";
        String outMsg = outRes ? "出场压线事件订阅成功！" : "出场压线事件订阅失败！";
        return AjaxResult.success(inMsg + outMsg);
    }

    @Override
    public AjaxResult unsubscribeEvent(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        boolean result = HikUtils.eventUnSubscription(HikConstants.EVENT_CRIMPING);
        return result ? AjaxResult.success("退订成功！") : AjaxResult.error("退订失败！");
    }

    @Override
    @Transactional(readOnly = false)
    public Integer batchUpdatePayQr(Park park) {
        if(park.getIds().length == 0){
            // 查询未设置设备二维码的设备id集
            List<Long> cameraIds = parkMapper.selectIdsOfNotQrCode();
            List<Park> accessList = packagePark(cameraIds);
            return parkMapper.batchUpdatePayQr(accessList);
        }
        List<Park> accessList = packagePark(Arrays.asList(park.getIds()));
        return parkMapper.batchUpdatePayQr(accessList);
    }

    /**
     * 封装二维码数据
     * @param parkIds 停车场id
     * @return java.util.List<com.wr.remote.aiot.Park>
     */
    private List<Park> packagePark(List<Long> parkIds) {
        List<Park> parkList = Lists.newArrayList();
        parkIds.forEach(parkId->{
            String payCode = CommunityConstants.PARK_FEE_SERVER + "?parkId=" + parkId;
            try {
                MultipartFile file = QrCodeUtils.createQrCode(payCode);
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    Park park = new Park();
                    park.setParkId(parkId);
                    park.setPayCode(url);
                    parkList.add(park);
                }
            } catch (Exception e) {
                log.error("二维码生成失败：{}", e.getMessage());
            }
        });
        return parkList;
    }
}
