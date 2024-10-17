package com.wr.remote.govern.autonomy.decide;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-26 14:02:46
 * @Desc: 自治表决选项表
 */
public class DecideItem extends BaseEntity {

    private static final long serialVersionUID = 6340663996164007861L;
    /**
     * 主键
     */
    private Long itemId;

    /**
     * 自治表决id，表xccj_govern.bus_decide
     */
    private Long statuteId;

    /**
     * 标题
     */
    private String itemTitle;

    /**
     * 选择人数
     */
    private Integer voteNum;

    /**
     * '删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getStatuteId() {
        return statuteId;
    }

    public void setStatuteId(Long statuteId) {
        this.statuteId = statuteId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
