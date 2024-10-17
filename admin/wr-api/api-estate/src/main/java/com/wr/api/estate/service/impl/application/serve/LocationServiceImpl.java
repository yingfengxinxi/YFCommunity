package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.mapper.SensitiveMapper;
import com.wr.api.estate.mapper.application.serve.LocationMapper;
import com.wr.api.estate.service.application.serve.LocationService;
import com.wr.common.customize.constant.DeviceConstants;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.domain.FacilitieEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 11:52:33
 * @Desc: 资产定位业务层
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public Map<String,Object> getDeviceName(String indexCode, String type) {
        return locationMapper.getDeviceName(indexCode, type);
    }

    @Override
    public Integer updateAddress(String indexCode, String type, String address) {
        String tableName;
        if (DeviceConstants.RESOURCE_ACS_DEVICE.equals(type)) {
            tableName = "xccj_device.bus_access";
        } else if (DeviceConstants.RESOURCE_VIS_DEVICE.equals(type)) {
            tableName = "xccj_device.bus_visual";
        } else if (DeviceConstants.RESOURCE_CAMERA.equals(type)) {
            tableName = "xccj_device.bus_camera";
        } else if (DeviceConstants.RESOURCE_CLOUD.equals(type)) {
            tableName = "xccj_device.bus_cloud_access";
        } else if (DeviceConstants.RESOURCE_BARRIER.equals(type)) {
            tableName = "xccj_device.bus_barrier";
        } else {
            return null;
        }
        return locationMapper.updateAddress(tableName, indexCode, SensitiveUtil.sensitiveFilter(address,sensitiveMapper.selectSensitiveList()));
    }

    @Override
    public FacilitieEquipment getFacilitieById(String id) {
        return locationMapper.getFacilitieById(id);
    }
}
