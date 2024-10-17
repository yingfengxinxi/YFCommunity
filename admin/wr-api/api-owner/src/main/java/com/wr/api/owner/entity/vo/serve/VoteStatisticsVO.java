package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

@Data
public class VoteStatisticsVO {

    /**
     * 同意人数
     */
    private Integer consentCount;

    /**
     * 不同意人数
     */
    private Integer unConsentCount;

}
