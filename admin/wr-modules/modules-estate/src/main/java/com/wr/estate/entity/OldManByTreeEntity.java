package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/10/11
 * @Description: 树形图实体
 * @FileName: OldManByTreeEntity
 * @History:
 */
public class OldManByTreeEntity extends BaseEntity {

    private long communityId;

    private String communityName;

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    @Override
    public String toString() {
        return "OldManByTreeEntity{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                '}';
    }
}
