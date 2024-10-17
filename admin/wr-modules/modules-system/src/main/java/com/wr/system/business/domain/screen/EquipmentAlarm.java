package com.wr.system.business.domain.screen;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/31
 * 设备告警，表sc_warn_event
 */
public class EquipmentAlarm implements Serializable {
    private static final long serialVersionUID = -8787256993614195694L;
    /**
     * 设备告警名称
     */
    private String equipmentAlarmName;
    /**
     * 设备告警数量
     */
    private Long equipmentAlarmNum;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    public String getEquipmentAlarmName() {
        return equipmentAlarmName;
    }

    public void setEquipmentAlarmName(String equipmentAlarmName) {
        this.equipmentAlarmName = equipmentAlarmName;
    }

    public Long getEquipmentAlarmNum() {
        return equipmentAlarmNum;
    }

    public void setEquipmentAlarmNum(Long equipmentAlarmNum) {
        this.equipmentAlarmNum = equipmentAlarmNum;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
