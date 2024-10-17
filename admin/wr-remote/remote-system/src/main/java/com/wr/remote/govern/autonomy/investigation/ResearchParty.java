package com.wr.remote.govern.autonomy.investigation;

/**
 * @Author: liubei
 * @Date: 2022/10/31 14:46
 * @Desc:党建调研党组织关联表
 */
public class ResearchParty {
    /**
     * 党组织主键ID
     */

    private  Long   researchId;
    /**
     * 党组织ID
     */
    private Long   partyId;

    public Long getResearchId() {
        return researchId;
    }

    public void setResearchId(Long researchId) {
        this.researchId = researchId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }
}
