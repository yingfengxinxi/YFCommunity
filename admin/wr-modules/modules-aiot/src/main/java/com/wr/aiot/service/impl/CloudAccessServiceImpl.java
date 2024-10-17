package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.CloudAccessMapper;
import com.wr.aiot.service.CloudAccessService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.CloudAccess;
import com.wr.remote.aiot.Door;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-26 10:10:19
 * @Desc: 云+门禁业务处理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CloudAccessServiceImpl implements CloudAccessService {
    private static final Logger log = LoggerFactory.getLogger(CloudAccessServiceImpl.class);

    private final CloudAccessMapper cloudAccessMapper;
    private final RemoteFileService remoteFileService;

    @Override
    public List<CloudAccess> selectCloudAccessList(CloudAccess cloudAccess) {

        return cloudAccessMapper.selectCloudAccessList(cloudAccess);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertCloudAccess(CloudAccess cloudAccess) {
        cloudAccess.setCreateBy(SecurityUtils.getUsername());
        cloudAccess.setManufacturer("Cloud+");
        // 添加设备
        int num = cloudAccessMapper.insertCloudAccess(cloudAccess);
        //生成设备二维码
        // TODO: 2022/10/31 生成设备二维码
        /*String url = createQrCode(cloudAccess.getAccessId());
        List<CloudAccess> accessList = Lists.newArrayList();
        cloudAccess.setDeviceQr(url);
        accessList.add(cloudAccess);
        cloudAccessMapper.batchUpdateDeviceQr(accessList);*/
        return num;
    }

    @Override
    public CloudAccess selectCloudAccessById(Long accessId) {

        return cloudAccessMapper.selectCloudAccessById(accessId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateCloudAccess(CloudAccess cloudAccess) {
        cloudAccess.setUpdateBy(SecurityUtils.getUsername());
        return cloudAccessMapper.updateCloudAccess(cloudAccess);
    }

    @Override
    public void batchUpdateDeviceQr(CloudAccess cloudAccess) {
        List<CloudAccess> accessList;
        if (ArrayUtils.isEmpty(cloudAccess.getIds())) {
            // 查询未设置设备二维码的设备id集
            accessList = cloudAccessMapper.selectIdsOfNotQrCode();
        } else {
            accessList = cloudAccessMapper.selectCloudAccessByIds(Arrays.asList(cloudAccess.getIds()));
        }
        List<CloudAccess> cloudAccesses = packageAccess(accessList);
        if (!CollectionUtils.isEmpty(cloudAccesses)){
            cloudAccessMapper.batchUpdateDeviceQr(cloudAccesses);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Integer removeCloudAccess(Long[] ids) {

        return cloudAccessMapper.removeCloudAccess(ids);
    }

    @Override
    public List<Door> selectUnbindDoors(CloudAccess cloudAccess) {

        return cloudAccessMapper.selectUnbindDoors(cloudAccess);
    }

    /**
     * 封装二维码数据
     *
     * @param accessIds 门禁id
     * @return java.util.List<com.wr.remote.aiot.CloudAccess>
     */
    private List<CloudAccess> packageAccess(List<CloudAccess> accessList) {
        accessList.forEach(access -> {
            String url = createQrCode(access);
            access.setDeviceQr(url);
        });
        return accessList;
    }

    private String createQrCode(CloudAccess cloudAccess) {
        JSONObject json = JSONObject.of();
        json.put("id", cloudAccess.getAccessSerial());
        json.put("deviceType", "cloud");
        json.put("type", "device");
        try {
            MultipartFile file = QrCodeUtils.createQrCode(json.toJSONString(),null,cloudAccess.getAccessName());
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                return fileResult.getData().getUrl();
            }
            return null;
        } catch (Exception e) {
            log.error("二维码生成失败：{}", e.getMessage());
            return null;
        }
    }
}
