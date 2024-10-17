package com.wr.govern.mapper.security;

import com.wr.remote.govern.security.Camera;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-27 14:44:45
 * @Desc: 监控视频DAO层
 */
@Mapper
public interface CameraMapper {

    /**
     * 监控视频集合
     * @param camera
     * @return
     */
    List<Camera> selectCameraList(Camera camera);
}
