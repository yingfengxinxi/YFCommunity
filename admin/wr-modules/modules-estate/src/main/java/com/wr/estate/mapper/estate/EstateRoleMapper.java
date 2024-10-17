package com.wr.estate.mapper.estate;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.EstateMobileMenu;
import com.wr.remote.domain.EstateRole;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色表
 */

public interface EstateRoleMapper {


    /**
     * @param estateRole
     * @return
     */
    List<EstateRole> getList(EstateRole estateRole);

    /**
     * @param estateRoleId
     * @return
     */
    EstateRole getById(@Param("estateRoleId") Long estateRoleId);

    /**
     * @param estateId
     * @param estateRoleName
     * @param estateRoleId
     * @return
     */
    Integer getByNameCount(@Param("estateId") Long estateId, @Param("estateRoleName") String estateRoleName, @Param("estateRoleId") Long estateRoleId);

    /**
     * @param estateRole
     * @return
     */
    int insert(EstateRole estateRole);

    /**
     * @param estateRole
     * @return
     */
    int update(EstateRole estateRole);

    /**
     * @param estateRoleId
     */
    void delete(@Param("estateRoleId") Long estateRoleId);

}
