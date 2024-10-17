package com.wr.remote.govern.autonomy.statute;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-25 08:52:41
 * @Desc: 业主捐赠表(规约表)
 */
public class Statute extends BaseEntity {

    private static final long serialVersionUID = -8712671994052162599L;
    /**
     * 主键
     */
    private Long statuteId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    private String statuteTitle;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 内容
     */
    private String content;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 所发布的小区
     */
    private Long[] communityIds;


    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }

    public Long getStatuteId() {
        return statuteId;
    }

    public void setStatuteId(Long statuteId) {
        this.statuteId = statuteId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getStatuteTitle() {
        return statuteTitle;
    }

    public void setStatuteTitle(String statuteTitle) {
        this.statuteTitle = statuteTitle;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
