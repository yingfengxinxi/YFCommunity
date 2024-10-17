package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-15 13:42:21
 * @Desc:
 */
public class EquipmentStatus {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 在线设备数*/
    private Integer online;

    /** 离线设备数*/
    private Integer offline;

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getOffline() {
        return offline;
    }

    public void setOffline(Integer offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return "EquipmentStatus{" +
                "online=" + online +
                ", offline=" + offline +
                '}';
    }
}
