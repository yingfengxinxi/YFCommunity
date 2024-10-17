package com.wr.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.system.domain.HomeMenu;
import com.wr.system.mapper.HomeMenuMapper;
import com.wr.system.service.HomeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:31
 */
@Service
public class HomeMenuServiceImpl extends ServiceImpl<HomeMenuMapper, HomeMenu> implements HomeMenuService {

    @Autowired
    private HomeMenuMapper homeMenuMapper;

    @Override
    public List<HomeMenu> selectList(HomeMenu homeMenu) {
        return homeMenuMapper.selectListByestateId(homeMenu);
    }
}
