package com.wr.estate.service.center;

import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.vo.BuildingInsertVO;
import com.wr.remote.estate.center.vo.BuildingUpdateVO;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:09:07
 * @Desc:
 */
public interface BuildingService {

    /**
     * 批量新增楼宇
     *
     * @param insertVO 批量数据
     * @author yx
     * @date 2022/10/12 11:33
     */
    void insertBatch(BuildingInsertVO insertVO);

    /**
     *
     * @param communityId
     * @return
     */
    List<Building> getCommunityIdBuildingName(Long communityId);

    /**
     * 根据楼宇id修改楼栋信息
     *
     * @param updateVO 更新信息
     * @author yx
     * @date 2022/10/13 13:52
     */
    void updateBuildingById(BuildingUpdateVO updateVO);

    /**
     * 根据楼栋id删除楼栋信息
     *
     * @param buildingId 楼栋id
     * @author yx
     * @date 2022/10/13 14:34
     */
    void delete(Long buildingId);
}
