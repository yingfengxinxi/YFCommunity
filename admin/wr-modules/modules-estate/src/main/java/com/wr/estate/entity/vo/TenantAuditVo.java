package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-15 15:30:08
 * @Desc: 租客审核辅助类
 */
@Data
@ToString
public class TenantAuditVo {

    private String contractNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractStart;

    private String contractUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractEnd;

    private Long tenantId;

    private Long roomStatus;

    private String communityName;

    private String licenseUrl;

    private String buildingName;

    private String unitName;

    private Long roomId;

    private String roomName;

    private Long accountId;

    private Long ownerId;

    private String tenantName;

    private String tenantPhone;

    private String ownerName;

    private String ownerPhone;

    private String gender;

    private String cardType;

    private String cardNo;

    private String cardFont;

    private String cardBack;

    private String faceUrl;

    private String domicileAddress;

    private String politicsStatus;

    private String nation;

    private String nativePlace;

    private String religion;

    private String eduLevel;

    private String military;

    private String marriageStatus;

    private String nationality;

    private String population;

    private String career;

    private String qqNumber;

    private String email;

    private String dataSource;

    private String selfIntro;

    private String auditStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    private String rejectReason;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String delFlag;


}
