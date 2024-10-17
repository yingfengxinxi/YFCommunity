package com.wr.remote.estate.center;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 车辆管理表
 * @Author: lvzy
 * @Date: 2022-10-11 19:39:54
 * @Desc: 车辆管理表
 */
@Data
public class BusVehicle extends BaseEntity {

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

    /**
     * 车牌颜色 sys_plate_color
     */
    private Integer plateColor;

    /**
     * 车牌类型  sys_plate_type
     */
    private Integer plateType;

    /**
     * 车辆状态：0=正常，1=关闭
     */
    private String vehicleStatus;



    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

}
