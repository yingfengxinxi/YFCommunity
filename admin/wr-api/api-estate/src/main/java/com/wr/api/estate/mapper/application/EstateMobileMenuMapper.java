package com.wr.api.estate.mapper.application;

import com.wr.remote.domain.EstateMobileMenu;
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
     *
     * @param staffId
     * @return
     */
    List<EstateMobileMenu> getStaffIdAuthMenuList(@Param("staffId")Long staffId);

}
