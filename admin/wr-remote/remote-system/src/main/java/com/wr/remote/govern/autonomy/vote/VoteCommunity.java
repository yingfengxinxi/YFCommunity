package com.wr.remote.govern.autonomy.vote;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 投票社区关联
 */
public class VoteCommunity {
    /**
     * 问卷主键，表sc_question
     */

    private  Long   voteId;
    /**
     * 小区主键，sc_community
     */
    private Long   communityId;


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }
}
