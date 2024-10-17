package com.wr.api.owner.service.home.location;

import com.wr.api.owner.entity.home.location.Banner;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 17:41:10
 * @Desc: 轮播图接口
 */
public interface BannerService {

    /**
     * 查询轮播图
     * @param estateId  物业Id
     * @return
     */
    List<Banner> selectListBanner(Long estateId,String type);
}
