package com.wr.api.estate.entity.vo.application;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:09:59
 * @Desc: 党员咨询(BusMessage)VO类
 */
@Data
public class MessageVO {
    /**
     * 咨询标题
     */
    private String msgTitle;
    //封面图片
    private String activePicture;

    /**
     * 主键
     */
    private Long messageId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //发布人
    private String createBy;
    /**
     * 内容
     */
    private String content;
    //是否置顶0=否1=是
    private String top;
    //浏览次数
    private Long lookNumber;
}
