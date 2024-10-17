package com.wr.remote.estate.center.vo;

import com.wr.common.core.annotation.Excel;
import com.wr.remote.estate.center.BusVehicle;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车辆管理
 *
 * @Author: lvzy
 * @Date: 2022-10-12 09:58:25
 * @Desc: 车辆管理
 */
@Data
public class BusVehicleVo extends BusVehicle {

    /**
     * 所属项目  小区，表bus_community
     */
    private String communityName;
    private Long communityId;

    /**
     * 车主姓名
     */
    @Excel(name = "车主姓名", prompt = "必填")
    private String accountName;

    /**
     * 手机号  bus_account.login_name
     */
    @Excel(name = "车主手机号", prompt = "必填")
    private String loginPhone;

    /**
     * 车主角色 bus_account.account_type
     */
    private String accountType;

    /**
     * 所属楼栋ID
     */
    private Long buildingId;

    /**
     * 所属楼栋名称
     */
    private String buildingName;

    /**
     * 所属单元ID bus_owner
     */
    private Long unitId;

    /**
     * 所属单元名称 bus_owner
     */
    private String unitName;
    private Long accountId;

    /**
     * 所属房间ID bus_owner
     */
    private Long roomId;

    /**
     * 所属房间名称 bus_owner
     */
    private String roomName;

    /**
     * 业户ID
     */
    private String ownerId;

    /**
     * 业主姓名 bus_owner
     */
    private String ownerName;

    /**
     * 联系方式 (业主手机号) bus_owner
     */
    private Long ownerPhone;

    /**
     * 能耗类型 (燃油车, 新能源)
     */
    private String energyType;

    /**
     * 审核人 bus_vehicle.update_by
     */
    private String updateByName;

    /**
     * 车位编号
     */
    private String carportNo;

    /**
     * 租售方式
     * rental_type 判断0为售, 其他为租
     */
    private String rentalMode;

    /**
     * 租售类型 ：0=售；1=年租；2=月租',
     */
    private String rentalType;

    /**
     * 车位费用
     */
    private BigDecimal carportFee;

    /**
     * 租售开始时间
     */
    private String rentalStart;

    /**
     * 租售结束时间
     */
    private String rentalEnd;

    /**
     * 租售时长 (计算)
     */
    private String rentalDuration;

    /**
     * 车位属性：0=人防；1=非人防
     */
    private String carportNature;

    /**
     * 车辆ID 新增用
     */
    private Long ownerCarValueId;

    /**
     * 颜色内容
     */
    private String dictLabel;

    /**
     * 颜色编号
     */
    private String dictSort;

    /**
     * 身份证号码
     */
    private String cardNo;

    /**
     * 车位转让详情 - 身份证号码
     */
    private String cardNos;

    /**
     * 车牌颜色名称
     */
    private String plateColorName;

    /**
     * 车牌类型名称
     */
    private String plateTypeName;

    /**
     * 车辆类型名称
     */
    private String vehicleTypeName;

    /**
     * 车辆颜色名称
     */
    private String vehicleColorName;

    /**
     * 详情车位费用
     */
    private String carportFees;

    /**
     * 详情租售开始时间
     */
    private String rentalStarts;

    /**
     * 详情租售结束时间
     */
    private String rentalEnds;

    //车位id
    private Long carportId;

}
