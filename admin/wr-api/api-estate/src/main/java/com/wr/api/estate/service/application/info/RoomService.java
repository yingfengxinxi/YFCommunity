package com.wr.api.estate.service.application.info;

import com.wr.api.estate.entity.vo.application.info.BuildingInfoVo;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomVo;
import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 11:04:22
 * @Desc: 业务层
 */
public interface RoomService {

    /**
     * 楼栋总数
     * @param communityId
     * @return
     */
    int getBuildingCount(Long communityId);

    /**
     * 楼栋列表
     * @param communityId
     * @return
     */
    List<RoomVo> getBuildingList (Long communityId);

    /**
     * 房间列表
     * @param roomVo
     * @return
     */
    List<RoomVo> getRoomList (RoomVo roomVo);

    /**
     * 房主信息
     * @param roomId
     * @return
     */
    OwnerVo getOwner(Long roomId);

    /**
     * 租客列表
     * @param tenantVo
     * @return
     */
    AjaxResult getBuildingInfo(TenantVo tenantVo);

    /**
     * 楼宇概况
     * @param roomVo
     * @return
     */
    RoomVo getStatusCount(RoomVo roomVo);

    void updateByRoomIdRoomStatus(Long roomId, String roomStatus);
}
