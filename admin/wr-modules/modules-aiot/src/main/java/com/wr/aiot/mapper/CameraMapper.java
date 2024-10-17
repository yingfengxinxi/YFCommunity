package com.wr.aiot.mapper;

import com.alibaba.fastjson2.JSONArray;
import com.wr.remote.aiot.Camera;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:35:34
 * @Desc: 监控点数据层
 */
public interface CameraMapper {
    /**
     * 查询监控点数据
     *
     * @param camera 监控点信息
     * @return java.util.List<com.wr.remote.aiot.Camera>
     */
    List<Camera> selectCameraList(Camera camera);

    /**
     * 修改监控点状态
     *
     * @param cameraList 监控点数据
     * @return java.lang.Integer
     */
    Integer updateCameraOnline(@Param("cameraList") List<Camera> cameraList);

    /**
     * 同步监控点点设备
     *
     * @param camera
     * @return
     */
    Integer insert(Camera camera);

    /**
     * @param camera
     */
    void update(Camera camera);

    /**
     * @param indexCode
     * @return
     */
    Camera getByIndexCodeInfo(@Param("indexCode") String indexCode);

    /**
     *
     */
    void delete();

    /**
     * 根据id查询数据
     *
     * @param cameraId 监控点id
     * @return com.wr.remote.aiot.Camera
     */
    Camera selectCameraById(Long cameraId);

    /**
     * 根据ids查询监控信息
     *
     * @param cameraIds
     * @return java.util.List<com.wr.remote.aiot.Camera>
     * @author yx
     * @date 2022/11/17 16:54
     */
    List<Camera> selectCameraByIds(List<Long> cameraIds);

    /**
     * 更新监控点
     *
     * @param camera 监控点信息
     * @return java.lang.Integer
     */
    Integer updateCamera(Camera camera);

    /**
     * 查询未设置设备二维码的设备id集
     *
     * @return java.lang.Long[]
     */
    List<Camera> selectIdsOfNotQrCode();

    /**
     * 批量设置监控点二维码
     *
     * @param cameraList 监控点信息
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(@Param("cameraList") List<Camera> cameraList);
}
