package com.wr.remote.govern.autonomy.investigation;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 问题社区关联
 */
public class QuestionCommunity {
    /**
     * 问卷主键，表sc_question
     */

    private  Long   questionnaireId;
    /**
     * 小区主键，sc_community
     */
    private Long   communityId;

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
