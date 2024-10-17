package com.wr.estate.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:13
 * 社区关怀
 */
@Data
@ApiModel("社区关怀")
public class BusCommunityCare implements Serializable {

    private Long id;

    private Long estateId;
    private Long communityId;
    //活动名称
    private String activityName;

    //活动图片【封面】
    private String activePicture;

    //活动内容
    private String content;

    //活动类型
    private Long communityCareId;
    //活动列表中显示
    private String activeListContent;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventReleaseTime;

    //状态0=未发布1=发布
    private String status;

    //顺序
    private int sort;

    private String eventReleaseBy;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    @TableLogic
    private String delFlag;
}
