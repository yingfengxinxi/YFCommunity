package com.wr.system.business.domain.screen;

import lombok.Data;

/**
 * @Author: GuanYu
 * @Date: 2021-09-15 10:16:06
 * @Desc: 设备统计实体类
 */
@Data
public class EquipmentStatistics {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 门禁设备*/
    private Integer access;

    private Integer cloudAccess;

    /** 电梯设备*/
    private Integer elevator;

    /** 监控设备*/
    private Integer camera;

    /**  单元门可视设备 */
    private Integer visual;

    /** 消防传感器*/
     private Integer sensor;

     /** 道闸设备*/
     private Integer barrier;

}
