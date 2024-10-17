package com.wr.govern.mapper.autonomy.investigation;


import com.wr.remote.govern.autonomy.investigation.ResearchParty;

/**
 * 党建调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface ResearchPartyMapper {

    /**
     * 新增党建调研
     * @param researchParty
     * @return
     */
    public int insertResearchParty(ResearchParty researchParty);
}
