package com.wr.system.business.domain.screen;

import java.io.Serializable;

public class CameraVo implements Serializable {
    private static final long serialVersionUID = 933654611454963188L;
    /**设备id*/
    private String cameraId;
    /**唯一编码*/
    private String indexCode;
    /**资源名称*/
    private String cameraName;
    /**海康服务器ip*/
    private String hikIp;
    /**海康服务器port*/
    private String hikPort;
    /**海康服务ak*/
    private String hikAk;
    /**海康服务sk*/
    private String hikSk;
    /**小区id*/
    private Long communityId;
    /**路径*/
    private String cameraUrl;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getHikIp() {
        return hikIp;
    }

    public void setHikIp(String hikIp) {
        this.hikIp = hikIp;
    }

    public String getHikPort() {
        return hikPort;
    }

    public void setHikPort(String hikPort) {
        this.hikPort = hikPort;
    }

    public String getHikAk() {
        return hikAk;
    }

    public void setHikAk(String hikAk) {
        this.hikAk = hikAk;
    }

    public String getHikSk() {
        return hikSk;
    }

    public void setHikSk(String hikSk) {
        this.hikSk = hikSk;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCameraUrl() {
        return cameraUrl;
    }

    public void setCameraUrl(String cameraUrl) {
        this.cameraUrl = cameraUrl;
    }

    @Override
    public String toString() {
        return "CameraVo{" +
                "cameraId='" + cameraId + '\'' +
                ", indexCode='" + indexCode + '\'' +
                ", name='" + cameraName + '\'' +
                ", hikIp='" + hikIp + '\'' +
                ", hikPort='" + hikPort + '\'' +
                ", hikAk='" + hikAk + '\'' +
                ", hikSk='" + hikSk + '\'' +
                ", communityId=" + communityId +
                ", CameraUrl='" + cameraUrl + '\'' +
                '}';
    }
}
