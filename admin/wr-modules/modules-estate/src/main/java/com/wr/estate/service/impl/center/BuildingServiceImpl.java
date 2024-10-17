package com.wr.estate.service.impl.center;

import com.wr.common.core.exception.ServiceException;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.BuildingMapper;
import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.center.UnitMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.service.center.BuildingService;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.vo.BuildingInsertVO;
import com.wr.remote.estate.center.vo.BuildingUpdateVO;
import com.wr.remote.domain.SysUser;
import io.jsonwebtoken.lang.Collections;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:13:08
 * @Desc:
 */
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private StaffMapper staffMapper;

    /**
     * 批量新增楼宇
     *
     * @param insertVO 批量数据
     * @author yx
     * @date 2022/10/12 11:33
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(BuildingInsertVO insertVO) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        List<Building> buildings = new ArrayList<>();
        List<Integer> excludeBuildingNums = insertVO.getExcludeBuildingNums();
        // 查询现有小区所有楼栋
        List<Building> buildingList = buildingMapper.listByCommunityId(insertVO.getCommunityId());
        List<String> buildingNames = buildingList.stream().map(Building::getBuildingName).collect(Collectors.toList());
        // 生成批量数据
        for (int i = insertVO.getStartBuildingNum(); i <= insertVO.getEndBuildingNum(); i++) {
            // 排除楼宇
            if (excludeBuildingNums != null && excludeBuildingNums.contains(i)) {
                continue;
            }
            String buildingName = insertVO.getBuildingName() + "-" + i + "#";
            if (buildingNames.contains(buildingName)) {
                continue;
            }
            Building building = new Building();
            building.setCommunityId(insertVO.getCommunityId());
            building.setBuildingName(buildingName);
            building.setCreateBy(sysUser.getNickName());
            building.setUpdateBy(sysUser.getNickName());
            buildings.add(building);
        }
        if (!buildings.isEmpty()) {
            this.buildingMapper.insertBatch(buildings);
        }
    }

    /**
     * @param communityId
     * @return
     */
    @Override
    public List<Building> getCommunityIdBuildingName(Long communityId) {
        List<Building> buildingList = buildingMapper.getCommunityIdBuildingName(communityId);
        if (CollectionUtils.isNotEmpty(buildingList)) {
            for (Building building : buildingList) {
                int count = staffMapper.getBuildingCount(String.valueOf(building.getBuildingId()), communityId);
                building.setFlag(String.valueOf(count));
            }
        }
        return buildingList;
    }

    /**
     * 修改楼宇信息
     *
     * @param updateVO 修改信息
     * @author yx
     * @date 2022/10/13 13:57
     */
    @Override
    public void updateBuildingById(BuildingUpdateVO updateVO) {
        updateVO.setUpdateBy(SecurityUtils.getUsername());
        buildingMapper.updateBuildingById(updateVO);
    }

    /**
     * 根据楼栋id删除楼栋信息
     *
     * @param buildingId 楼栋id
     * @author yx
     * @date 2022/10/13 14:34
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long buildingId) {
        List<Room> rooms = roomMapper.listByBuildingId(buildingId);
        if (!Collections.isEmpty(rooms)) {
            throw new ServiceException("删除失败，楼栋下存在房屋！");
        }
        buildingMapper.deleteById(buildingId);
        unitMapper.deleteByBuildingId(buildingId);
    }
}
