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
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/5 12:22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("抄表工单")
@TableName(value = "xccj_community.bus_meter_reading_work_order")
public class MeterReadingWorkOrder extends BaseEntity {

    //	id
    private String id;
    //物业id
    private Long estateId;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;
    //工单编号
    private String no;
    //工单到期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderExpirationTime;
    //抄表时间
    private String meterReadingTime;
    //抄表人信息sys_user表user_id
    private String name;
    @TableField(exist = false)
    private String phone;
    //能耗类型字典表 energy_consumption_type
    private String energyConsumptionType;
    //	抄表类型数据字典meter_reading_type
    private String meterReadingType;
    //抄表状态字典值meter_reading_status
    private String meterReadingStatus;
    @TableField(exist = false)
    private String energyMeterContent;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;
    //是否只读 0=可操作1=不可操作
    @TableField(exist = false)
    private String isRead;

    //0=保存1=提交
    @TableField(exist = false)
    private String type;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;


    @TableField(exist = false)
    private List<MeterReadingRecordWorkOrder> recordWorkOrderList;


}
