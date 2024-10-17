package com.wr.estate.entity.screen;


/**
 * @Author: GuanYu
 * @Date: 2021-09-26 13:47:54
 * @Desc: 大屏设备在线离线数量统计
 */
public class EquipmentCount {

    private static final long serialVersionUID = -8949534726788014138L;

    /**
     * 门禁设备在线数量
     */
    private Integer accessOnLine;
    /**
     * 门禁设备离线数量
     */
    private Integer accessOffLine;
    /**
     * 监控设备在线
     */
    private Integer monitorOnLine;
    /**
     * 监控设备离线
     */
    private Integer monitorOffLine;
    /**
     * 抓拍显示一体机设备在线
     */
    private Integer AIOOnLine;
    /**
     * 抓怕显示一体机设备离线
     */
    private Integer AIOOffLine;

    public Integer getAccessOnLine() {
        return accessOnLine;
    }

    public void setAccessOnLine(Integer accessOnLine) {
        this.accessOnLine = accessOnLine;
    }

    public Integer getAccessOffLine() {
        return accessOffLine;
    }

    public void setAccessOffLine(Integer accessOffLine) {
        this.accessOffLine = accessOffLine;
    }

    public Integer getMonitorOnLine() {
        return monitorOnLine;
    }

    public void setMonitorOnLine(Integer monitorOnLine) {
        this.monitorOnLine = monitorOnLine;
    }

    public Integer getMonitorOffLine() {
        return monitorOffLine;
    }

    public void setMonitorOffLine(Integer monitorOffLine) {
        this.monitorOffLine = monitorOffLine;
    }

    public Integer getAIOOnLine() {
        return AIOOnLine;
    }

    public void setAIOOnLine(Integer AIOOnLine) {
        this.AIOOnLine = AIOOnLine;
    }

    public Integer getAIOOffLine() {
        return AIOOffLine;
    }

    public void setAIOOffLine(Integer AIOOffLine) {
        this.AIOOffLine = AIOOffLine;
    }

    @Override
    public String toString() {
        return "EquipmentCount{" +
                "accessOnLine=" + accessOnLine +
                ", accessOffLine=" + accessOffLine +
                ", monitorOnLine=" + monitorOnLine +
                ", monitorOffLine=" + monitorOffLine +
                ", AIOOnLine=" + AIOOnLine +
                ", AIOOffLine=" + AIOOffLine +
                '}';
    }
}
