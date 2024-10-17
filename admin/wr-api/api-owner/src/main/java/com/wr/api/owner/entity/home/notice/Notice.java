package com.wr.api.owner.entity.home.notice;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-11-14 17:13:28
 * @Desc: 社区公告表
 */
@Data
public class Notice extends BaseEntity {
    private static final long serialVersionUID = 7319236100070954945L;
    /**
     * 主键
     */
    private Long noticeId;

    /**
     * 物业id，表bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    private String noticeTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 阅读数
     */
    private Long readNum;

    /**
     * 状态：0=已发布，1=已撤回
     */
    private String noticeStatus;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 小区id
     */
    private Long communityId;


    /**
     * 物业名称
     */
    private String estateName;

    //简介
    private String briefIntroduction;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
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

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
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

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", estateId=" + estateId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", content='" + content + '\'' +
                ", readNum=" + readNum +
                ", noticeStatus='" + noticeStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", communityId=" + communityId +
                ", estateName='" + estateName + '\'' +
                '}';
    }
}
