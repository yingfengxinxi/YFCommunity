package com.wr.api.owner.service.home.headline;


import com.wr.api.owner.entity.home.headline.Headline;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
public interface HeadlineService {


    /**
     *列表
     * @param headline
     * @return
     */
    List<Headline> getList(Headline headline);

    /**
     * 根据公告id茶详情
     * @param headline
     * @return
     */
    Headline selectOneById(Headline headline);

    /**
     * 查询根据发布时间最新的社区公告
     * @return
     */
    Headline selectNews(Headline headline);
}
