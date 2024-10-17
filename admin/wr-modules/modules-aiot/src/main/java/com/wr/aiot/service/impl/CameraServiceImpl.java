package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.CameraMapper;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.service.CameraService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ProtocolConstants;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Camera;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: 2022-10-26 13:34:07
 * @Desc: 监控点业务处理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CameraServiceImpl implements CameraService {
    private static final Logger log = LoggerFactory.getLogger(CameraServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final CameraMapper cameraMapper;
    private final RemoteFileService remoteFileService;

    @Override
    @Transactional(readOnly = false)
    public List<Camera> selectCameraList(Camera camera) {
        if (null == camera.getCommunityId() || 0L == camera.getCommunityId()) {
            return Lists.newArrayList();
        }
        return cameraMapper.selectCameraList(camera);
    }

    /**
     * 同步监控点
     *
     * @param communityId 小区id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncCameraData(Long communityId) throws Exception {

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
            int num = 0;
            Integer total = HaiKUtils.cameraSearchTotal(hik);
            pageNo = (int) Math.ceil((double) total / pageSize);
            for (int i = 0; i < pageNo; i++) {
                JSONArray dataList = HaiKUtils.cameraSearch(hik, (i + 1), pageSize);

                log.info("监控点数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());
                for (int j = 0; j < dataList.size(); i++) {
                    String indexCode = String.valueOf(dataList.getJSONObject(j).get("indexCode"));
                    String cameraName = String.valueOf(dataList.getJSONObject(j).get("name"));
                    String cameraType = String.valueOf(dataList.getJSONObject(j).get("cameraType"));
                    String chanNum = String.valueOf(dataList.getJSONObject(j).get("chanNum"));
                    String capability = String.valueOf(dataList.getJSONObject(j).get("capability"));
                    String channelType = String.valueOf(dataList.getJSONObject(j).get("channelType"));
                    String decodeTag = String.valueOf(dataList.getJSONObject(j).get("decodeTag"));
                    String transType = String.valueOf(dataList.getJSONObject(j).get("transType"));
                    String treatyType = String.valueOf(dataList.getJSONObject(j).get("treatyType"));
                    String dacIndexCode = String.valueOf(dataList.getJSONObject(j).get("dacIndexCode"));
                    Camera camera = cameraMapper.getByIndexCodeInfo(indexCode);
                    //同步状态
                    Integer status = HaiKUtils.cameraOnline(hik, indexCode);
                    int online = Optional.ofNullable(status).orElse(-1);
                    if (camera != null) {
                        camera.setIndexCode(indexCode);
                        camera.setCameraName(cameraName);
                        camera.setCameraType(Integer.valueOf(cameraType));
                        camera.setChanNum(Integer.valueOf(chanNum));
                        camera.setCapability(capability);
                        camera.setChannelType(channelType);
                        camera.setDecodeTag(decodeTag);
                        camera.setCameraRelateTalk("");
                        camera.setTransType(Integer.valueOf(transType));
                        camera.setTreatyType(treatyType);
                        camera.setRecordLocation("");
                        camera.setDacIndexCode(dacIndexCode);
                        camera.setOnline(online);
                        camera.setUpdateTime(new Date());
                        cameraMapper.update(camera);
                    } else {
                        camera = new Camera();
                        camera.setCommunityId(communityId);
                        camera.setRenewalBy(renewalBy);
                        camera.setIndexCode(indexCode);
                        camera.setCameraName(cameraName);
                        camera.setCameraType(Integer.valueOf(cameraType));
                        camera.setChanNum(Integer.valueOf(chanNum));
                        camera.setCapability(capability);
                        camera.setChannelType(channelType);
                        camera.setDecodeTag(decodeTag);
                        camera.setCameraRelateTalk("");
                        camera.setTransType(Integer.valueOf(transType));
                        camera.setTreatyType(treatyType);
                        camera.setRecordLocation("");
                        camera.setDacIndexCode(dacIndexCode);
                        camera.setOnline(online);
                        camera.setCreateTime(new Date());
                        camera.setUpdateTime(new Date());
                        cameraMapper.insert(camera);
                    }
                }
                num += dataList.size();
            }

            cameraMapper.delete();
            return num > 0 ? AjaxResult.success("已同步" + num + "条监控点数据") : AjaxResult.error("数据同步失败");

    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult checkOnline(Camera camera) {
        try {
            if (null == camera.getCommunityId() || 0L == camera.getCommunityId()) {
                return AjaxResult.error("请选择小区！");
            }
            CommunityHik hik = communityMapper.selectHikByCommunityId(camera.getCommunityId());
            if (null == hik) {
                return AjaxResult.error("请检查小区海康配置后重试！");
            }
            List<Camera> cameraList = Lists.newArrayList();
            for (String indexCode : camera.getIndexCodeList()) {
                Integer status = HaiKUtils.cameraOnline(hik, indexCode);
                int online = Optional.ofNullable(status).orElse(-1);
                Camera temp = new Camera();
                temp.setIndexCode(indexCode);
                temp.setOnline(online);
                cameraList.add(temp);
            }
            if (!cameraList.isEmpty()) {
                //修改状态
                cameraMapper.updateCameraOnline(cameraList);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Camera selectCameraById(Long cameraId) {

        return cameraMapper.selectCameraById(cameraId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateCamera(Camera camera) {

        return cameraMapper.updateCamera(camera);
    }

    @Override
    public Integer batchUpdateDeviceQr(Camera camera) {
        List<Camera> cameraList;
        if (camera.getIds().length == 0) {
            // 查询未设置设备二维码的设备id集
            cameraList = cameraMapper.selectIdsOfNotQrCode();
        } else {
            cameraList = cameraMapper.selectCameraByIds(Arrays.asList(camera.getIds()));
        }
        return cameraMapper.batchUpdateDeviceQr(packageCamera(cameraList));
    }

    @Override
    public AjaxResult selectCameraUrl(Camera camera) {
        try {
            CommunityHik hik = communityMapper.selectHikByCommunityId(camera.getCommunityId());
            if (null == hik) {
                return AjaxResult.error("请检查小区海康配置后重试！");
            }
            String url = HaiKUtils.camerasPreviewURLs(hik, camera.getIndexCode(), ProtocolConstants.PROTOCOL_WS);
            if (StringUtils.isEmpty(url)) {
                return AjaxResult.error("取流失败！");
            }
            return AjaxResult.success("取流成功！", url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装二维码数据
     *
     * @param cameras 监控点设备信息
     * @return java.util.List<com.wr.remote.aiot.Camera>
     */
    private List<Camera> packageCamera(List<Camera> cameras) {
        cameras.forEach(camera -> {
            JSONObject json = JSONObject.of();
            json.put("id", camera.getIndexCode());
            json.put("deviceType", "camera");
            json.put("type", "device");
            try {
                MultipartFile file = QrCodeUtils.createQrCode(json.toJSONString(), null, camera.getCameraName());
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (com.wr.common.core.utils.StringUtils.isNotNull(fileResult) && com.wr.common.core.utils.StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    camera.setDeviceQr(url);
                }
            } catch (Exception e) {
                log.error("二维码生成失败：{}", e.getMessage());
            }
        });

        return cameras;
    }
}
