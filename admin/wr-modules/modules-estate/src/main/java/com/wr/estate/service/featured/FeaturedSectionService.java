package com.wr.estate.service.featured;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import com.wr.remote.govern.autonomy.social.Social;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
public interface FeaturedSectionService {

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
     *新增系统信息
     * @param social
     * @return
     */
    Integer addSystemFeatured(FeaturedSection featuredSection);

    /**
     * 删除社交记录
     * @param socialId
     * @return
     */
    int delMessageFeatured(Long[] featuredId);

    /**
     * 删除系统社交记录
     * @param id
     * @return
     */
    int delSystemFeatured(Long[] id);

    /**
     * 修改系统信息
     * @param featuredSection
     * @return
     */
    int updateSysFeatured(FeaturedSection featuredSection);

    /**
     * 查询featuredId表格中信息
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
}
