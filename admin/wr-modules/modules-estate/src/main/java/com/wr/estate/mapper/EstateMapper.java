package com.wr.estate.mapper;

import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysDept;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 21:04:56
 * @Desc: estate数据层
 */
public interface EstateMapper {

    /**
     * 根据小区id查询物业
     * @param communityId 小区id
     * @return {@link Estate}
     * @ver v1.0.0
     */
    Estate selectEstateByCommunityId(Long communityId);

    /**
     * 根据当前登录人所在id查询部门
     * @param deptId 登录人所在id
     * @return com.wr.remote.domain.SysDept
     */
    SysDept selectDeptByDeptId(Long deptId);

    /**
     * 根据部门id查询对应的物业id
     * @param deptId 部门id
     * @return {@link Long}
     * @ver v1.0.0
     */
    Long selectEstateIdByDeptId(Long deptId);
}
