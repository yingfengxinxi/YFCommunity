package com.wr.govern.service.impl.security;

import com.wr.govern.mapper.security.CameraMapper;
import com.wr.govern.service.security.CameraService;
import com.wr.remote.govern.security.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-27 14:43:31
 * @Desc: 监控视频业务层
 */
@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    private CameraMapper cameraMapper;


    @Override
    public List<Camera> getList(Camera camera) {
        return cameraMapper.selectCameraList(camera);
    }
}
