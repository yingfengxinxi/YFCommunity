package com.wr.api.owner.web.home.location;

import com.wr.api.owner.entity.home.location.ApiCamera;
import com.wr.api.owner.service.home.location.AreaService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 19:07:57
 * @Desc: 区域控制层
 */
@RequestMapping("/v2/Area")
@RestController
public class AreaController  extends BaseController {

    @Autowired
    private AreaService areaService;

    @AuthRequired
    @GetMapping("/communtion")
    public AjaxResult selectAreaList()
    {
        return  AjaxResult.success(areaService.AreaAndcommunityVoList());
    }


    //远程开门获取所有门禁点信息
    @AuthRequired
    @GetMapping("/controlDoor")
    public AjaxResult controlDoor(Long accountId, Long communityId)
    {
        return AjaxResult.success(areaService.selectDoorControl(accountId,communityId));
    }

    //远程开门
    @AuthRequired
    @GetMapping("/openControllerdoor")
    public AjaxResult openControllerdoor(Long communityId,String indexCode)
    {
        return areaService.openDoorControl(communityId,indexCode);
    }

    /**
     * 根据条件查询监控点集合
     * @param apiCamera communityId buildingId unitId 必传一个
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    @AuthRequired
    @GetMapping("/get_camera")
    public AjaxResult getCamera(ApiCamera apiCamera) {
        if(null == apiCamera.getCommunityId() && null == apiCamera.getBuildingId() && null == apiCamera.getUnitId()){
            logger.warn("条件不足，未查询到监控设备");
            return AjaxResult.error("未查询到监控设备");
        }
        return AjaxResult.success(areaService.selectCameraList(apiCamera));
    }

    /**
     * 获取监控视频流
     * @param apiCamera communityId indexCode 必传
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    @AuthRequired
    @GetMapping("/camera_url")
    public AjaxResult cameraUrl(ApiCamera apiCamera) {
        if(null == apiCamera.getCommunityId()){
            logger.warn("小区id为空，不查询");
            return AjaxResult.error("未知的小区");
        }
        if(StringUtils.isEmpty(apiCamera.getIndexCode())){
            logger.warn("监控点唯一标识为空，不查询");
            return AjaxResult.error("请选择设备");
        }
        return areaService.selectCameraUrl(apiCamera);
    }
}
