package com.wr.estate.mapper;

import com.wr.remote.domain.Community;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.center.vo.RoomTreeVO;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 09:52:46
 * @Desc:
 */
public interface CommunityMapper {

    /**
     * 根据当前登录人所在物业查询小区
     *
     * @param estateId 物业id
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    List<Community> selectCommunityList(Long estateId);

    /**
     * 查询所有小区
     *
     * @return 所有小区信息
     */
    List<Community> selectAllCommunityList();

    /**
     * 根据小区id查询楼栋
     *
     * @param communityId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Building> selectBuildingList(Long communityId);

    List<Building> getPropertyFeeBuilding(Long communityId);

    /**
     * 根据楼栋id查询单元
     *
     * @param buildingId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Unit> selectUnitList(Long buildingId);

    /**
     * @param communityId
     * @return
     */
    String getCommunityName(@Param("communityId") Long communityId);

    /**
     * 根据单元id查询房间
     *
     * @param unitId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Room> selectRoomList(Long unitId);

    /**
     * 根据小区id查询海康配置
     *
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询业主
     *
     * @param owner
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Owner> selectOwnerList(Owner owner);

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询租客
     *
     * @param tenant
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    List<Tenant> selectTenantList(Tenant tenant);

    /**
     * 根据用户ID查询小区楼栋tree数据
     *
     * @param estateId 物业id
     * @return java.util.List<com.wr.estate.entity.vo.RoomTreeVO>
     * @author yx
     * @date 2022/10/11 17:12
     */
    List<RoomTreeVO> treeRoom(Long estateId);

    /**
     * @param estateId
     * @return
     */
    Long getByEstateId(@Param("estateId") Long estateId);

    /**
     * @param communityId
     * @return
     */
    Long getByCommunityIdEstateId(@Param("communityId") Long communityId);


    Long getByCommunityNameId(@Param("communityName") String communityName);

    Owner selectOwner(Owner owner);
}

