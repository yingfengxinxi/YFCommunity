package com.wr.api.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/16 18:59
 */
@Data
public class NoticeListVo implements Serializable {

    //id
    private String id;
    //标题
    private String title;
    //简介
    private String briefIntroduction;
    //时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //是否出现未读消息
    private Integer isRead;
    //消息类型0=系统消息1=信息发布2=防控信息
    private String flag;
}
