package com.wr.remote.estate.center;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车位管理表
 *
 * @Author: lvzy
 * @Date: 2022-10-11 11:56:45
 * @Desc: 车位管理表
 */
@Data
@ApiModel("车位管理")
@TableName(value = "xccj_community.bus_carport")
public class BusCarport extends BaseEntity {

    /**
     * 主键
     */
    private Long carportId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 车位编号
     */
    private String carportNo;

    /**
     * 车位属性：0=人防；1=非人防
     */
    private String carportNature;

    /**
     * 车位状态：0=开启，1=关闭
     */
    private String carportStatus;

    /**
     * 租售类型：0=售；1=年租；2=月租
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
     * 续租结束时间
     */
    private String rentalOver;

    /**
     * 产权人id，表bus_account
     */
    private Long accountId;

    /**
     * 车辆id，表bus_vehicle
     */
    private Long vehicleId;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 所属房间ID bus_owner
     */
    private Long roomId;

    /**
    * 租售状态：0待租售；1已租赁；2已出售；3待续费
    **/
    private String allStatus;
}