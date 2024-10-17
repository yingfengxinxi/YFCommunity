package com.wr.api.estate.entity.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:52:19
 * @Desc: 社会救助实现类
 */
@Data
public class Rescue implements Serializable {

    private static final long serialVersionUID = -8908653539241278033L;

    /**
     * 主键
     */
    private Long rescueId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 标题
     */
    private String rescueTitle;

    /**
     * 所在位置
     */
    private String address;

    /**
     * 发布人类型：0=业主，1=租客
     */
    private String releaseType;

    /**
     * 发布人id，表xccj_community.bus_owner/bus_tenant
     */
    private Long releaseId;

    /**
     * 需要人数
     */
    private Long needNum;

    /**
     * 具体内容
     */
    private String content;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 紧急联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
