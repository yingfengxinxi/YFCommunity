package com.wr.api.estate.service.application;

import com.wr.api.estate.entity.vo.NoticeListVo;
import com.wr.api.estate.entity.vo.application.NoticeVO;
import com.wr.remote.domain.NoticeFestival;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区公告表(BusNotice)表服务接口
 *
 * @author lvzy
 * @since 2022-11-14 13:21:04
 */
public interface NoticeService {

    public void insertNoticeRead(Long communityId, String noticeId, String userId, String type, String flag, Long estateId);

    /**
     * 小区的公告
     *
     * @param estateId
     * @return
     */
    List<NoticeVO> searchByCommunityId(Long estateId, String loginName, Long communityId);

    /**
     * 详情
     *
     * @param noticeId
     * @return
     */
    NoticeVO detailByNoticeId(Long noticeId);


    NoticeFestival festivalDetail(String id);

    /**
     * @param estateId
     * @return
     */
    List<NoticeListVo> getNoticeList(Long estateId, String loginName, Long communityId);

}
