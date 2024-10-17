package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.ApiBuildingVo;
import com.wr.api.owner.mapper.serve.BuildingMapper;
import com.wr.api.owner.service.serve.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼栋关联
 * @Author: lvzy
 * @Date: 2022-11-14 17:23:40
 * @Desc: 楼栋关联
 */
@Service
@AllArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingMapper buildingMapper;

    @Override
    public List<ApiBuildingVo> buildingList(Long communityId) {
        return buildingMapper.buildingList(communityId);
    }

    @Override
    public List<ApiBuildingVo> unitList(Long buildingId) {
        return buildingMapper.unitList(buildingId);
    }

    @Override
    public List<ApiBuildingVo> roomList(Long unitId) {
        return buildingMapper.roomList(unitId);
    }

    @Override
    public List<ApiBuildingVo> ownerList(Long roomId) {
        return buildingMapper.ownerList(roomId);
    }
}
