package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * 投票纪录实体，表：bus_vote_record
 *
 * @author SJiang
 * @since 2022-11-16 17:45:46
 */
@Data
public class VoteRecordVO implements Serializable {
    private static final long serialVersionUID = -9048133033674547236L;

    private Long id;

    /**
     * 投票id
     */
    private Long voteId;

    /**
     * 业主id
     */
    private Long ownerId;

    /**
     * 投票结果id
     */
    private Long itemId;
}

