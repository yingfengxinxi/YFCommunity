package com.wr.api.estate.entity.application.mine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.sql.Date;

/**
 * 车辆管理对象 vehicle
 *
 * @author DIANWEI
 * @date 2022-11-16
 */
@Data
public class Vehicle extends BaseEntity {
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
    private Long plateColor;

    /**
     * 车牌类型:见数据字典 sys_plate_type
     */
    private Long plateType;

    /**
     * 车辆品牌
     */
    private String vehicleBrand;

    /**
     * 0=其他车,1=小型车,2=大型车,3=摩托车
     */
    private Long vehicleType;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 驳回理由
     */
    @Excel(name = "驳回理由")
    private String rejectReason;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
