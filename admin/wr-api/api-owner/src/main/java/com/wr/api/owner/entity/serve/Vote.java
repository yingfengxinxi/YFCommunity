package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 投票表决实体，表：bus_vote
 *
 * @author SJiang
 * @since 2022-11-16 20:26:38
 */
@Data
public class Vote extends BaseEntity {
    private static final long serialVersionUID = -6677333136680372371L;

    /**
     * 主键
     */
    private Long voteId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 投票名称
     */
    private String voteName;
    /**
     * 状态：0=启用，1=关闭
     */
    private String voteStatus;
    /**
     * 数据来源：0=物业，1=业主委员会
     */
    private String dataSource;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 投票选项
     */
    private List<VoteItem> itemList;
}

