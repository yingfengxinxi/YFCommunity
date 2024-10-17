package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 社区人员结构
 */
public class InvestVo {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 标题
     */
    private String investTitle;

    /**
     * 公示类型0=物业招商公示1=物业通报公示
     */
    private String investType;

    /**
     * 状态：0=已发布，1=已撤回
     */
    private String investStatus;

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

    public String getInvestTitle() {
        return investTitle;
    }

    public void setInvestTitle(String investTitle) {
        this.investTitle = investTitle;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(String investStatus) {
        this.investStatus = investStatus;
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
