package com.wr.estate.mapper.estate;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.EstateMobileRoleMenu;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端角色菜单中间表
 */
public interface EstateMobileRoleMenuMapper {

    /**
     * @param estateRoleId
     */
    List<EstateMobileRoleMenu> getByRoleIdList(@Param("estateRoleId") Long estateRoleId);

    /**
     * @param estateMobileRoleMenu
     */
    void insert(EstateMobileRoleMenu estateMobileRoleMenu);

    /**
     * @param estateRoleId
     */
    void deleteByEstateRoleId(@Param("estateRoleId") Long estateRoleId);

    /**
     * @param estateMenuId
     * @return
     */
    Integer getByMenuIdRoleCount(@Param("estateMenuId") Long estateMenuId);
}
