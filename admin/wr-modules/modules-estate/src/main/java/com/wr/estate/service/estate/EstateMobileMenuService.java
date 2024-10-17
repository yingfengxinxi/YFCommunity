package com.wr.estate.service.estate;

import com.wr.remote.domain.EstateMobileMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端菜单表
 */

public interface EstateMobileMenuService {

    /**
     * @param estateMobileMenu
     * @return
     */
    List<EstateMobileMenu> getList(EstateMobileMenu estateMobileMenu);

    /**
     * @param estateMenuId
     * @return
     */
    EstateMobileMenu getById(Long estateMenuId);

    /**
     * @param estateMenuName
     * @param estateMenuId
     * @return
     */
    Integer getByNameCount(EstateMobileMenu estateMobileMenu);

    /**
     * @param estateMobileMenu
     * @return
     */
    int insert(EstateMobileMenu estateMobileMenu);

    /**
     * @param estateMobileMenu
     * @return
     */
    int update(EstateMobileMenu estateMobileMenu);

    /**
     * @param estateMenuId
     */
    void delete(Long estateMenuId);

}
