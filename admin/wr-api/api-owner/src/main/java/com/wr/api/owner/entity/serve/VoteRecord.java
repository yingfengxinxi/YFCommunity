package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 投票纪录实体，表：bus_vote_record
 *
 * @author SJiang
 * @since 2022-11-16 17:45:46
 */
@Data
public class VoteRecord extends BaseEntity {
    private static final long serialVersionUID = -9048133033674547236L;

    /**
     * 主键id
     */
    private Long recordId;
    /**
     * 物业id，表xccj_community.bus_estate
     */
    private Long estateId;
    /**
     * 物业名称
     */
    private String estateName;
    /**
     * 用户类型：0=业主，1=员工
     */
    private String userType;
    /**
     * 用户id，user_type对应的小区中的表
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 考核id，表xccj_office.bus_assess
     */
    private Long assessId;
    /**
     * 投票得分
     */
    private Double assessScore;
    /**
     * 投票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date voteTime;

}

