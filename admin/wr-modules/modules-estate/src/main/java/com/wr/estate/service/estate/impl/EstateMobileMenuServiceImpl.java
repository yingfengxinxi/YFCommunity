package com.wr.estate.service.estate.impl;

import com.wr.estate.mapper.estate.EstateMobileMenuMapper;
import com.wr.estate.service.estate.EstateMobileMenuService;
import com.wr.remote.domain.EstateMobileMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端菜单表
 */
@Service
public class EstateMobileMenuServiceImpl implements EstateMobileMenuService {

    @Autowired
    private EstateMobileMenuMapper estateMobileMenuMapper;

    @Override
    public List<EstateMobileMenu> getList(EstateMobileMenu estateMobileMenu) {
        return estateMobileMenuMapper.getList(estateMobileMenu);
    }

    @Override
    public EstateMobileMenu getById(Long estateMenuId) {
        return estateMobileMenuMapper.getById(estateMenuId);
    }

    @Override
    public Integer getByNameCount(EstateMobileMenu estateMobileMenu) {
        return estateMobileMenuMapper.getByNameCount(estateMobileMenu);
    }

    @Override
    public int insert(EstateMobileMenu estateMobileMenu) {
        return estateMobileMenuMapper.insert(estateMobileMenu);
    }

    @Override
    public int update(EstateMobileMenu estateMobileMenu) {
        return estateMobileMenuMapper.update(estateMobileMenu);
    }

    @Override
    public void delete(Long estateMenuId) {
        estateMobileMenuMapper.delete(estateMenuId);
    }
}
