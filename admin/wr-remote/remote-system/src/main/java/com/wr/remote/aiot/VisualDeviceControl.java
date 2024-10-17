package com.wr.remote.aiot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/4/15 12:28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("可视对讲返控记录")
@TableName(value = "xccj_device.bus_visual_device_control")
public class VisualDeviceControl extends BaseEntity {

    private String id;
    //反控操作返回结果码(-1:开门失败 1:开门成功 2:参数非法3:Das地址为空 4:设备无权限)
    @JSONField(name = "controlResultCode")
    private Integer controlResultCode;
    //设备反控结果描述
    @JSONField(name = "controlResultDesc")
    private String controlResultDesc;
    //设备编号
    @JSONField(name = "deviceIndexCode")
    private String deviceIndexCode;
    //小区id
    private Long communityId;
    //楼栋id
    private Long buildingId;
    //单元楼id
    private Long unitId;
    //	房号id
    private Long roomId;
    //业主id
    private Long ownerId;
    @TableLogic
    private String delFlag;

}
