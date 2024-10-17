package com.wr.api.owner.service.impl.home.notice;


import com.wr.api.owner.entity.home.notice.Notice;
import com.wr.api.owner.mapper.home.notice.InvestMapper;
import com.wr.api.owner.mapper.home.notice.NoticeMapper;
import com.wr.api.owner.mapper.serve.PreventMapper;
import com.wr.api.owner.service.home.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公告
 */
@Service
public class NoticeServiceImpl implements NoticeService {


    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private PreventMapper preventMapper;

    /**
     * 列表
     *
     * @param notice
     * @return
     */
    @Override
    public List<Notice> getList(Notice notice) {
        return noticeMapper.getList( notice );
    }

    /**
     * 根据公告id查详情
     *
     * @param noticeId
     * @return
     */
    @Override
    public Notice selectOneNoticeById(Long noticeId) {
        //添加阅读数
        noticeMapper.updateReadNum( noticeId );

        return noticeMapper.selectOneNoticeById( noticeId );
    }


    /**
     * 查询发布时间最新的公告
     *
     * @return
     */
    @Override
    public Notice selectNews(Notice notice) {
        return noticeMapper.selectNews( notice );
    }
}
