package com.wr.estate.entity.vo;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

@Data
public class BusVehicleImportVo {

    private Integer estateId;
    private String createBy;

    @Excel(name = "车主姓名")
    private String accountName;

    @Excel(name = "车主手机号", prompt = "业主移动端登录手机号(必填)")
    private String accountId;

    @Excel(name = "车牌号",prompt="必填")
    private String plateNo;

    //见数据字典 sys_plate_color
    @Excel(name = "车牌颜色", combo = "蓝色,黄色,白色,黑色,绿色,民航黑色,其他颜色", readConverterExp = "0=蓝色,1=黄色,2=白色,3=黑色,4=绿色,5=民航黑色,6=其他颜色",defaultValue="0",prompt = "必填")
    private String plateColor;

    @Excel(name = "车辆品牌",prompt = "必填")
    private String vehicleBrand;

    //见数据字典 sys_vehicle_color
    @Excel(name = "车辆颜色", combo = "其他颜色,白色,银色,灰色,黑色,红色,深蓝色,蓝色,黄色,绿色",
            readConverterExp = "0=其他颜色,1=白色,2=银色,3=灰色,4=黑色,5=红色,6=深蓝色,7=蓝色,8=黄色,9=绿色",defaultValue="1",prompt = "必填")
    private String vehicleColor;

    @Excel(name = "车辆类型", combo = "其他车,小型车,大型车,摩托车",
            readConverterExp = "0=其他车,1=小型车,2=大型车,3=摩托车",prompt = "必填")
    private String vehicleType;

    //见数据字典 sys_plate_type
    @Excel(name = "车牌类型", combo = "标准民用车/军车,02式民用车牌,武警车车牌,警车车牌,民用车双行尾牌,使馆车牌,农用车车牌,摩托车,新能源车,其它车牌",
            readConverterExp = "0=标准民用车/军车,1=02式民用车牌,2=武警车车牌,3=警车车牌,4=民用车双行尾牌,5=使馆车牌,6=农用车车牌,7=摩托车,8=新能源车,9=其它车牌",prompt = "必填")
    private String plateType;

    @Excel(name = "车辆状态", combo = "正常,关闭", readConverterExp = "0=正常，1=关闭",prompt = "必填")
    private String vehicleStatus;


}
