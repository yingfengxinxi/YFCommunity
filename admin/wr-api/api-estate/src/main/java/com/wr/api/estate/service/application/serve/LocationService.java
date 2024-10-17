package com.wr.api.estate.service.application.serve;

import com.wr.remote.domain.FacilitieEquipment;

import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 11:52:12
 * @Desc: 资产定位实现类
 */
public interface LocationService {

    /**
     * 获取设备名称
     * @param indexCode 设备唯一标识
     * @param type 设备类型
     * @return
     */
    Map<String,Object> getDeviceName(String indexCode, String type);

    /**
     * 更改设备地址
     * @param indexCode
     * @param type
     * @return
     */
    Integer updateAddress(String indexCode, String type ,String address);

    /**
     *
     * @param id
     * @return
     */
    FacilitieEquipment getFacilitieById(String id);
}
