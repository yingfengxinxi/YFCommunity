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
 * @Date 2024/3/2 13:46
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("巡检工单")
@TableName(value = "xccj_community.bus_patrol_work_order")
public class PatrolWorkOrder extends BaseEntity {

    //	id
    private String id;
    //物业id
    private Long estateId;
    //	所属小区id，表xccj_community.bus_community
    private Long communityId;
    //	设备表id
    private String facilitieEquipmentId;
    //设备编号
    @TableField(exist = false)
    private String facilitieEquipmentNo;
    //工单编号
    private String no;
    //巡检类型数据字典facilitie_equipment_type
    private String facilitieEquipmentType;
    //巡检人姓名，sys_user表user_id
    private String name;
    @TableField(exist = false)
    private String phone;

    //巡检日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date patrolTime;

    //巡检内容json格式
    private String patrolContent;
    //	巡检状态字典值patrol_status 0=待上报1=已完成2=已超期
    private String patrolStatus;
    //设备状态0=正常1=维修
    private String equipmentStatus;
    //巡查点
    @TableField(exist = false)
    private String patroltAddress;
    //安装位置
    @TableField(exist = false)
    private String installationLocation;
    //其他备注
    private String otherRemark;
    //备注
    private String remarks;
    //巡检图片
    private String patrolImages;
    @TableField(exist = false)
    private List<String> patrolImagesList;
    //工单到期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderExpirationTime;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

}
