package com.wr.remote.govern.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-02 09:22:15
 * @Desc: 车辆进出记录实体类
 */
@Data
public class VehicleLog extends BaseEntity {

    private static final long serialVersionUID = 1196930967924866234L;
    /**
     * 主键
     */
    private Long logId;

    /**
     * 用户类型：0=业主，1=租客，2=临时车
     */
    private String userType;

    /**
     * 车主姓名，user_type=2时，可能为空
     */
    private String userName;

    /**
     * 手机号，user_type=2时，可能为空
     */
    private String userPhone;

    /**
     * 身份证号，user_type=2时，可能为空
     */
    private String idCardNo;

    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 车牌类型:0=标准民用车/军车,1=02式民用车牌,2=武警车车牌,3=警车车牌,4=民用车双行尾牌,5=使馆车牌,6=农用车车牌,7=摩托车,8=新能源车,255=其它车牌
     */
    private Integer plateType;

    /**
     * 车牌颜色:0=蓝色,1=黄色,2=白色,3=黑色,4=绿色,5=民航黑色,255=其他颜色
     */
    private Integer plateColor;

    /**
     * 车辆类型:0=其他车,1=小型车,2=大型车,3=摩托车
     */
    private Integer vehicleType;

    /**
     * 车辆颜色:0=其他颜色,1=白色,2=银色,3=灰色,4=黑色,5=红色,6=深蓝色,7=蓝色,8=黄色,9=绿色,10=棕色,11=粉色,12=紫色,13=深灰,14=杏色,255=未识别的车辆颜色
     */
    private Integer vehicleColor;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 车库类型：0=车库，1=大门
     */
    private String parkType;

    /**
     * 停车库编号
     */
    private String parkIndex;

    /**
     * 停车库名称
     */
    private String parkName;

    /**
     * 出入口编号
     */
    private String gateIndex;

    /**
     * 出入口名称
     */
    private String gateName;

    /**
     * 车道编号
     */
    private String roadwayIndex;

    /**
     * 车道名称
     */
    private String roadwayName;

    /**
     * 进出场类型，0：进场，1：出场
     */
    private String inoutType;

    /**
     * 进出场发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inoutTime;

    /**
     * 进出场开门时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inoutOpen;

    /**
     * 支付状态：0=未支付，1=已付
     */
    private String payStatus;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    private String tableName;
}
