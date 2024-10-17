package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 10:29:21
 * @Desc: 医疗检测通告 VO
 */
@Data
public class ExaminerVO implements Serializable {
    private static final long serialVersionUID = 8268537709542598534L;

    /**
     * 主键id
     */
    private Long examinerId;
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

    @Override
    public String toString() {
        return "ExaminerVO{" +
                "activityName='" + activityName + '\'' +
                ", medicalOrg='" + medicalOrg + '\'' +
                ", examinerTime=" + examinerTime +
                '}';
    }
}
