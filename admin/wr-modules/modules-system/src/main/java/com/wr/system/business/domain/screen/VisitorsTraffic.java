package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @Author: Guo Jia
 * @Date: 2021-12-31 17:18:29
 * @Desc:
 */
public class VisitorsTraffic implements Serializable {
    private static final long serialVersionUID = -1297769151485293217L;
    private Integer toDay;

    private Integer yesterday;

    private Integer beforeDay;

    private Integer threeDay;

    private Integer fourDay;

    private Integer fiveDay;

    private Integer sixDay;

    private Integer sevenDay;

    private Long communityId;

    private String discernTime;

    private Long duringMonth;

    public Integer getToDay() {
        return toDay;
    }

    public void setToDay(Integer toDay) {
        this.toDay = toDay;
    }

    public Long getDuringMonth() {
        return duringMonth;
    }

    public void setDuringMonth(Long duringMonth) {
        this.duringMonth = duringMonth;
    }

    public Integer getYesterday() {
        return yesterday;
    }

    public void setYesterday(Integer yesterday) {
        this.yesterday = yesterday;
    }

    public Integer getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(Integer beforeDay) {
        this.beforeDay = beforeDay;
    }

    public Integer getThreeDay() {
        return threeDay;
    }

    public void setThreeDay(Integer threeDay) {
        this.threeDay = threeDay;
    }

    public Integer getFourDay() {
        return fourDay;
    }

    public void setFourDay(Integer fourDay) {
        this.fourDay = fourDay;
    }

    public Integer getFiveDay() {
        return fiveDay;
    }

    public void setFiveDay(Integer fiveDay) {
        this.fiveDay = fiveDay;
    }

    public Integer getSixDay() {
        return sixDay;
    }

    public void setSixDay(Integer sixDay) {
        this.sixDay = sixDay;
    }

    public Integer getSevenDay() {
        return sevenDay;
    }

    public void setSevenDay(Integer sevenDay) {
        this.sevenDay = sevenDay;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getDiscernTime() {
        return discernTime;
    }

    public void setDiscernTime(String discernTime) {
        this.discernTime = discernTime;
    }

    @Override
    public String toString() {
        return "VisitorsTraffic{" +
                "toDay=" + toDay +
                ", yesterday=" + yesterday +
                ", beforeDay=" + beforeDay +
                ", threeDay=" + threeDay +
                ", fourDay=" + fourDay +
                ", fiveDay=" + fiveDay +
                ", sixDay=" + sixDay +
                ", sevenDay=" + sevenDay +
                ", communityId=" + communityId +
                ", discernTime='" + discernTime + '\'' +
                '}';
    }
}
