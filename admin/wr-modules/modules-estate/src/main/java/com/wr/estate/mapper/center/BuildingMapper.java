package com.wr.estate.mapper.center;

import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.vo.BuildingUpdateVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 19:55:38
 * @Desc: 楼栋dao
 */
public interface BuildingMapper {

    /**
     * 批量新增楼宇
     *
     * @param list 批量数据
     * @author yx
     * @date 2022/10/12 11:33
     */
    void insertBatch(List<Building> list);

    void insert(Building building);

    /**
     * 根据楼栋id获取楼栋详情
     *
     * @param buildingId 楼栋id
     * @return java.util.List<com.wr.remote.business.center.Building>
     * @author yx
     * @date 2022/10/12 17:39
     */
    Building getDetailByBuildingId(Long buildingId);

    /**
     *
     * @param buildingName
     * @param communityId
     * @return
     */
    Long getBuildingNameId(@Param("buildingName") String buildingName, @Param("communityId") Long communityId);

    /**
     * 根据单元id获取楼栋详情
     *
     * @param unitId 单元id
     * @return com.wr.remote.business.center.Building
     * @author yx
     * @date 2022/10/13 13:18
     */
    Building getDetailByUnitId(Long unitId);

    /**
     * 根据楼宇id修改楼栋信息
     *
     * @param updateVO 更新信息
     * @author yx
     * @date 2022/10/13 13:52
     */
    void updateBuildingById(BuildingUpdateVO updateVO);

    /**
     * 删除楼栋
     *
     * @param buildingId
     * @author yx
     * @date 2022/10/13 14:37
     */
    void deleteById(Long buildingId);

    /**
     * 根据小区id查询所有楼栋
     *
     * @param communityId 小区id
     * @return java.util.List<com.wr.remote.business.center.Building>
     * @author yx
     * @date 2022/10/13 15:51
     */
    List<Building> listByCommunityId(Long communityId);

    /**
     * @param communityId
     * @return
     */
    List<Building> getCommunityIdBuildingName(@Param("communityId") Long communityId);
}

