package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 线索反馈实体，表：bus_clue
 *
 * @author SJiang
 * @since 2022-11-15 14:19:28
 */
@Data
public class Clue extends BaseEntity {
    private static final long serialVersionUID = 8030794168396996038L;

    /**
     * 主键
     */
    private Long clueId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 小区id，xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 通报id，表xccj_govern.bus_bulletin
     */
    private Long bulletinId;
    /**
     * 举报人id，表xccj_community.bus_account
     */
    private Long accountId;
    /**
     * 地点
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    /**
     * 举报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

