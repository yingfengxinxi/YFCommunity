package com.wr.estate.service.estate.impl;

import com.wr.estate.mapper.estate.EstateMobileRoleMenuMapper;
import com.wr.estate.service.estate.EstateMobileRoleMenuService;
import com.wr.remote.domain.EstateMobileRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端角色菜单中间表
 */
@Service
public class EstateMobileRoleMenuServiceImpl implements EstateMobileRoleMenuService {

    @Autowired
    private EstateMobileRoleMenuMapper estateMobileRoleMenuMapper;

    @Override
    public List<EstateMobileRoleMenu> getByRoleIdList(Long estateRoleId) {
        return estateMobileRoleMenuMapper.getByRoleIdList(estateRoleId);
    }

    @Override
    public void insert(List<EstateMobileRoleMenu> estateMobileRoleMenuList) {
        Long estateRoleId = estateMobileRoleMenuList.get(0).getEstateRoleId();
        this.deleteByEstateRoleId(estateRoleId);
        for (EstateMobileRoleMenu estateMobileRoleMenu : estateMobileRoleMenuList) {
            estateMobileRoleMenuMapper.insert(estateMobileRoleMenu);
        }

    }

    @Override
    public void deleteByEstateRoleId(Long estateRoleId) {
        estateMobileRoleMenuMapper.deleteByEstateRoleId(estateRoleId);
    }

    @Override
    public Integer getByMenuIdRoleCount(Long estateMenuId) {
        return estateMobileRoleMenuMapper.getByMenuIdRoleCount(estateMenuId);
    }
}
