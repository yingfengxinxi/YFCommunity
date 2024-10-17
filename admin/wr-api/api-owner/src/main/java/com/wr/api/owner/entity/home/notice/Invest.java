package com.wr.api.owner.entity.home.notice;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-11-15 18:53:14
 * @Desc: 物业招商公示表
 */
public class Invest extends BaseEntity {


    private static final long serialVersionUID = -4272672717832570826L;

    /**
     * 主键
     */
    private Long investId;

    /**
     * 物业id，表bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    private String investTitle;


    /**
     * 招商类别：0=物业招投标，1=中标信息，2=其他
     */
    private String investType;

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
    private String investStatus;


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


    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getInvestId() {
        return investId;
    }

    public void setInvestId(Long investId) {
        this.investId = investId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

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

    public String getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(String investStatus) {
        this.investStatus = investStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Invest{" +
                "investId=" + investId +
                ", estateId=" + estateId +
                ", investTitle='" + investTitle + '\'' +
                ", investType='" + investType + '\'' +
                ", content='" + content + '\'' +
                ", readNum=" + readNum +
                ", investStatus='" + investStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
