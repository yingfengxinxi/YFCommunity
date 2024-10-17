package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 党员管理
 * @Author: lvzy
 * @Date: 2022-11-17 20:41:29
 * @Desc:党员管理
 */
@Data
public class ApiMember implements Serializable {
    /**
    * 党员id
    */
    private Long memberId;

    /**
    * 小区id，表xccj_community.bus_community
    */
    private Long communityId;

    /**
    * 楼栋id，表xccj_community.bus_building
    */
    private Long buildingId;

    /**
    * 单元id，表xccj_community.bus_unit
    */
    private Long unitId;

    /**
    * 当前所住房间id，表xccj_community.bus_room
    */
    private Long roomId;

    /**
    * 账号id，表xccj_community.bus_account
    */
    private Long accountId;

    /**
    * 入党时间
    */
    private Date joinTime;

    /**
    * 所属党组织id，表xccj_govern.bus_party
    */
    private Long partyId;

    /**
    * 证明图片
    */
    private String proveUrl;

    /**
    * 审核状态：0=审核中，1=通过，2=驳回
    */
    private String auditStatus;

    /**
    * 审核时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
    * 驳回理由
    */
    private String rejectReason;

    /**
    * 党内职务
    */
    private String memberDuty;

    /**
    * 个人党费
    */
    private BigDecimal partyFee;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 修改人
    */
    private String updateBy;

    /**
    * 修改时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
    * 删除状态 0 未删除 1 已删除
    */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}