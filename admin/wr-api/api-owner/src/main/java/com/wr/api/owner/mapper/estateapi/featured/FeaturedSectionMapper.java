package com.wr.api.owner.mapper.estateapi.featured;

import com.wr.remote.govern.autonomy.featured.FeaturedCommentVO;
import com.wr.remote.govern.autonomy.featured.FeaturedLikeVO;
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
    * @Author SUNk
    * @Description 业主端分页查询特色板块话题
    * @Date 16:00 2024/3/18
    * @Param [featuredSection]
    * @return java.util.List<com.wr.remote.govern.autonomy.featured.FeaturedSection>
    **/
    List<FeaturedSection> findListSection(FeaturedSection featuredSection);

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

    /**
     * 将featured_id 和community_id  保存到关联表中
     * @param featuredSection
     */
    int saveFeaturedRang(FeaturedSection featuredSection);

    /**
    * @Author SUNk
    * @Description 查询评价
    * @Date 16:20 2024/3/18
    * @Param [featuredSection]
    * @return java.util.List<com.wr.remote.govern.autonomy.featured.FeaturedComment>
    **/
    List<FeaturedCommentVO> selectFeaturedCommentList(FeaturedSection featuredSection);


    /**
    * @Author SUNk
    * @Description 点赞
    * @Date 16:53 2024/3/18
    * @Param featuredLikeVO
    * @return int
    **/
    int saveFeaturedLike(FeaturedLikeVO featuredLikeVO);

    /**
     * 取消点赞
     * @param featuredLikeVO
     * @return
     */
    Integer removeFeaturedLike(FeaturedLikeVO featuredLikeVO);

    /**
     * 检查是否点赞
     * @param featuredLikeVO
     * @return
     */
    Integer checkFeaturedLike(FeaturedLikeVO featuredLikeVO);

    /**
    * @Author SUNk
    * @Description 新增评论
    * @Date 17:02 2024/3/18
    * @Param [commentVO]
    * @return int
    **/
    int saveComment(FeaturedCommentVO commentVO);

    /**
    * @Author SUNk
    * @Description 根据主键查询评论
    * @Date 17:02 2024/3/18
    * @Param [commentId]
    * @return com.wr.remote.domain.ApiSocialComment
    **/
    FeaturedCommentVO findCommentByPrimaryKey(Long commentId);

    /**
     * @Author SUNk
     * @Description 查询评价
     * @Date 16:20 2024/3/18
     * @Param [featuredSection]
     * @return java.util.List<com.wr.remote.govern.autonomy.featured.FeaturedComment>
     **/
    List<FeaturedCommentVO> selectNextFeaturedCommentList(Long commentId);

    /**
     * @Author SUNk
     * @Description 查询评论总数
     * @Date 18:35 2024/3/18
     * @Param [socialId]
     * @return java.lang.Integer
     **/
    Integer selectCountFeaturedComment(Long featuredId);

    /**
    * @Author SUNk
    * @Description 查询特色板块详情
    * @Date 15:46 2024/3/19
    * @Param [featuredSection]
    * @return com.wr.remote.govern.autonomy.featured.FeaturedSection
    **/
    FeaturedSection detilFeaturedSection(Long featuredId);
}
