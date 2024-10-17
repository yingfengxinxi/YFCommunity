package com.wr.estate.mapper.featured;

import com.wr.remote.govern.autonomy.featured.FeaturedRang;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
public interface FeaturedSectionMapper {


    /**
     * 获取列表
     * @param featuredSection
     * @return
     */
    List<FeaturedSection> getList(FeaturedSection featuredSection);

    /**
     * 获取系统管理信息列表
     * @param featuredSection
     * @return
     */
    List<FeaturedSection> systemList(FeaturedSection featuredSection);


    /**
     * 新增
     * @param featuredSection
     * @return
     */
    Integer addSystemFeatured(FeaturedSection featuredSection);

    /**
     * 将featured_id 和community_id  保存到关联表中
     * @param list
     */
    void addFeaturedRang(ArrayList<FeaturedRang> list);

    /**
     * 删除社交记录
     * @param featuredId
     * @return
     */
    int delMessageFeatured(Long[] featuredId);

    /**
     * 删除关联表信息
     * @param id
     */
    void delFeaturedRang(Long[] id);

    /**
     * 修改
     * @param featuredSection
     * @return
     */
    int updateSysFeatured(FeaturedSection featuredSection);

    /**
     * 查询featuredSection表格中信息
     * @param featuredId
     * @return
     */
    FeaturedSection getFeaturedById(Long featuredId);

    /**
     * 查询小区与社交表的关联表
     * @param featuredId
     * @return
     */
    List<Long> queryFeaturedRangById(Long featuredId);

    /**
     * 根据社交表id删除关联表中的信息
     * @param featuredId
     */
    void delFeaturedRangByIds(Long featuredId);
}
