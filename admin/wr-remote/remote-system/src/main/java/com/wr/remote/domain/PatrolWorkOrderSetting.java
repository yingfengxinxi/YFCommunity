package com.wr.remote.domain;

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
 * @Date 2024/3/2 9:42
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("巡检工单设置")
@TableName(value = "xccj_community.bus_patrol_work_order_setting")
public class PatrolWorkOrderSetting extends BaseEntity {

    //	id
    private String id;
    //物业id
    private Long estateId;
    //	所属小区id，表xccj_community.bus_community
    private Long communityId;
    //设备类型字典表 facilitie_equipment_type
    private String facilitieEquipmentType;
    //	巡检状态0=关1=开
    private String patrolStatus;
    //巡检频率0=每日1=每月
    private String frequency;
    //是否生成过订单0=否1=是
    private String isOrder;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;
    //工单到期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderExpirationTime;

}
