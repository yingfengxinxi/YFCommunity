package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Camera;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:51:43
 * @Desc: 监控点业务处理
 */
public interface CameraService {
    /**
     * 查询监控点数据
     * @param camera 监控点信息
     * @return java.util.List<com.wr.remote.aiot.Camera>
     */
    List<Camera> selectCameraList(Camera camera);

    /**
     * 同步监控点设备
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncCameraData(Long communityId) throws Exception;

    /**
     * 更新设备状态
     * @param camera 监控点
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult checkOnline(Camera camera);

    /**
     * 根据id查询数据
     * @param cameraId 监控点id
     * @return com.wr.remote.aiot.Camera
     */
    Camera selectCameraById(Long cameraId);

    /**
     * 更新监控点
     * @param camera 监控点
     * @return java.lang.Integer
     */
    Integer updateCamera(Camera camera);

    /**
     * 批量设置监控点设备二维码
     * @param camera 监控点
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(Camera camera);

    /**
     * 查询视频监控视频流
     * @param camera 监控点信息
     * @return java.lang.String
     */
    AjaxResult selectCameraUrl(Camera camera);

}
