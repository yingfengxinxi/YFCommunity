package com.wr.remote.govern.party;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-10-24 17:10:31
 * @Desc: 党员管理 实体
 */
@Data
@TableName("xccj_govern.bus_member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = -3951766298138275665L;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinTime;
    /**
     * 所属党组织id，表xccj_govern.bus_party
     */
    private Long partyId;
    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 认证图片
     */
    private String proveUrl;
    /**
     * ids
     */
    private Long [] memberIds;
    /**
     * 人员类型：：0=业主，1=租客
     */
    private String accountType;
    private String delFlag;

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", accountId=" + accountId +
                ", joinTime=" + joinTime +
                ", partyId=" + partyId +
                ", auditStatus='" + auditStatus + '\'' +
                ", auditTime=" + auditTime +
                ", rejectReason='" + rejectReason + '\'' +
                ", memberDuty='" + memberDuty + '\'' +
                ", partyFee=" + partyFee +
                ", proveUrl='" + proveUrl + '\'' +
                ", memberIds=" + Arrays.toString(memberIds) +
                '}';
    }
}

