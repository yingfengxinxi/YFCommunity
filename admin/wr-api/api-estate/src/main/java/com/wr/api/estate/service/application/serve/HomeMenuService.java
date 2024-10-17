package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.HomeMenu;
import com.wr.remote.estate.info.Banner;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:31
 */
public interface HomeMenuService extends IService<HomeMenu> {

    /*
     * 查询列表
     * */
    List<HomeMenu> selectList();
}
