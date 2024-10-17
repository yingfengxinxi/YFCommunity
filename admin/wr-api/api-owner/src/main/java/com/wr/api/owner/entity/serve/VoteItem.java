package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * 投票表决选项实体，表：bus_vote_item
 *
 * @author SJiang
 * @since 2022-11-16 20:37:53
 */
@Data
public class VoteItem implements Serializable {
    private static final long serialVersionUID = -16680621670187327L;

    /**
     * 主键
     */
    private Long itemId;
    /**
     * 投票表决id，表xccj_govern.bus_vote
     */
    private Long voteId;
    /**
     * 选项名称
     */
    private String itemName;
    /**
     * 选择人数
     */
    private Integer voteNum;

}

