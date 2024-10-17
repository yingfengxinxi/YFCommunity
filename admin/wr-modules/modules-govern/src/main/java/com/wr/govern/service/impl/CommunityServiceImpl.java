package com.wr.govern.service.impl;

import com.wr.govern.mapper.CommunityMapper;
import com.wr.govern.service.CommunityService;
import com.wr.remote.domain.Community;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CommunityHik selectCommunityHikById(Long communityId) {

        return communityMapper.selectHikByCommunityId(communityId);
    }

    @Override
    public List<Community> selectCommunityList(Long estateId) {
        if(null == estateId){
            return Lists.newArrayList();
        }
        return communityMapper.selectCommunityList(estateId);
    }

    @Override
    public List<Building> selectBuildingList(Long communityId) {

        return communityMapper.selectBuildingList(communityId);
    }

    @Override
    public List<Unit> selectUnitList(Long buildingId) {

        return communityMapper.selectUnitList(buildingId);
    }

    @Override
    public List<Room> selectRoomList(Long unitId) {

        return communityMapper.selectRoomList(unitId);
    }

    @Override
    public List<Owner> selectOwnerList(Owner owner) {
        if(null == owner.getCommunityId() && null == owner.getBuildingId()
                && null == owner.getUnitId() && null == owner.getRoomId()){
            return Lists.newArrayList();
        }
        return communityMapper.selectOwnerList(owner);
    }

    @Override
    public List<Tenant> selectTenantList(Tenant tenant) {
        if(null == tenant.getCommunityId() && null == tenant.getBuildingId()
                && null == tenant.getUnitId() && null == tenant.getRoomId()){
            return Lists.newArrayList();
        }
        return communityMapper.selectTenantList(tenant);
    }
    
}
