package com.wr.api.owner.service.estateapi.featured;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.govern.autonomy.featured.FeaturedCommentVO;
import com.wr.remote.govern.autonomy.featured.FeaturedLikeVO;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;

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
     *新增特色板块话题
     * @param featuredSection
     * @return
     */
    Integer saveSection(FeaturedSection featuredSection);

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

    /**
    * @Author SUNk
    * @Description 特色板块点赞
    * @Date 16:47 2024/3/18
    * @Param [featuredLikeVO]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult likeFeatured(FeaturedLikeVO featuredLikeVO);

    /**
    * @Author SUNk
    * @Description 特色板块评论
    * @Date 16:47 2024/3/18
    * @Param [featuredCommentVO]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult commentFeatured(FeaturedCommentVO featuredCommentVO);
}
