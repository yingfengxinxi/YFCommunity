package com.wr.api.owner.web.serve;

import com.wr.api.owner.service.serve.VisualService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Visual;
import com.wr.remote.aiot.VisualDeviceControl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/4/15 12:43
 */
@RestController
@RequestMapping("v2/visual")
@Api(tags = "业主移动端-返控单元楼设备")
public class VisualController extends BaseController {


    @Autowired
    private VisualService visualService;

    /**
     * @param visual
     * @return
     */
    @ApiOperation("设备列表")
    @GetMapping("getList")
    public AjaxResult getList(@RequestParam("communityId") Long communityId, @RequestParam("buildingId") Long buildingId, @RequestParam("unitId") Long unitId) {

        return AjaxResult.success(visualService.getList(communityId, buildingId, unitId));
    }

    /**
     * @param visualDeviceControl
     * @return
     */
    @ApiOperation("单元楼开门")
    @PostMapping("visualDeviceControl")
    public AjaxResult visualDeviceControl(@RequestBody VisualDeviceControl visualDeviceControl) {
        try {
            //visualDeviceControl.setDeviceIndexCode("b823098e4d0047488bd2536af635af26");
            return visualService.visualDeviceControl(visualDeviceControl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
