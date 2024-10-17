package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.entity.vo.application.info.VehicleVo;
import com.wr.api.estate.service.application.info.TenantService;
import com.wr.api.estate.service.application.info.VehicleService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/vehicle")
public class VehicleController extends BaseController {
    @Autowired
    private VehicleService vehicleService;

    /**
     * 列表
     * @param vehicleVo
     * @return
     */

    @GetMapping("/list")
    public TableDataInfo list(VehicleVo vehicleVo) {
        startPage();
        List<VehicleVo> list = vehicleService.getList(vehicleVo);
        return getDataTable(list);
    }

    /**
     * 详情
     * @param vehicleVo
     * @return
     */

    @GetMapping("/detail")
    public AjaxResult getInfo(VehicleVo vehicleVo) {
        return AjaxResult.success(vehicleService.getDetail(vehicleVo));
    }

    /**
     * 车辆总数
     * @param communityId
     * @return
     */

    @GetMapping("/count/{communityId}")
    public AjaxResult getCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(vehicleService.getVehicleCount(communityId));
    }
}
