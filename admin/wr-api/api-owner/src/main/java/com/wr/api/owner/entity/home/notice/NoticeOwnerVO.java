package com.wr.api.owner.entity.home.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 社区公告表(BusNotice)VO类
 *
 * @author lvzy
 * @since 2022-11-14 13:20:58
 */
@Data
public class NoticeOwnerVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String noticeTitle;
    //简介
    private String briefIntroduction;
    /**
     * 内容
     */
    private String content;
    /**
     * 阅读数
     */
    private Long readNum;
    /**
     * 创建人
     */
    private String createBy;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Long noticeId;

    /**
     * 统计数
     */
    private Integer readWaitNum;
    /**
     * 页面展示等待用户阅读数
     */
    private Integer readShowNum;

    //板块编号
    private String code;
    //板块ID
    private String codeId;

    //喜欢或者点赞人
    private String likeUser;

}

