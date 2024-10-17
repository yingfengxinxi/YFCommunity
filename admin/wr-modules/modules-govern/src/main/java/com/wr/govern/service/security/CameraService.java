package com.wr.govern.service.security;

import com.wr.remote.govern.security.Camera;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-27 14:43:17
 * @Desc: 监控视频接口层
 */
public interface CameraService {

    /**
     * 获取监控视频集合
     * @param camera
     * @return
     */
    List<Camera> getList(Camera camera);
}
