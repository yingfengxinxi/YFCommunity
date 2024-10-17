package com.wr.api.estate.mapper.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.info.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:29:08
 * @Desc:
 */
@Repository
public interface BannerMapper extends BaseMapper<Banner> {
    /*
     * 查询列表
     * */
    List<Banner> selectListByestateId();
}
