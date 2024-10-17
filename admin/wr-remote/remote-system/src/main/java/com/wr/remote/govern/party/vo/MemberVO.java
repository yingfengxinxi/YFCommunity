package com.wr.remote.govern.party.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author: SJiang
 * @Date: 2022-10-26 14:23:36
 * @Desc: 党员管理 VO
 */
@Data
public class MemberVO implements Serializable {
    private static final long serialVersionUID = -455536745815576805L;

    /**
     * 党员id
     */
    private Long memberId;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 党员姓名
     */
    private String memberName;
    /**
     * 党员手机号
     */
    private String memberPhone;
    /**
     * 入党时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 小区id,表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 小区,表xccj_community.bus_community
     */
    private String communityName;
    /**
     * 楼栋id，表xccj_community.bus_building
     */
    private Long buildingId;
    /**
     * 楼栋，表xccj_community.bus_building
     */
    private String buildingName;
    /**
     * 单元id，表xccj_community.bus_unit
     */
    private Long unitId;
    /**
     * 单元，表xccj_community.bus_unit
     */
    private String unitName;
    /**
     * 当前所住房间id，表xccj_community.bus_room
     */
    private Long roomId;
    /**
     * 当前所住房间，表xccj_community.bus_room
     */
    private String roomName;
    /**
     * 党员职务
     */
    private String memberDuty;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 账号类型：0=业主，1=租客，2=访客，3=访客（装修工）
     */
    private String accountType;
    /**
     * 党组织id
     */
    private Long partyId;
    /**
     * 党组织
     */
    private String partyName;
    /**
     * 地址
     */
    private String address;
    /**
     * 请求参数
     */
    private Map<String, Object> params;
    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 认证图片
     */
    private String proveUrl;
    /**
     * 个人党费
     */
    private BigDecimal partyFee;

    @Override
    public String toString() {
        return "MemberVO{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", joinTime=" + joinTime +
                ", createTime=" + createTime +
                ", communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", memberDuty='" + memberDuty + '\'' +
                ", auditTime=" + auditTime +
                ", accountId=" + accountId +
                ", accountType='" + accountType + '\'' +
                ", partyId=" + partyId +
                '}';
    }
}
