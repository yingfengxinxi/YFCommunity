package com.wr.api.owner.mapper.home.headline;


import com.wr.api.owner.entity.home.headline.Headline;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区头条
 */
@Mapper
public interface HeadlineMapper {
    /**
     * 列表
     * @param headline
     * @return
     */
    List<Headline> getList(Headline headline);

    /**
     * 根据头条id查详情
     * @param headline
     * @return
     */
    Headline selectOneById(Headline headline);

    /**
     * 查询根据发布时间最新的社区头条
     * @return
     */
    Headline selectNews(Headline headline);

    void readNumAdd(Headline headline);
}
