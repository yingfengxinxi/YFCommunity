package com.wr.api.owner.service.home.notice;

import com.wr.api.owner.entity.home.notice.NoticeOwnerVO;
import com.wr.remote.domain.NoticeFestival;

import java.util.List;
import java.util.Map;

/**
 * 社区公告表(BusNotice)表服务接口
 *
 * @author lvzy
 * @since 2022-11-14 13:21:04
 */
public interface NoticeOwnerService {

    public void insertNoticeRead(Map<String,Object> map);

    /**
     * 小区的公告
     *
     * @param estateId
     * @return
     */
    List<NoticeOwnerVO> searchByCommunityId(Long estateId, String loginName, Long communityId);

    /**
     * 详情
     *
     * @param noticeId
     * @return
     */
    NoticeOwnerVO detailByNoticeId(Long noticeId);


    NoticeFestival festivalDetail(String id);

    /**
     * @param reqMap
     * @return
     */
    Map<String, Object> getNoticeList(Map<String,Object> reqMap);

    /**
    * @Author SUNk
    * @Description 查询物业通知
    * @Date 16:39 2024/4/1
    * @Param [reqMap]
    * @return java.util.List<com.wr.api.owner.entity.home.notice.NoticeOwnerVO>
    **/
    List<NoticeOwnerVO> selectListProperty(Map<String,Object> reqMap);

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
