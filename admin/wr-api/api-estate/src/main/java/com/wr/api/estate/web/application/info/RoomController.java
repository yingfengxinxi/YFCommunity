package com.wr.api.estate.web.application.info;

import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomVo;
import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.service.application.info.OwnerService;
import com.wr.api.estate.service.application.info.RoomService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 14:07:17
 * @Desc:
 */
@RestController
@RequestMapping("/v2/room")
public class RoomController extends BaseController {
    @Autowired
    private RoomService roomService;

    /**
     * 楼栋列表
     * @param communityId
     * @return
     */

    @GetMapping("/buildingList/{communityId}")
    public AjaxResult list(@PathVariable("communityId") Long communityId) {
        List<RoomVo> buildingList = roomService.getBuildingList(communityId);
        return AjaxResult.success(buildingList);
    }

    /**
     * 楼宇概况
     * @param roomVo
     * @return
     */

    @GetMapping("/countDetail")
    public AjaxResult getCount(RoomVo roomVo) {
        return AjaxResult.success(roomService.getStatusCount(roomVo));
    }

    /**
     * 楼栋总数
     * @param communityId
     * @return
     */
    @GetMapping("/count/{communityId}")
    public AjaxResult getCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(roomService.getBuildingCount(communityId));
    }

    /**
     * 房间列表
     * @param roomVo
     * @return
     */
    @GetMapping("/roomList")
    public AjaxResult getRoomList(RoomVo roomVo) {
        List<RoomVo> roomList = roomService.getRoomList(roomVo);
        return AjaxResult.success(roomList);
    }
    /**
     * 房主信息
     * @param roomId
     * @return
     */

    @GetMapping("/ownerInfo/{roomId}")
    public AjaxResult getOwner(@PathVariable("roomId") Long roomId) {
        return AjaxResult.success(roomService.getOwner(roomId));
    }

    /**
     * 租客/业主信息
     * @param tenantVo
     * @return
     */
    @GetMapping("/getBuildingInfo")
    public AjaxResult getBuildingInfo(TenantVo tenantVo) {

        return AjaxResult.success(roomService.getBuildingInfo(tenantVo));
    }
}
