package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.HomeMenuMapper;
import com.wr.api.estate.service.application.serve.HomeMenuService;
import com.wr.remote.domain.HomeMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:31
 */
@Service
public class HomeMenuServiceImpl extends ServiceImpl<HomeMenuMapper,HomeMenu> implements HomeMenuService {

    @Autowired
    private HomeMenuMapper homeMenuMapper;

    @Override
    public List<HomeMenu> selectList() {
        return homeMenuMapper.selectListByestateId();
    }
}
