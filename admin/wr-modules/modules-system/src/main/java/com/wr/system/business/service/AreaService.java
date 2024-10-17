package com.wr.system.business.service;

import com.wr.system.business.domain.AreaVo;
import com.wr.system.business.domain.TreeSelectVo;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-15 15:13:05
 * @Desc:
 */
public interface AreaService {
    /**
     * 查询数据列表
     * @param area
     * @return
     */
    List<AreaVo> selectAreaList(AreaVo area);

    /**
     * 校验地区名称是否唯一
     * @param area
     * @return
     */
    String checkAreaNameUnique(AreaVo area);

    /**
     * 添加地区
     * @param area
     * @return
     */
    Integer insertArea(AreaVo area);

    /**
     * 根据id查询数据
     * @param areaId
     * @return
     */
    AreaVo selectAreaById(Long areaId);

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
    Boolean hasChildByAreaId(Long areaId);

    /**
     * 是否存在小区
     * @param areaId
     * @return
     */
    Boolean checkAreaExistCommunity(Long areaId);

    /**
     * 删除
     * @param areaId
     * @return
     */
    Integer deleteAreaById(Long areaId);

    /**
     * 地区树选择
     * @param areas
     * @return
     */
    List<TreeSelectVo> buildAreaTreeSelectVo(List<AreaVo> areas);
}
