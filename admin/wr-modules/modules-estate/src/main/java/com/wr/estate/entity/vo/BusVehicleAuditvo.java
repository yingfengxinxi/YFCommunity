package com.wr.estate.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-17 15:46:57
 * @Desc: 车俩审核辅助类
 */
@Data
@ToString
public class BusVehicleAuditvo extends BaseEntity {

    //车辆管理表id
    private Long vehicleId;
    //
    private Integer estateId;

    private Long communityId;

    private String communityName;

    private String buildingName;
    private String ownerRelation;

    private String unitName;

    private String roomName;

    private String accountName;

    //手机号
    private String loginName;

    //所属项目
    private Long accountType;

    private String plateNo;

    private String plateColor;

    private String vehicleBrand;

    private String vehicleType;

    private String vehicleStatus;

    private String vehicleColor;

    private String auditStatus;

    private Date auditTime;

    private String rejectReason;

    private String delFlag;

    private String plateType;

    private String frontIdCard;

    private String backIdCard;

    private String realEstate;

    private String otherPhoto;

    private static final long serialVersionUID = 1L;
}
