package com.wr.api.estate.entity.vo.application.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 19:00:26
 * @Desc: VehicleVo
 */
@Data
public class VehicleVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8359750194440246984L;
    /**
     * 主键
     */
    private Long vehicleId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 车主id，表bus_account
     */
    private Long accountId;
    /**
     * 车牌编号
     */
    private String plateNo;
    /**
     * 车牌颜色: 见数据字典 sys_plate_color
     */
    private Integer plateColor;
    /**
     * 车牌类型:见数据字典 sys_plate_type
     */
    private Integer plateType;
    /**
     * 车辆品牌
     */
    private String vehicleBrand;
    /**
     * 0=其他车,1=小型车,2=大型车,3=摩托车
     */
    private Integer vehicleType;
    /**
     * 车辆状态：0=正常，1=关闭
     */
    private String vehicleStatus;
    /**
     * 车辆颜色:见数据字典 sys_vehicle_color
     */
    private String vehicleColor;
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
     * 当前所住房间，表bus_room
     */
    private String roomName;
    /**
     * 车位编号
     */
    private String carportNo;
    /**
     * 租售类型：0=售；1=年租；2=月租
     */
    private String rentalType;
    /**
     * 车位状态：0=开启，1=关闭
     */
    private String carportStatus;
    /**
     * 账号类型：0=业主，1=租客，2=访客，3=访客（装修工）
     */
    private String accountType;
    /**
     * 车主姓名
     */
    private String driverName;
    /**
     * 车主性别
     */
    private String gender;
    /**
     * 车主手机号
     */
    private String phone;

    /**
     * 车主姓名，表bus_account
     */
    private String accountName;

    /**
     * 车主手机号，表bus_account
     */
    private String loginName;

    //车位信息
    private CarportVo carportVo;

}
