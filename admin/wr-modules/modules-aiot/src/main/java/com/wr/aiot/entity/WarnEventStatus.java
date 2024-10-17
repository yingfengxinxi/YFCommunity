package com.wr.aiot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/4/23 14:24
 */
@Data
@ApiModel("预警事件订阅状态")
@TableName(value = "xccj_device.bus_warn_event_status")
public class WarnEventStatus extends BaseEntity {

    private String id;
    //所属小区
    private Long communityId;
    //事件订阅状态0=未订阅1=已订阅2=取消订阅
    private String status;
    //删除0=未删除1=删除
    @TableLogic
    private String delFlag;

}
