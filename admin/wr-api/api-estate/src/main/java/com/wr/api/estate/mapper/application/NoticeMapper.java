package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.vo.NoticeListVo;
import com.wr.api.estate.entity.vo.application.NoticeVO;
import com.wr.remote.domain.NoticeFestival;
import com.wr.remote.estate.notice.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区公告表(BusNotice)表数据库访问层
 *
 * @author lvzy
 * @since 2022-11-14 13:20:53
 */
public interface NoticeMapper {
    /**
     * 小区的公告
     *
     * @param estateId
     * @param communityId
     * @return
     */
    List<NoticeVO> searchByCommunityId(@Param("estateId") Long estateId, @Param("communityId") Long communityId);

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
    NoticeVO detailByNoticeId(@Param("noticeId") Long noticeId);

    /**
     * @param userId
     * @return
     */
    List<NoticeListVo> getNoticeList(@Param("userId") Long userId, @Param("communityId") Long communityId);

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
    void insertNoticeRead(@Param("communityId") Long communityId, @Param("noticeId") String noticeId, @Param("userId") String userId, @Param("type") String type, @Param("flag") String flag);

    NoticeFestival festivalDetail(@Param("id") String id);
}

