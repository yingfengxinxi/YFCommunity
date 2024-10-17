package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.AccessMapper;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.service.AccessService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.Door;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:55:39
 * @Desc: 门禁信息业务处理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccessServiceImpl implements AccessService {
    private static final Logger log = LoggerFactory.getLogger(AccessServiceImpl.class);

    private final AccessMapper accessMapper;
    private final CommunityMapper communityMapper;
    private final RemoteFileService remoteFileService;

    @Override
    public List<Access> selectAccessList(Access access) {
        if (null == access.getCommunityId() || 0L == access.getCommunityId()) {
            return Lists.newArrayList();
        }
        return accessMapper.selectAccessList(access);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncAccessData(Long communityId) throws Exception {
        if (null == communityId || 0L == communityId) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String renewalBy = SecurityUtils.getUsername();
        int pageNo = 1;
        int pageSize = 1000;
        Integer total = HaiKUtils.acsDeviceSearchTotal(hik);
        pageNo = (int) Math.ceil((double) total / pageSize);
        int num = 0;
        for (int i = 0; i < pageNo; i++) {
            JSONArray dataList = HaiKUtils.acsDeviceSearch(hik, (i + 1), pageSize);
            num = num + dataList.size();
            log.info("门禁数据同步：第{}页，共：{}条数据", pageNo, dataList.size());
            for (int j = 0; j < dataList.size(); j++) {
                String indexCode = String.valueOf(dataList.getJSONObject(j).get("indexCode"));
                String accessName = String.valueOf(dataList.getJSONObject(j).get("name"));
                String devTypeCode = String.valueOf(dataList.getJSONObject(j).get("devTypeCode"));
                String devTypeDesc = String.valueOf(dataList.getJSONObject(j).get("devTypeDesc"));
                String ip = String.valueOf(dataList.getJSONObject(j).get("ip"));
                String port = String.valueOf(dataList.getJSONObject(j).get("port"));
                String treatyType = String.valueOf(dataList.getJSONObject(j).get("treatyType"));
                String capability = String.valueOf(dataList.getJSONObject(j).get("capability"));
                Integer cardCapacity = Integer.valueOf(String.valueOf(dataList.getJSONObject(j).get("cardCapacity")));
                Integer fingerCapacity = Integer.valueOf(String.valueOf(dataList.getJSONObject(j).get("fingerCapacity")));
                Integer faceCapacity = Integer.valueOf(String.valueOf(dataList.getJSONObject(j).get("faceCapacity")));
                Integer doorCapacity = Integer.valueOf(String.valueOf(dataList.getJSONObject(j).get("doorCapacity")));
                String dataVersion = String.valueOf(dataList.getJSONObject(j).get("dataVersion"));
                String devSerialNum = String.valueOf(dataList.getJSONObject(j).get("devSerialNum"));
                String manufacturer = String.valueOf(dataList.getJSONObject(j).get("manufacturer"));
                Access access = new Access();
                access.setIndexCode(indexCode);
                access.setAccessName(accessName);
                access.setDevTypeCode(devTypeCode);
                access.setDevTypeDesc(devTypeDesc);
                access.setAccessIp(ip);
                access.setAccessPort(port);
                access.setTreatyType(treatyType);
                access.setCapability(capability);
                access.setCardCapacity(cardCapacity);
                access.setFingerCapacity(fingerCapacity);
                access.setFaceCapacity(faceCapacity);
                access.setDoorCapacity(doorCapacity);
                access.setDataVersion(dataVersion);
                access.setDevSerialNum(devSerialNum);
                Integer status = HaiKUtils.acsDeviceOnline(hik, indexCode);
                int online = Optional.ofNullable(status).orElse(-1);
                access.setOnline(online);
                access.setManufacturer(manufacturer);
                access.setRenewalBy(renewalBy);
                access.setCommunityId(communityId);
                Access access1 = accessMapper.getByIndexCodeInfo(indexCode);
                if (access1 != null) {
                    access.setAccessId(access1.getAccessId());
                    accessMapper.update(access);
                } else {
                    accessMapper.insert(access);
                }
            }
            num += dataList.size();
            pageNo++;
        }
        accessMapper.delete();
        return num > 0 ? AjaxResult.success("已同步" + num + "条门禁数据") : AjaxResult.error("数据同步失败");
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult checkOnline(Access access) throws Exception {
        if (null == access.getCommunityId() || 0L == access.getCommunityId()) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(access.getCommunityId());
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        List<Access> accessList = Lists.newArrayList();
        for (String indexCode : access.getIndexCodeList()) {
            Integer status = HaiKUtils.acsDeviceOnline(hik, indexCode);
            int online = Optional.ofNullable(status).orElse(-1);
            Access temp = new Access();
            temp.setIndexCode(indexCode);
            temp.setOnline(online);
            accessList.add(temp);
        }
        if (!accessList.isEmpty()) {
            accessMapper.updateAccessOnline(accessList);
        }
        return AjaxResult.success();
    }

    @Override
    public Access selectAccessById(Long accessId) {

        return accessMapper.selectAccessById(accessId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateAccess(Access access) {

        return accessMapper.updateAccess(access);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer batchUpdateAccess(Access access) {

        return accessMapper.batchUpdateAccess(access);
    }

    @Transactional(readOnly = false)
    @Override
    public Integer batchUpdateDeviceQr(Access access) {
        List<Access> accessList;
        if (access.getIds().length == 0) {
            // 查询未设置设备二维码的设备id集
            accessList = accessMapper.selectIdsOfNotQrCode();
        } else {
            accessList = accessMapper.selectAccessByIds(Arrays.asList(access.getIds()));
        }
        List<Access> updateList = packageAccess(accessList);
        return accessMapper.batchUpdateDeviceQr(updateList);
    }


    @Override
    public AjaxResult subscribeLegalCard(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        String eventDest = CommunityConstants.GATEWAY_URL + "/api/device/acsOpen";
        // 合法卡比对事件
        boolean result = HikUtils.eventSubscription(HikConstants.EVENT_LEGAL_CARD, eventDest, 0);
        return result ? AjaxResult.success("订阅成功！") : AjaxResult.error("订阅失败！");
    }

    @Override
    public AjaxResult unsubscribeLegalCard(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        // 合法卡比对事件
        boolean result = HikUtils.eventUnSubscription(HikConstants.EVENT_LEGAL_CARD);
        return result ? AjaxResult.success("退订成功！") : AjaxResult.error("退订失败！");
    }

    @Override
    public List<Door> selectUnbindDoors(Access access) {

        return accessMapper.selectUnbindDoors(access);
    }

    /**
     * 封装二维码数据
     *
     * @param accessList 门禁id
     * @return java.util.List<com.wr.remote.aiot.Access>
     */
    private List<Access> packageAccess(List<Access> accessList) {
        accessList.forEach(access -> {
            JSONObject json = JSONObject.of();
            json.put("id", access.getIndexCode());
            json.put("deviceType", "acsDevice");
            json.put("type", "device");
            String deviceQr = json.toJSONString();
            try {
                MultipartFile file = QrCodeUtils.createQrCode(deviceQr, null, access.getAccessName());
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    access.setDeviceQr(url);
                }
            } catch (Exception e) {
                log.error("二维码生成失败：{}", e.getMessage());
            }
        });

        return accessList;
    }
}
