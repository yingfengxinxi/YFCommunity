package com.wr.api.estate.service.impl.application.info;

import com.wr.api.estate.entity.vo.application.info.BuildingInfoVo;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomVo;
import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.mapper.application.info.RoomMapper;
import com.wr.api.estate.service.application.info.RoomService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 14:17:21
 * @Desc:
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public int getBuildingCount(Long communityId) {
        return roomMapper.getBuildingCount(communityId);
    }

    @Override
    public List<RoomVo> getBuildingList(Long communityId) {
        return roomMapper.getBuildingList(communityId);
    }

    @Override
    public List<RoomVo> getRoomList(RoomVo roomVo) {
        return roomMapper.getRoomList(roomVo);
    }

    @Override
    public OwnerVo getOwner(Long roomId) {
        // 查房主
        OwnerVo ownerVo = roomMapper.getOwner(roomId);
        //查家庭成员
        List<Map<String, Object>> map = roomMapper.getFamilyList(roomId);
        ownerVo.setFamilyList(map);
        return ownerVo;
    }

    @Override
    public AjaxResult getBuildingInfo(TenantVo tenantVo) {

        AjaxResult ajaxResult=new AjaxResult();
        //租客信息
        ajaxResult.put("tenant",roomMapper.getTenantList(tenantVo.getRoomId(), tenantVo.getCommunityId()));
        //业主信息
        ajaxResult.put("owner",roomMapper.getOwnerInfo(tenantVo.getRoomId(), tenantVo.getCommunityId()));

        return ajaxResult;
    }

    @Override
    public RoomVo getStatusCount(RoomVo roomVo) {
        return roomMapper.getStatusCount(roomVo);
    }

    @Override
    public void updateByRoomIdRoomStatus(Long roomId, String roomStatus) {
        roomMapper.updateByRoomIdRoomStatus(roomId,roomStatus);
    }
}
