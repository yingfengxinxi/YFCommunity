package com.wr.remote.estate.center.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/2/6 10:23
 */
@Data
public class SpaceVehicleVo {

    private Long id;
    //车位编号id
    private String carportNo;
    //车位属性
    private String carportNature;
    //车位类型(0=售1=年租2=月租)
    private String rentalType;
    //费用
    private BigDecimal carportFee;
    //车位租赁开始时间
    private String rentalStart;
    //车位租赁结束时间
    private String rentalEnd;
    //车辆id
    private Long vehicleId;
    //车牌号
    private String plateNo;
    //车辆颜色
    private String plateColor;
    //车辆类型
    private String vehicleType;
    //车辆品牌
    private String vehicleBrand;
}
