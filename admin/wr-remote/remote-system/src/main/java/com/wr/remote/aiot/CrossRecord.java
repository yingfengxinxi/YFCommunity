package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/4/8 18:02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("车辆出行记录")
@TableName(value = "xccj_device.bus_cross_record")
public class CrossRecord extends BaseEntity {

    private String id;
    private Long communityId;
    //过车记录唯一标识
    @JSONField(name = "crossRecordSyscode")
    private String crossRecordSyscode;

    //停车库唯一标识
    @JSONField(name = "parkSyscode")
    private String parkSyscode;

    //停车库名称
    @JSONField(name = "parkName")
    private String parkName;

    //出入口唯一标识
    @JSONField(name = "entranceSyscode")
    private String entranceSyscode;

    //出入口名称
    @JSONField(name = "entranceName")
    private String entranceName;
    //车道唯一标识
    @JSONField(name = "roadwaySyscode")
    private String roadwaySyscode;

    //车道名称
    @JSONField(name = "roadwayName")
    private String roadwayName;

    //	是否出场0-进场,1-出场
    @JSONField(name = "vehicleOut")
    private String vehicleOut;

    //	放行模式0-禁止放行，1-固定车包期，2-临时车入场，3-预约车入场，10-离线出场，11-缴费出场，12-预付费出场，13-免费出场，30- 非法卡不放行，31-手动放行，32-特殊车辆放行，33-节假日放行，35-群组放行，36-遥控器开闸
    @JSONField(name = "releaseMode")
    private String releaseMode;

    //放行结果0-未放行1-正常放行2-离线放行
    @JSONField(name = "releaseResult")
    private String releaseResult;

    //放行方式10-未开闸11-自动开闸12-人工/人工开闸13-遥控器开闸
    @JSONField(name = "releaseWay")
    private String releaseWay;
    //放行原因，100-固定车自动放行101-临时车自动放行102-预约车自动放行103-一户多车自动放行
    @JSONField(name = "releaseReason")
    private String releaseReason;

    //车牌号
    @JSONField(name = "plateNo")
    private String plateNo;

    //卡片号码
    @JSONField(name = "cardNo")
    private String cardNo;

    //车辆颜色0：其他颜色；1：白色；2：银色；3：灰色；4：黑色；5：红色；6：深蓝色；7：蓝色；8：黄色；9：绿色；10：棕色；11：粉色；12：紫色’
    @JSONField(name = "vehicleColor")
    private String vehicleColor;

    //车辆类型0：其他车；1：小型车；2：大型车；3：摩托车
    @JSONField(name = "vehicleType")
    private String vehicleType;

    //车牌颜色0:蓝色,1:黄色,2:白色,3:黑色,4:绿色,5:民航黑色, 255:其他颜色
    @JSONField(name = "plateColor")
    private String plateColor;

    //车牌类型0:标准民用车,1:02式民用车,3:警车,4:民用车双行尾牌车,5:使馆车,6:农用车,7:摩托车,8:新能源车
    @JSONField(name = "plateType")
    private String plateType;
    //车辆分类
    @JSONField(name = "carCategory")
    private String carCategory;

    //车辆分类名称
    @JSONField(name = "carCategoryName")
    private String carCategoryName;

    //车辆图片uri
    @JSONField(name = "vehiclePicUri")
    private String vehiclePicUri;

    //车牌图片uri
    @JSONField(name = "plateNoPicUri")
    private String plateNoPicUri;

    //人脸图片uri
    @JSONField(name = "facePicUri")
    private String facePicUri;

    //图片服务唯一标识
    @JSONField(name = "facePicUri")
    private String aswSyscode;
    //车主人员id
    private String personId;
    //车主姓名
    private String personName;
    //车辆描述
    private String description;
    //证件类型
    private String certificateType;
    //证件号
    private String certificateNo;
    //车主手机号
    private String phoneNo;
    //性别1=男2=女
    private String gender;

    //通过时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "crossTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date crossTime;

    @TableLogic
    private String delFlag;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

}
