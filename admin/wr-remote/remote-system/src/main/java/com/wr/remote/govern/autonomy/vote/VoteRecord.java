package com.wr.remote.govern.autonomy.vote;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.estate.manage.contract.Owner;
import lombok.Data;

import java.util.Date;

/**
 * @author DIANWEI
 * @Desc: 投票结果业主详情
 */
@Data
public class VoteRecord extends BaseEntity {

    /**
     * 投票id
     */
    private Long voteId;
    /**
     * 业主名称
     */
    private String ownerName;
    /**
     * 业主手机号
     */
    private String ownerPhone;
    /**
     * 投票表决选项信息
     */
    private String itemName;
    /**
     * 投票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date voteTime;


}
