package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:52:50
 * @Desc: 物业通报公告
 */
@Data
public class BulletinVO {
    /**
     * 标题
     */
    private String noticeTitle;

    /**
     * 主键
     */
    private Long id;
    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date time;
}
