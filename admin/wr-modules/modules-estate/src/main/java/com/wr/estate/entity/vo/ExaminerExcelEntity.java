package com.wr.estate.entity.vo;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/18
 * @Description:
 * @FileName: ExaminerExcelEntity
 * @History:
 */
public class ExaminerExcelEntity extends BaseEntity {

    private Long examinerId;

    private Long examinerMedical;
    @Excel(name = "体检名称",type = Excel.Type.ALL)
    private String activityName;
    @Excel(name = "体检人员姓名",type = Excel.Type.ALL)
    private String examinerName;
    @Excel(name = "身份证号",type = Excel.Type.ALL)
    private String cardNo;
    @Excel(name = "体检时间",type = Excel.Type.ALL)
    private Date examinerTime;
    @Excel(name = "体检医生",type = Excel.Type.ALL)
    private String examinerDoctor;
    @Excel(name = "体检报告状态",type = Excel.Type.EXPORT)
    private Long examinerImportStatus;
    @Excel(name = "报告发送状态",type = Excel.Type.EXPORT)
    private Long examinerSendStatus;
    @Excel(name = "体检报告发送时间",type = Excel.Type.EXPORT)
    private Date examinerReportTime;

    @Override
    public String toString() {
        return "ExaminerExcelEntity{" +
                "examinerId=" + examinerId +
                ", examinerMedical=" + examinerMedical +
                ", activityName='" + activityName + '\'' +
                ", examinerName='" + examinerName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", examinerTime=" + examinerTime +
                ", examinerDoctor='" + examinerDoctor + '\'' +
                ", examinerImportStatus=" + examinerImportStatus +
                ", examinerSendStatus=" + examinerSendStatus +
                ", examinerReportTime=" + examinerReportTime +
                '}';
    }

    public Long getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(Long examinerId) {
        this.examinerId = examinerId;
    }

    public Long getExaminerMedical() {
        return examinerMedical;
    }

    public void setExaminerMedical(Long examinerMedical) {
        this.examinerMedical = examinerMedical;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getExaminerName() {
        return examinerName;
    }

    public void setExaminerName(String examinerName) {
        this.examinerName = examinerName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getExaminerTime() {
        return examinerTime;
    }

    public void setExaminerTime(Date examinerTime) {
        this.examinerTime = examinerTime;
    }

    public String getExaminerDoctor() {
        return examinerDoctor;
    }

    public void setExaminerDoctor(String examinerDoctor) {
        this.examinerDoctor = examinerDoctor;
    }

    public Long getExaminerImportStatus() {
        return examinerImportStatus;
    }

    public void setExaminerImportStatus(Long examinerImportStatus) {
        this.examinerImportStatus = examinerImportStatus;
    }

    public Long getExaminerSendStatus() {
        return examinerSendStatus;
    }

    public void setExaminerSendStatus(Long examinerSendStatus) {
        this.examinerSendStatus = examinerSendStatus;
    }

    public Date getExaminerReportTime() {
        return examinerReportTime;
    }

    public void setExaminerReportTime(Date examinerReportTime) {
        this.examinerReportTime = examinerReportTime;
    }
}
