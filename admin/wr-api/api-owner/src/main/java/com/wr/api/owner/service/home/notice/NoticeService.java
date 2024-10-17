package com.wr.api.owner.service.home.notice;


import com.wr.api.owner.entity.home.notice.Notice;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
public interface NoticeService {


    /**
     * 列表
     *
     * @param notice
     * @return
     */
    List<Notice> getList(Notice notice);

    /**
     * 根据公告id茶详情
     *
     * @param noticeId
     * @return
     */
    Notice selectOneNoticeById(Long noticeId);

    /**
     * 查询发布时间最新的公告
     *
     * @return
     */
    Notice selectNews(Notice notice);
}
