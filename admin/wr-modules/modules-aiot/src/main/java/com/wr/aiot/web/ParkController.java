package com.wr.aiot.web;

import com.wr.aiot.service.ParkService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Entrance;
import com.wr.remote.aiot.Park;
import com.wr.remote.aiot.Roadway;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 10:17:51
 * @Desc: 停车场管理
 */
@RestController
@RequestMapping("/park")
@Api(tags = "停车场管理")
public class ParkController extends BaseController {
    @Autowired
    private ParkService parkService;


    @ApiOperation("分页列表")
    @GetMapping("/list")
    public TableDataInfo list(Park park) {
        startPage();
        List<Park> list = parkService.selectParkList(park);
        return getDataTable(list);
    }

    /**
     * 设备同步
     */
    @Log(title = "停车场信息", businessType = BusinessType.INSERT)
    @PostMapping("/sync_data/{communityId}")
    public AjaxResult syncData(@PathVariable Long communityId) {

        try {
            return parkService.syncParkData(communityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查询数据
     */
    @GetMapping(value = "/{parkId}")
    public AjaxResult getInfo(@PathVariable Long parkId) {
        Park park = parkService.selectParkById(parkId);
        if (null != park) {
            List<Entrance> entranceList = parkService.selectEntranceByPark(park.getIndexCode());
            park.setEntranceList(entranceList);
            for (Entrance entrance : entranceList) {
                List<Roadway> roadwayList = parkService.selectRoadwayByEntrance(entrance.getIndexCode());
                for (Roadway roadway : roadwayList) {
                    roadway.setBarrierList(parkService.selectBarrierByRoadway(roadway.getIndexCode()));
                }
                entrance.setRoadwayList(roadwayList);
            }
        }
        return AjaxResult.success(park);
    }

    /**
     * 更新停车场信息
     */
    @Log(title = "停车场信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Park park) {
        park.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(parkService.updatePark(park));
    }

    /**
     * 订阅停车场出入事件
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "停车场信息", businessType = BusinessType.OTHER)
    @PostMapping("/subscribe/{communityId}")
    public AjaxResult subscribe(@PathVariable Long communityId) {
        return AjaxResult.error("演示模式！！");
        //return parkService.subscribeEvent(communityId);
    }

    /**
     * 停车场出入事件取消订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "停车场信息", businessType = BusinessType.OTHER)
    @PostMapping("/unsubscribe/{communityId}")
    public AjaxResult unsubscribe(@PathVariable Long communityId) {
        return AjaxResult.error("演示模式！！");
        //return parkService.unsubscribeEvent(communityId);
    }

    /**
     * 批量设置停车场支付二维码
     */
    @Log(title = "停车场信息", businessType = BusinessType.UPDATE)
    @PutMapping("/qr")
    public AjaxResult qrCode(@RequestBody Park park) {

        return toAjax(parkService.batchUpdatePayQr(park));
    }

    @GetMapping(value = "/all_park/{communityId}")
    public AjaxResult allPark(@PathVariable Long communityId) {
        Park park = new Park();
        park.setCommunityId(communityId);
        List<Park> parkList = parkService.selectParkList(park);
        return AjaxResult.success(parkList);
    }

    @GetMapping(value = "/all_entrance/{parkIndex}")
    public AjaxResult allEntrance(@PathVariable String parkIndex) {
        List<Entrance> entranceList = parkService.selectEntranceByPark(parkIndex);
        return AjaxResult.success(entranceList);
    }

}
