package com.wr.remote.govern.autonomy.vote;


import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 /**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 投票选项
 */
@Data
public class VoteItem extends BaseEntity {
    /**
     * 主键
     */
    private Long itemId;
    /**
     * 投票表决id
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
    /**
     * 类型
     */
    private String  type;

}
