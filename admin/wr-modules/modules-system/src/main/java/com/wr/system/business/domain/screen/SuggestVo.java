package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @Author: LiuBei
 * @Date: 2021-12-31 09:27:27
 * @Desc:
 */
public class SuggestVo implements Serializable {
    private static final long serialVersionUID = 74451478848594664L;
    /**小区id*/
    private Long communityId;
    /**好评率*/
    private Double score;
    /**物业名称*/
    private String deptName;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "SuggestVo{" +
                "communityId=" + communityId +
                ", score=" + score +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
