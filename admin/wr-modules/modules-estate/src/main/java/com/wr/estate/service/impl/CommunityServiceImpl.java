package com.wr.estate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.service.CommunityService;
import com.wr.remote.domain.Community;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.center.vo.RoomTreeVO;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:13:08
 * @Desc:
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<Community> selectCommunityList(Long estateId) {
        if (null == estateId) {
            return Lists.newArrayList();
        }
        return communityMapper.selectCommunityList(estateId);
    }

    @Override
    public List<Community> selectCommunityLists(Long estateId) {
        return communityMapper.selectCommunityList(estateId);
    }

    /**
     * 查询所有小区
     *
     * @return 所有小区信息
     */
    @Override
    public List<Community> selectAllCommunityList() {
        return communityMapper.selectAllCommunityList();
    }

    @Override
    public List<Building> selectBuildingList(Long communityId) {

        return communityMapper.selectBuildingList(communityId);
    }

    @Override
    public List<Building> getPropertyFeeBuilding(Long communityId) {

        return communityMapper.getPropertyFeeBuilding(communityId);
    }

    @Override
    public List<Unit> selectUnitList(Long buildingId) {

        return communityMapper.selectUnitList(buildingId);
    }

    @Override
    public String getCommunityName(Long communityId) {
        return communityMapper.getCommunityName(communityId);
    }

    @Override
    public List<Room> selectRoomList(Long unitId) {

        return communityMapper.selectRoomList(unitId);
    }

    @Override
    public List<Owner> selectOwnerList(Owner owner) {

        return communityMapper.selectOwnerList(owner);
    }

    @Override
    public List<Tenant> selectTenantList(Tenant tenant) {
        if (null == tenant.getCommunityId() && null == tenant.getBuildingId()
                && null == tenant.getUnitId() && null == tenant.getRoomId()) {
            return Lists.newArrayList();
        }
        return communityMapper.selectTenantList(tenant);
    }

    /**
     * 根据用户ID查询小区楼栋tree数据
     *
     * @param estateId 物业id
     * @return java.util.List<com.wr.estate.entity.vo.RoomTreeVO>
     * @author yx
     * @date 2022/10/11 17:12
     */
    @Override
    public List<RoomTreeVO> treeRoom(Long estateId) {
        List<RoomTreeVO> roomTreeVOS = communityMapper.treeRoom(estateId);
//        if(CollectionUtils.isNotEmpty(roomTreeVOS)){
//            for (RoomTreeVO roomTreeVO : roomTreeVOS) {
//                List<RoomTreeVO> childrens = roomTreeVO.getChildren();
//                if(CollectionUtils.isNotEmpty(childrens)){
//
//
//                    Comparator comparing = Collator.getInstance(Locale.CHINA);
//                    childrens = childrens.stream().distinct().sorted((p1,p2) ->
//                            comparing.compare(p1.getName(),p2.getName())
//                    ).collect(Collectors.toList());
//                    roomTreeVO.setChildren(childrens);
//                    //childrens = childrens.stream().sorted(Comparator.comparing()comparing(roomTreeVO::getChildren)).collect(Collectors.toList());
//                }
//            }
//        }

        return roomTreeVOS;
    }

    @Override
    public Long getByEstateId(Long estateId) {
        return communityMapper.getByEstateId(estateId);
    }

    @Override
    public Owner selectOwner(Owner owner) {
        return communityMapper.selectOwner(owner);
    }


}
