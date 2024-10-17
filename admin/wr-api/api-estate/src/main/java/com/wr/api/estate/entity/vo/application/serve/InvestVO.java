package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:42:18
 * @Desc: 物业招商公示
 */
@Data
public class InvestVO {

    private Long id;
    /**
     * 主键
     */
    private Long investId;
    /**
     * 标题
     */
    private String noticeTitle;

    //简介
    private String briefIntroduction;
    /**
     * 公示类型0=物业招商公示1=物业通报公示
     */
    private String investType;
    /**
     * 内容
     */
    private String content;

    //发布人
    private String createBy;
    /**
     * 阅读数
     */
    private Long readNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
