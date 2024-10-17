package com.wr.api.owner.mapper.home.notice;


import com.wr.api.owner.entity.home.notice.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
@Mapper
public interface NoticeMapper {
    /**
     * 列表
     * @param notice
     * @return
     */
    List<Notice> getList(Notice notice);

    /**
     * 根据公告id查详情
     * @param noticeId
     * @return
     */
    Notice selectOneNoticeById(Long noticeId);


    /**
     * 查询发布时间最新的公告
     * @return
     */
    Notice selectNews(Notice notice);

    /**
     * 新增阅读数
     * @param noticeId
     */
    void updateReadNum(Long noticeId);
}
