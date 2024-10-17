package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/20 9:44
 */
@Data
public class BusStaffPropertyVo {

    //
    private String id;
    private Long estateId;
    //小区名称
    private String communityId;
    private String communityName;
    //提交人姓名
    private String name;
    //提交人手机号
    private String phone;
    //车位编号
    private String vehicleNo;
    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 车辆颜色:见数据字典 sys_vehicle_color
     */
    private String vehicleColor;

    /**
     * 车辆品牌
     */
    private String vehicleBrand;

    /**
     * 车辆类型:见数据字典 vehicle_type
     */
    private String vehicleType;

    /**
     * 车辆状态：0=正常，1=闲置
     */
    private String vehicleStatus;
    //驳回原因
    private String rejectReason;


    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;
    //审核人
    private String auditBy;
}
