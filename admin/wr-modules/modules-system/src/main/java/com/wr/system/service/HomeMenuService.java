package com.wr.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.system.domain.HomeMenu;


import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:31
 */
public interface HomeMenuService extends IService<HomeMenu> {

    /*
     * 查询列表
     * */
    List<HomeMenu> selectList(HomeMenu homeMenu);
}
