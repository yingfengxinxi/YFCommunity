package com.wr.api.owner.web.home.notice;


import com.wr.api.owner.entity.home.notice.Notice;
import com.wr.api.owner.service.home.notice.NoticeService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 公告控制层
 */
@RestController
@RequestMapping("/v2/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 获取公告列表
     *
     * @param notice
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(Notice notice) {
        startPage();
        //调用接口获取内容
        List<Notice> list = noticeService.getList( notice );
        return getDataTable( list );
    }


    /**
     * 根据公告id查询详情
     *
     * @param noticeId
     * @return
     */
    @GetMapping(value = "/{noticeId}")
    @AuthRequired
    public AjaxResult getNoticeById(@PathVariable Long noticeId) {
        Notice notice = noticeService.selectOneNoticeById( noticeId );
        return AjaxResult.success( notice );
    }

    /**
     * 查询发布时间最新的公告
     *
     * @return
     */
    @GetMapping(value = "/newsNotice")
    @AuthRequired
    public AjaxResult getNewsNotice(Notice notice) {
        Notice n = noticeService.selectNews( notice );
        return AjaxResult.success( n );
    }


}
