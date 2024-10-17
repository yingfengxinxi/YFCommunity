package com.wr.remote.govern.autonomy.decide;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-10-26 13:51:58
 * @Desc: 自治表决表
 */
public class Decide extends BaseEntity  {
    private static final long serialVersionUID = 4652208624654933569L;
    /**
     * 主键
     */
    private Long decideId;

    /**
     * 物业id，表xccj_community.bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    private String decideTitle;

    /**
     * 内容
     */
    private String content;


    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 所发布的小区（表决发布到那些小区）
     */
    private Long[] communityIds;

    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }

    public Long getDecideId() {
        return decideId;
    }

    public void setDecideId(Long decideId) {
        this.decideId = decideId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getDecideTitle() {
        return decideTitle;
    }

    public void setDecideTitle(String decideTitle) {
        this.decideTitle = decideTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
