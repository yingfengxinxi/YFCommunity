package com.wr.api.owner.entity.home.memberAct;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-16 17:54:59
 * @Desc: 党员活动-党员关联表
 */
public class ApiMemberJoin  implements Serializable {


    private static final long serialVersionUID = 3832955826638701556L;

    /**
     * 党员活动id，表xccj_govern.bus_member_act
     */
    private Long activityId;


    /**
     * 党员id，表xccj_govern.bus_member
     */
    private Long memberId;

    /**
     *账号id  由登录后提供
     */

    private Long accountId;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }



    @Override
    public String toString() {
        return "ApiMemberJoin{" +
                "activityId=" + activityId +
                ", memberId=" + memberId +
                '}';
    }
}
