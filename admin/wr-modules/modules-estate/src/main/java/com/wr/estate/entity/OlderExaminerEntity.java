package com.wr.estate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.security.PrivateKey;
import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/17
 * @Description:
 * @FileName: OlderExaminerEntity
 * @History:
 */
public class OlderExaminerEntity {
    private long examinerId;

    private String activityName;

    private String medicalOrg;

    private String examinerDoctor;

    private long examinerImportStatus;

    private long examinerSendStatus;

    private long medicalType;

    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date examinerReportTime;


    @Override
    public String toString() {
        return "OlderExaminerEntity{" +
                "examinerId=" + examinerId +
                ", activityName='" + activityName + '\'' +
                ", medicalOrg='" + medicalOrg + '\'' +
                ", examinerDoctor='" + examinerDoctor + '\'' +
                ", examinerImportStatus=" + examinerImportStatus +
                ", examinerSendStatus=" + examinerSendStatus +
                ", medicalType=" + medicalType +
                ", examinerReportTime=" + examinerReportTime +
                '}';
    }

    public long getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(long medicalType) {
        this.medicalType = medicalType;
    }

    public long getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(long examinerId) {
        this.examinerId = examinerId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getMedicalOrg() {
        return medicalOrg;
    }

    public void setMedicalOrg(String medicalOrg) {
        this.medicalOrg = medicalOrg;
    }

    public String getExaminerDoctor() {
        return examinerDoctor;
    }

    public void setExaminerDoctor(String examinerDoctor) {
        this.examinerDoctor = examinerDoctor;
    }

    public long getExaminerImportStatus() {
        return examinerImportStatus;
    }

    public void setExaminerImportStatus(long examinerImportStatus) {
        this.examinerImportStatus = examinerImportStatus;
    }

    public long getExaminerSendStatus() {
        return examinerSendStatus;
    }

    public void setExaminerSendStatus(long examinerSendStatus) {
        this.examinerSendStatus = examinerSendStatus;
    }

    public Date getExaminerReportTime() {
        return examinerReportTime;
    }

    public void setExaminerReportTime(Date examinerReportTime) {
        this.examinerReportTime = examinerReportTime;
    }
}
