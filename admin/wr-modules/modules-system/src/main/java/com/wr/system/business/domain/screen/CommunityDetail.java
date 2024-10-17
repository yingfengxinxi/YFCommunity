package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 社区人员结构
 */
public class CommunityDetail {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 容积率
     */
    private String plotRatio;

    /**
     * 绿化率
     */
    private String greenRatio;

    /**
     * 占地面积
     */
    private String landSpace;

    /**
     * 建筑面积
     */
    private String coveredArea;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(String plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getGreenRatio() {
        return greenRatio;
    }

    public void setGreenRatio(String greenRatio) {
        this.greenRatio = greenRatio;
    }

    public String getLandSpace() {
        return landSpace;
    }

    public void setLandSpace(String landSpace) {
        this.landSpace = landSpace;
    }

    public String getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(String coveredArea) {
        this.coveredArea = coveredArea;
    }
}
