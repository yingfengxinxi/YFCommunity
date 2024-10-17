package com.wr.api.owner.service.home.location;

import com.wr.api.owner.entity.home.location.ApiCamera;
import com.wr.api.owner.entity.vo.location.AreaAndcommunityVo;
import com.wr.api.owner.entity.vo.location.CameraVO;
import com.wr.api.owner.entity.vo.location.DoorVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:13:14
 * @Desc:
 */
public interface AreaService {

    /**
     * 查询所有小区的位置
     * @return
     */
    List<AreaAndcommunityVo> AreaAndcommunityVoList();


    /**
     * 查询当前业主得楼栋单元门禁设备
     * @param accountId   账号ID
     * @param communityId   小区ID
     * @return
     */
    List<DoorVo> selectDoorControl(Long accountId,Long communityId);

    /**
     * 远程打开单元设备门禁
     * @param communityId 小区ID
     * @param indexCode  唯一编码
     * @return
     */
    AjaxResult openDoorControl(Long communityId, String indexCode);

    /**
     * 根据条件查询监控点集合
     * @param apiCamera
     * @return {@link List< CameraVO>}
     * @ver v1.0.0
     */
    List<CameraVO> selectCameraList(ApiCamera apiCamera);

    /**
     * 获取监控视频流
     * @param apiCamera
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    AjaxResult selectCameraUrl(ApiCamera apiCamera);
}
