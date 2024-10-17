package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 20:41:02
 * @Desc: 投票表决VO
 */
@Data
public class VoteItemVO implements Serializable {
    private static final long serialVersionUID = 1888559091004594530L;

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
