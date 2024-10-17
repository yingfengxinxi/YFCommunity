package com.wr.estate.entity.vo;

import com.wr.remote.work.deviceorder.DeviceOrder;
import lombok.Data;

/**
 * @Description 设备维修工单实体类 VO
 * @Date 2022/11/4 10:04
 * @Author DIANWEI
 **/
@Data
public class DeviceOrderVO extends DeviceOrder {
    /**
     * 负责人名称，表xccj_community.bus_staff
     */
    private String staffName;
    /**
     * 执行人名称，表xccj_community.bus_staff
     */
    private Long executeName;

    /**
     * 表xccj_work.bus_abnormal 是否异常 0 否 1 是
     */
    private String abnormal;
}
