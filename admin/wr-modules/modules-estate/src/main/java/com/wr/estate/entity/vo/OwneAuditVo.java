package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 11:57:15
 * @Desc: 业主审核
 */
@Data
@ToString
public class OwneAuditVo {

    private Long ownerId;

    private Long houseId;

    private String communityName;

    private String licenseUrl;

    private String buildingName;

    private String unitName;

    private Long roomId;

    private String roomName;

    private Long accountId;

    private Long parentId;

    private Long roomStatus;

    private String ownerRelation;

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
    private String houseAddress;
    private String roomArea;
    private String totalArea;
    private String roomType;
    private String floor;
    private String ownRemarks;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;


}
