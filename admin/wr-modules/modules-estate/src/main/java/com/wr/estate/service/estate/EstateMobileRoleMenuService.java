package com.wr.estate.service.estate;

import com.wr.remote.domain.EstateMobileRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端角色菜单中间表
 */
public interface EstateMobileRoleMenuService {

    /**
     * @param estateRoleId
     */
    List<EstateMobileRoleMenu> getByRoleIdList(Long estateRoleId);

    /**
     * @param estateMobileRoleMenuList
     */
    void insert(List<EstateMobileRoleMenu> estateMobileRoleMenuList);

    /**
     * @param estateRoleId
     */
    void deleteByEstateRoleId(Long estateRoleId);

    /**
     * @param estateMenuId
     * @return
     */
    Integer getByMenuIdRoleCount(Long estateMenuId);
}
