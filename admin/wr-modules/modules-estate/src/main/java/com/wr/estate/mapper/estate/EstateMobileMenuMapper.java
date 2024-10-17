package com.wr.estate.mapper.estate;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.EstateMobileMenu;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端菜单表
 */

public interface EstateMobileMenuMapper {

    /**
     * @param estateMobileMenu
     * @return
     */
    List<EstateMobileMenu> getList(EstateMobileMenu estateMobileMenu);

    /**
     * @param estateMenuId
     * @return
     */
    EstateMobileMenu getById(@Param("estateMenuId") Long estateMenuId);

    /**
     *
     * @param estateMobileMenu
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
     *
     * @param estateMenuId
     */
    void delete(@Param("estateMenuId") Long estateMenuId);
}
