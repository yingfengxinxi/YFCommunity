package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 社区人员结构
 */
public class NoticeVo {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 头条标题
     */
    private String noticeTitle;

    /**
     * 发布范围0=全部1=物业2=业主
     */
    private String range;

    /**
     * 状态：0=已发布，1=已撤回
     */
    private String noticeStatus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 阅读量
     */
    private Long readNum;

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }
}
