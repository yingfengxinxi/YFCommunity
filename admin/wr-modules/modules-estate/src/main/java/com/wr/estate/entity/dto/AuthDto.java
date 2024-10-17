package com.wr.estate.entity.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: RainCity
 * @Date: 2021-11-26 15:31:10
 * @Desc:
 */
public class AuthDto implements Serializable {
    
    private static final long serialVersionUID = -4007891865562051834L;

    private Long communityId;

    private Long[] ids;

    private String[] indexCodes;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String[] getIndexCodes() {
        return indexCodes;
    }

    public void setIndexCodes(String[] indexCodes) {
        this.indexCodes = indexCodes;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "communityId=" + communityId +
                ", ids=" + Arrays.toString(ids) +
                ", indexCodes=" + Arrays.toString(indexCodes) +
                '}';
    }
}
