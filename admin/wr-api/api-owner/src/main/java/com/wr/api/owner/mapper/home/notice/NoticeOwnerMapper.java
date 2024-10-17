package com.wr.api.owner.mapper.home.notice;

import com.wr.api.owner.entity.home.notice.NoticeOwnerVO;
import com.wr.remote.domain.NoticeFestival;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 社区公告表(BusNotice)表数据库访问层
 *
 * @author lvzy
 * @since 2022-11-14 13:20:53
 */
@Mapper
public interface NoticeOwnerMapper {
    /**
     * 小区的公告
     *
     * @param estateId
     * @param communityId
     * @return
     */
    List<NoticeOwnerVO> searchByCommunityId(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    /**
     * 公告 阅读数 +1
     *
     * @param id
     * @return
     */
    int increaseReadNumByNoticeId(Long id);

    /**
     * 详情
     *
     * @param noticeId
     * @return
     */
    NoticeOwnerVO detailByNoticeId(@Param("noticeId") Long noticeId);

    /**
     * @param userId
     * @return
     */
    List<NoticeOwnerVO> getNoticeList(@Param("userId") Long userId, @Param("communityId") Long communityId);

    /**
     * @param userId
     * @param type
     * @return
     */
    Integer getUserReadCount(@Param("userId") String userId, @Param("type") String type, @Param("communityId") Long communityId);

    Integer getInvestCount(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    Integer getNoticeCount(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    /**
     * @param userId
     * @param communityId
     * @return
     */
    Integer getNoticeFestivalCount(@Param("userId") Long userId, @Param("communityId") Long communityId);

    Integer getPreventCount(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

    /**
     * @param userId
     * @param type
     * @param noticeId
     * @return
     */
    Integer getByUserIdTypeNoticeRead(@Param("communityId") Long communityId, @Param("userId") String userId, @Param("type") String type, @Param("id") String id);

    /**
     * @param communityId
     * @param noticeId
     * @param userId
     * @param type
     * @param flag
     */
    void insertNoticeRead(@Param("communityId") Long communityId, @Param("noticeId") String noticeId, @Param("userId") String userId, @Param("type") String type, @Param("flag") String flag, @Param("likeUser") String likeUser);

    NoticeFestival festivalDetail(@Param("id") String id);

    /**
    * @Author SUNk
    * @Description 查询治安防控总数
    * @Date 14:45 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountSafety(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询所有的已读数据
    * @Date 15:57 2024/3/21
    * @Param [userId, type]
    * @return java.lang.Integer
    **/
    @Select( "SELECT\n" +
            "\tCOUNT(*) readCount\n" +
            "FROM\n" +
            "\txccj_community.bus_notice_read_owner \n" +
            "WHERE\n" +
            "\tdel_flag = '0'\n" +
            "\tAND user_id = #{accountId}\n" +
            "\tAND type = #{type}" )
    Integer selectCountReadOwner(@Param("accountId") Long userId, @Param("type") String type);

    /**
    * @Author SUNk
    * @Description 查询报事报修总数
    * @Date 15:12 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountWarranty(Map<String,Object> map);
    
    /**
    * @Author SUNk
    * @Description 查询社区公告总数
    * @Date 15:23 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountNotice(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询自治规约统计
    * @Date 15:28 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountStatute(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询业主物业缴费通知
    * @Date 15:48 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountProperty(Map<String,Object> map);
    
    /**
    * @Author SUNk
    * @Description 查询节假日通告
    * @Date 15:50 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountFestival(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询警民互动
    * @Date 16:01 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountBulletin(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询公示表
    * @Date 16:12 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountInvest(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询社区社交评论表
    * @Date 17:17 2024/3/21
    * @Param [map]
    * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
    **/
    List<NoticeOwnerVO> selectCountSocial(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询社区社交点赞表
    * @Date 16:50 2024/3/25
    * @Param [map]
    * @return java.util.List<com.wr.api.owner.entity.home.notice.NoticeOwnerVO>
    **/
    List<NoticeOwnerVO> selectCountSocialLike(Map<String,Object> map);

    /**
     * @Author SUNk
     * @Description 查询特色板块评论表
     * @Date 17:17 2024/3/21
     * @Param [map]
     * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
     **/
    List<NoticeOwnerVO> selectCountSection(Map<String,Object> map);

    /**
    * @Author SUNk
    * @Description 查询特色板块点赞表
    * @Date 16:49 2024/3/25
    * @Param [map]
    * @return java.util.List<com.wr.api.owner.entity.home.notice.NoticeOwnerVO>
    **/
    List<NoticeOwnerVO> selectCountSectionLike(Map<String,Object> map);
    
    /**
    * @Author SUNk
    * @Description 查询物业通知
    * @Date 16:38 2024/4/1
    * @Param [map]
    * @return java.util.List<com.wr.api.owner.entity.home.notice.NoticeOwnerVO>
    **/
    List<NoticeOwnerVO> selectListProperty(Map<String,Object> map);

    /**
     * @Author SUNk
     * @Description 查询特色板块及社交板块的点赞评论
     * @Date 17:17 2024/3/21
     * @Param [map]
     * @return com.wr.api.owner.entity.home.notice.NoticeOwnerVO
     **/
    List<NoticeOwnerVO> selectListSocialFeatured(Map<String,Object> map);

    NoticeOwnerVO findOneMessage(Map<String,Object> map);
}

