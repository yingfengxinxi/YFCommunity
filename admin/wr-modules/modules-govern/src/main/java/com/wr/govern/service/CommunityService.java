package com.wr.govern.service;

import com.wr.remote.domain.Community;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:09:07
 * @Desc:
 */
public interface CommunityService {

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return {@link CommunityHik}
     * @ver v1.0.0
     */
    CommunityHik selectCommunityHikById(Long communityId);

    /**
     * 根据当前登录人所在物业查询小区
     * @param estateId 用户当前所在物业id
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    List<Community> selectCommunityList(Long estateId);

    /**
     * 根据小区id查询楼栋
     * @param communityId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Building> selectBuildingList(Long communityId);

    /**
     * 根据楼栋id查询单元
     * @param buildingId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Unit> selectUnitList(Long buildingId);

    /**
     * 根据单元id查询房间
     * @param unitId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Room> selectRoomList(Long unitId);

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询业主
     * @param owner
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Owner> selectOwnerList(Owner owner);

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询租客
     * @param tenant
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Tenant> selectTenantList(Tenant tenant);

}
