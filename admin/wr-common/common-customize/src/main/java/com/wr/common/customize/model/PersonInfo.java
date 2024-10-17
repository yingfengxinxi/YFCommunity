package com.wr.common.customize.model;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-07-21 20:25:52
 * @Desc: 人员信息
 */
public class PersonInfo implements Serializable {
    private static final long serialVersionUID = -8244900230452280758L;

    /**
     * 人员Id,系统外人员，人员Id由调用方指定，作为人员唯一标志
     */
    private String personId;

    /**
     * 人员姓名，系统外人员时必填
     */
    private String personName;
    /**
     * 性别，1：男；2：女；0：未知
     */
    private String gender;
    /**
     * 所属组织标识，必须是已存在组织，
     */
    private String orgIndexCode;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 卡号，支持8-20位数字+大写字母的卡号；请根据设备实际能力填写卡号
     */
    private String certificateNo;

    /**
     * 为人脸图片https下载的URL，图片要求浏览器可以直接打开，不支持重定向
     */
    private String faceUrl;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrgIndexCode() {
        return orgIndexCode;
    }

    public void setOrgIndexCode(String orgIndexCode) {
        this.orgIndexCode = orgIndexCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                ", gender='" + gender + '\'' +
                ", orgIndexCode='" + orgIndexCode + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                '}';
    }
}
