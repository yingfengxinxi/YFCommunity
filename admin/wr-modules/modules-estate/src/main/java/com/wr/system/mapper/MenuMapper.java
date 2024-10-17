package com.wr.system.mapper;

import com.wr.remote.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 数据层
 * 
 * @author wr
 */
public interface MenuMapper {
    /**
     * 根据版本查询菜单
     * @param userId
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuListByVer(Long userId);

    /**
     * 根据角色查询菜单
     * @param userId
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuListByRole(Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

}
