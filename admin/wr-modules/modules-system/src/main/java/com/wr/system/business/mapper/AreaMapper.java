package com.wr.system.business.mapper;

import com.wr.system.business.domain.AreaVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-15 15:11:53
 * @Desc: 地区管理数据层
 */
public interface AreaMapper {
    /**
     * 查询数据列表
     * @param area
     * @return
     */
    List<AreaVo> selectAreaList(AreaVo area);

    /**
     * 根据parentId 名称查询数据
     * @param areaName
     * @param parentId
     * @return
     */
    AreaVo checkAreaNameUnique(@Param("areaName") String areaName, @Param("parentId") Long parentId);

    /**
     * 根据id擦汗寻数据
     * @param areaId
     * @return
     */
    AreaVo selectAreaById(Long areaId);

    /**
     * 添加数据
     * @param area
     * @return
     */
    Integer insertArea(AreaVo area);

    /**
     * 查询子集
     * @param areaId
     * @return
     */
    List<AreaVo> selectChildrenAreaById(Long areaId);

    /**
     * 修改子集
     * @param children
     * @return
     */
    Integer updateAreaChildren(@Param("children") List<AreaVo> children);

    /**
     * 修改数据
     * @param area
     * @return
     */
    Integer updateArea(AreaVo area);

    /**
     * 是否存在下级
     * @param areaId
     * @return
     */
    Integer hasChildByAreaId(Long areaId);

    /**
     * 是否存在小区
     * @param areaId
     * @return
     */
    Integer checkAreaExistCommunity(Long areaId);

    /**
     * 删除数据
     * @param areaId
     * @return
     */
    Integer deleteAreaById(Long areaId);
}
