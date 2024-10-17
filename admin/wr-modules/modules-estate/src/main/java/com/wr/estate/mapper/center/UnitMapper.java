package com.wr.estate.mapper.center;

import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.center.vo.UnitInfoVo;
import com.wr.remote.estate.center.vo.UnitUpdateVO;
import com.wr.remote.estate.center.vo.UnitVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 19:55:38
 * @Desc: 楼栋单元dao
 */
public interface UnitMapper {

    /**
     * 批量新增单元
     *
     * @param units 单元批量数据
     * @author yx
     * @date 2022/10/12 17:00
     */
    void insertBatch(List<Unit> units);

    void insert(Unit unit);

    /**
     * @param buildingId
     * @param unitName
     * @param communityId
     */
    Long getUnitNameId(@Param("buildingId") Long buildingId, @Param("unitName") String unitName, @Param("communityId") Long communityId);

    /**
     * 根据id修改单元
     *
     * @param updateVO 修改信息
     * @author yx
     * @date 2022/10/13 14:01
     */
    void updateUnitById(UnitUpdateVO updateVO);

    /**
     * 删除单元
     *
     * @param unitId 单元id
     * @author yx
     * @date 2022/10/13 14:40
     */
    void deleteByUnitId(Long unitId);

    /**
     * 根据楼栋id删除单元信息
     *
     * @param buildingId 楼栋id
     * @author yx
     * @date 2022/10/13 14:45
     */
    void deleteByBuildingId(Long buildingId);

    /**
     * 根据楼栋id查询单元列表
     *
     * @param buildingId 楼栋id
     * @return java.util.List<com.wr.remote.business.center.Unit>
     * @author yx
     * @date 2022/10/13 16:15
     */
    List<Unit> listByBuildingId(Long buildingId);

    /**
     * 根据小区id查询单元列表
     *
     * @param communityId 小区id
     * @return java.util.List<com.wr.estate.entity.vo.UnitVO>
     * @author yx
     * @date 2022/10/13 20:33
     */
    List<UnitVO> listByCommunityId(Long communityId);

    /**
     * 根据id查询单元详情
     *
     * @param unitId 单元id
     * @return com.wr.remote.business.center.Unit
     * @author yx
     * @date 2022/10/13 16:45
     */
    Unit getDetailById(Long unitId);

    /**
     * @param unitId
     * @return
     */
    UnitInfoVo getByUnitIdDetail(@Param("unitId") Long unitId);
}

