package com.wr.api.owner.entity.home.headline;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-11-14 20:06:53
 * @Desc:  社区头条 —— 公示
 */
public class Headline extends BaseEntity {
    private static final long serialVersionUID = 2093660269954342663L;
    /**
     * 主键
     */
    private Long headlineId;

    /**
     *物业id，表bus_estate
     */
    private Long estateId;

    /**
     * 头条标题
     */
    private String headlineTitle;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date  startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 图片
     */
    private String headlineUrl;

    /**
     * 内容
     */
    private String content;


    /**
     * 阅读数
     */
    private Long readNum;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 物业名称
     */
    private String estateName;


    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getHeadlineId() {
        return headlineId;
    }

    public void setHeadlineId(Long headlineId) {
        this.headlineId = headlineId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getHeadlineTitle() {
        return headlineTitle;
    }

    public void setHeadlineTitle(String headlineTitle) {
        this.headlineTitle = headlineTitle;
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

    public String getHeadlineUrl() {
        return headlineUrl;
    }

    public void setHeadlineUrl(String headlineUrl) {
        this.headlineUrl = headlineUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }
}
