package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.serve.RescueUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 13:52:38
 * @Desc: 社区救助 VO
 */
@Data
public class RescueVO implements Serializable {
    private static final long serialVersionUID = 154852922793859410L;

    /**
     * 主键
     */
    private Long rescueId;
    /**
     * 标题
     */
    private String rescueTitle;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;
    /**
     * 发布人，表xccj_community.bus_owner/bus_tenant
     */
    private String releaseName;
    /**
     * 所在位置
     */
    private String address;
    /**
     * 需要人数
     */
    private Long needNum;
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
     * 具体内容
     */
    private String content;
    /**
     * 志愿者
     */
    private List<RescueUser> RescueUsers;
    /**
     * 状态：0=未开始，1=进行中，2=已结束
     */
    private String status;

    @Override
    public String toString() {
        return "RescueVO{" +
                "rescueId=" + rescueId +
                ", rescueTitle='" + rescueTitle + '\'' +
                ", releaseTime=" + releaseTime +
                ", releaseName=" + releaseName +
                ", address='" + address + '\'' +
                ", needNum=" + needNum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", content='" + content + '\'' +
                ", RescueUsers=" + RescueUsers +
                '}';
    }
}
