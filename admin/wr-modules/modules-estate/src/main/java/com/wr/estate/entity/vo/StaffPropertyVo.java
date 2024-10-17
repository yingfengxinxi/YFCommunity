package com.wr.estate.entity.vo;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/14 19:33
 */
@Data
public class StaffPropertyVo {


    @Excel(name = "姓名", prompt = "必填")
    private String staffName;

    @Excel(name = "手机号", prompt = "必填")
    private String staffPhone;

    @Excel(name = "车位编号", prompt = "必填")
    private String carportNo;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号")
    private String plateNo;
    /**
     * 车辆颜色:见数据字典 sys_vehicle_color  0=其他颜色,1=白色,2=银色,3=灰色,4=黑色,5=红色,6=深蓝色,7=蓝色,8=黄色,9=绿色,10=棕色,11=粉色,12=紫色,13=深灰,14=杏色,15=未识别的车辆颜色
     */
    @Excel(name = "车辆颜色", readConverterExp = "0=其他颜色,1=白色,2=银色,3=灰色,4=黑色,5=红色,6=深蓝色,7=蓝色,8=黄色,9=绿色,10=棕色,11=粉色,12=紫色,13=深灰,14=杏色,255=未识别的车辆颜色",
            combo = "其他颜色,白色,银色,灰色,黑色,红色,深蓝色,蓝色,黄色,绿色,棕色,粉色,紫色,深灰,杏色,未识别的车辆颜色")
    private String vehicleColor;
    /**
     * 车辆类型
     */
    @Excel(name = "车辆类型", readConverterExp = "0=其他车,1=小型车,2=大型车,3=摩托车", combo = "其他车,小型车,大型车,摩托车")
    private String vehicleType;

    /**
     * 车辆品牌
     */
    @Excel(name = "车辆品牌")
    private String vehicleBrand;


}
