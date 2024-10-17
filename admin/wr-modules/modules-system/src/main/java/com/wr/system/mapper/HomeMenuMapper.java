package com.wr.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wr.system.domain.HomeMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:26
 */
@Repository
public interface HomeMenuMapper extends BaseMapper<HomeMenu> {

    /*
     * 查询列表
     * */
    List<HomeMenu> selectListByestateId(HomeMenu homeMenu);
}
