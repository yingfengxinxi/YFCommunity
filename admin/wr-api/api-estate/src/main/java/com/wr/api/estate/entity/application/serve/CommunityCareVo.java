package com.wr.api.estate.entity.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:13
 * 社区关怀
 */
@Data
public class CommunityCareVo implements Serializable {

    private Long id;

    private Long communityId;
    //活动名称
    private String activityName;
    //活动列表中显示
    private String activeListContent;

    //活动图片【封面】
    private String activePicture;

    //活动内容
    private String content;

    //活动类型
    private String communityCareId;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventReleaseTime;

    //状态0=未发布1=发布
    private String status;

    //顺序
    private int sort;
    //发布人
    private String eventReleaseBy;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String delFlag;
}
