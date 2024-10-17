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
 * @Date 2024/3/4 11:20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("抄表设施管理")
@TableName(value = "xccj_community.bus_meter_reading_equipment")
public class MeterReadingEquipment extends BaseEntity {

    //	id
    private String id;
    //物业id
    private Long estateId;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;
    //工单编号
    private String equipmentNo;
    //能耗类型字典表 energy_consumption_type
    private String energyConsumptionType;
    //抄表类型数据字典meter_reading_type
    private String meterReadingType;
    //是否使用0=否1=是
    private String status;
    //工单到期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderExpirationTime;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
