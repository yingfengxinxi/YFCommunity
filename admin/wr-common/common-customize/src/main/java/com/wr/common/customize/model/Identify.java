package com.wr.common.customize.model;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-07-16 17:56:58
 * @Desc:
 */
public class Identify implements Serializable {
    private static final long serialVersionUID = 7145485443754592379L;

    /**事件流水号，32位以内*/
    private String eventId;

    /**设备编号（序列号），用于区分设备，固定9位*/
    private String deviceIndexCode;

    /**设备IP地址*/
    private String devIp;
    /**
     * 事件类型
     * 0-人证比对测温事件；
     * 1-二维码测温事件；
     * 2-刷脸测温事件
     */
    private Integer authType;
    /**
     * 设备人证比对后读出的身份证号码
     * or识别到的二维码字符串
     * or单独刷脸后从设备本地取出的人员身份证号（需提前下发到卡号字段），256位以内
     */
    private String authMsg;
    /**
     * 门禁模式下权限比对通过、直通模式下人证通过等能获取到的人员姓名
     */
    private String name;

    /**人脸温度数据*/
    private Float authTempture;
    /**
     * 温度是否异常
     * 0-正常，1-异常
     */
    private Integer temptureAbnormal;

    /**抓拍图片数据（base64）*/
    private String picData;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDeviceIndexCode() {
        return deviceIndexCode;
    }

    public void setDeviceIndexCode(String deviceIndexCode) {
        this.deviceIndexCode = deviceIndexCode;
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getAuthMsg() {
        return authMsg;
    }

    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAuthTempture() {
        return authTempture;
    }

    public void setAuthTempture(Float authTempture) {
        this.authTempture = authTempture;
    }

    public Integer getTemptureAbnormal() {
        return temptureAbnormal;
    }

    public void setTemptureAbnormal(Integer temptureAbnormal) {
        this.temptureAbnormal = temptureAbnormal;
    }

    public String getPicData() {
        return picData;
    }

    public void setPicData(String picData) {
        this.picData = picData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identify{")
                .append("eventId=\"").append(eventId).append("\"")
                .append(",deviceIndexCode=\"").append(deviceIndexCode).append("\"")
                .append(",devIp=\"").append(devIp).append("\"")
                .append(",authType=").append(authType)
                .append(",authMsg=\"").append(authMsg).append("\"")
                .append(",name=\"").append(name).append("\"")
                .append(",authTempture=\"").append(authTempture).append("\"")
                .append(",temptureAbnormal=\"").append(temptureAbnormal).append("\"");
        return sb.toString();
    }
}
