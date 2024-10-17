package com.wr.estate.mapper.estate;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.EstateStaffRole;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色中间表
 */
public interface EstateStaffRoleMapper {

    /**
     * @param staffId
     * @return
     */
    List<EstateStaffRole> getByStaffIdList(@Param("staffId") Long staffId);

    /**
     * @param estateStaffRole
     */
    void insert(EstateStaffRole estateStaffRole);

    /**
     * @param staffId
     */
    void deleteByStaffId(@Param("staffId") Long staffId);

    /**
     * @param estateRoleId
     * @return
     */
    Integer getByEstateRoleIdCount(@Param("estateRoleId") Long estateRoleId);
}
