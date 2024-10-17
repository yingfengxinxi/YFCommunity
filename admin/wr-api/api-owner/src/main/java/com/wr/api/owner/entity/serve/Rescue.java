package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 社区救助 实体，表：bus_rescue
 *
 * @author Sjiang
 * @since 2022-11-11 13:50:24
 */
@Data
public class Rescue extends BaseEntity {
    private static final long serialVersionUID = -6926483309566207766L;

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

    @Override
    public String toString() {
        return "Rescue{" +
                "rescueId=" + rescueId +
                ", communityId=" + communityId +
                ", rescueTitle='" + rescueTitle + '\'' +
                ", address='" + address + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", releaseId=" + releaseId +
                ", needNum=" + needNum +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", releaseTime=" + releaseTime +
                '}';
    }
}

