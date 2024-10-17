package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 10:20:40
 * @Desc: ExaminerVO
 */
@Data
public class ExaminerVO implements Serializable {
    private static final long serialVersionUID = 3013127938635064787L;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 检测机构
     */
    private String medicalOrg;
    /**
     * 检测时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date examinerTime;

    /**
     * 检测报告路径
     */
    private String examinerReportPath;
    /**
     * 体检报告状态 0=导入 1= 未导入
     */
    private Long examinerImportStatus;

    private String examinerName;

    @Override
    public String toString() {
        return "ExaminerVO{" +
                "activityName='" + activityName + '\'' +
                ", medicalOrg='" + medicalOrg + '\'' +
                ", examinerTime=" + examinerTime +
                ", examinerReportPath='" + examinerReportPath + '\'' +
                ", examinerImportStatus=" + examinerImportStatus +
                '}';
    }
}
