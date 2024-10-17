package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.vo.application.serve.CameraVO;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Camera;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 20:12:25
 * @Desc:
 */
public interface CameraService {
    /**
     * 在线 监控点
     * @param communityId
     * @return
     */
    List<CameraVO> searchByCommunityId(Long communityId,String cameraName);

    /**
     * 查询视频监控视频流
     * @param indexCode
     * @param communityId
     * @return java.lang.String
     */
    AjaxResult selectCameraUrl(String indexCode,Long communityId);
}

