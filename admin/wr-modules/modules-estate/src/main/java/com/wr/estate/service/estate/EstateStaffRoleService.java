package com.wr.estate.service.estate;

import com.wr.remote.domain.EstateStaffRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色中间表
 */
public interface EstateStaffRoleService {

    /**
     * @param staffId
     * @return
     */
    List<EstateStaffRole> getByStaffIdList(Long staffId);

    /**
     * @param estateStaffRoleList
     */
    void insert(List<EstateStaffRole> estateStaffRoleList);

    /**
     * @param staffId
     */
    void deleteByStaffId(Long staffId);

    Integer getByEstateRoleIdCount(Long estateRoleId);
}
