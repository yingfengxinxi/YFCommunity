package com.wr.remote.estate.property;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/19 19:47
 * 个人资产
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("个人资产")
@TableName(value = "xccj_community.bus_staff_property")
public class StaffProperty extends BaseEntity {

    /**
     * 主键
     */
    private String id;

    //小区id，表xccj_community.bus_community
    private Long communityId;
    @TableField(exist = false)
    private String communityName;

    /**
     * 员工id,xccj_community.bus_staff
     */
    private Long userId;

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

    /**
     * 驳回理由
     */
    @Excel(name = "驳回理由")
    private String rejectReason;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    @TableLogic
    private String delFlag;
    //租赁购买0=租1买
    private String leasePurchase;
    //购买套餐0=包年1=包月
    private String purchasePackage;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    //year_month
    @TableField(value = "`year_month`")
    private Integer yearMonth;
    //行驶证图片
    private String drivingLicense;
    //身份证图片
    private String idCard;
    //其他图片
    private String other;
    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String userName;
    //
    @TableField(exist = false)
    private String carportNature;
}
