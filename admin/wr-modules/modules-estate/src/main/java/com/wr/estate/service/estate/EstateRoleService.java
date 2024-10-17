package com.wr.estate.service.estate;

import com.wr.remote.domain.EstateRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色表
 */

public interface EstateRoleService {


    /**
     * @param estateRole
     * @return
     */
    List<EstateRole> getList(EstateRole estateRole);

    /**
     * @param estateRoleId
     * @return
     */
    EstateRole getById(Long estateRoleId);

    /**
     * @param estateId
     * @param estateRoleName
     * @param estateRoleId
     * @return
     */
    Integer getByNameCount(Long estateId, String estateRoleName, Long estateRoleId);

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
    void delete(Long estateRoleId);

}
