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
 * @Date 2024/3/5 12:22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("抄表数据记录表")
@TableName(value = "xccj_community.bus_meter_reading_record_work_order")
public class MeterReadingRecordWorkOrder extends BaseEntity {

    //	id
    private String id;
    //抄表工单id
    private String meterReadingWorkOrderId;
    //设备表id
    private String meterReadingEquipmentId;
    //消耗量
    private String data;
    //设备编号
    @TableField(exist = false)
    private String equipmentNo;

    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
