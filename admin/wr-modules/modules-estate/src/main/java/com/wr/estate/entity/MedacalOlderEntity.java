package com.wr.estate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/17
 * @Description:
 * @FileName: MedacalOlderEntity
 * @History:
 */
public class MedacalOlderEntity extends BaseEntity {

    private long examinerId;
    @Excel(name = "体检名称",type = Excel.Type.ALL)
    private String activityName;
    @Excel(name = "体检人员姓名",type = Excel.Type.ALL)
    private String ownerName;
    @Excel(name = "体检人员身份证号",type = Excel.Type.ALL)
    private String cardNo;
    @Excel(name = "体检人员性别",type = Excel.Type.ALL,readConverterExp = "0=男,1=女,2=未知")
    private long olderGender;
    @Excel(name = "体检人员年龄",type = Excel.Type.ALL)
    private long olderAge;
    @Excel(name = "体检时间",dateFormat = "yyyy-MM-dd",width = 30,type = Excel.Type.ALL)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date examinerTime;
    @Excel(name = "体检办理机构",type = Excel.Type.ALL)
    private String medicalOrg;
    @Excel(name = "体检医生",type = Excel.Type.ALL)
    private String examinerDoctor;
    @Excel(name = "体检报告状态",type = Excel.Type.ALL,readConverterExp = "0=已导入,1=未导入")
    private long examinerImportStatus;
    @Excel(name = "报告发送状态",type = Excel.Type.ALL,readConverterExp = "0=已发送,1=未发送")
    private long examinerSendStatus;
    @Excel(name = "报告发送时间",dateFormat = "yyyy-MM-dd",width = 30,type = Excel.Type.ALL)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date examinerReportTime;

    private String examinerReportPath;

    private Long medicalId;

    public Long getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(Long medicalId) {
        this.medicalId = medicalId;
    }

    @Override
    public String toString() {
        return "MedacalOlderEntity{" +
                "examinerId=" + examinerId +
                ", activityName='" + activityName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", olderGender=" + olderGender +
                ", olderAge=" + olderAge +
                ", examinerTime=" + examinerTime +
                ", medicalOrg='" + medicalOrg + '\'' +
                ", examinerDoctor='" + examinerDoctor + '\'' +
                ", examinerImportStatus=" + examinerImportStatus +
                ", examinerSendStatus=" + examinerSendStatus +
                ", examinerReportTime=" + examinerReportTime +
                ", examinerReportPath='" + examinerReportPath + '\'' +
                '}';
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getOlderGender() {
        return olderGender;
    }

    public void setOlderGender(long olderGender) {
        this.olderGender = olderGender;
    }

    public long getOlderAge() {
        return olderAge;
    }

    public void setOlderAge(long olderAge) {
        this.olderAge = olderAge;
    }

    public Date getExaminerTime() {
        return examinerTime;
    }

    public void setExaminerTime(Date examinerTime) {
        this.examinerTime = examinerTime;
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

    public String getExaminerReportPath() {
        return examinerReportPath;
    }

    public void setExaminerReportPath(String examinerReportPath) {
        this.examinerReportPath = examinerReportPath;
    }
}
