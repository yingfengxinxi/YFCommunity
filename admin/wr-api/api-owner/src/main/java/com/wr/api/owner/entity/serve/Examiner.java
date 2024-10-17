package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 医疗检测通告 实体，表：bus_examiner
 *
 * @author Sjiang
 * @since 2022-11-11 10:27:06
 */
@Data
public class Examiner extends BaseEntity {

    private static final long serialVersionUID = 6141357221266208274L;

    /**
     * 主键id
     */
    private Long examinerId;
    /**
     * 所属医疗档案库
     */
    private Long examinerMedical;
    /**
     * 体检人员名字
     */
    private String examinerName;
    /**
     * 证件号码
     */
    private String cardNo;
    /**
     * 体检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date examinerTime;
    /**
     * 体检医生
     */
    private String examinerDoctor;
    /**
     * 体检报告状态 0=导入 1= 未导入
     */
    private Long examinerImportStatus;
    /**
     * 报告发送状态 0 = 发送 1= 未发生
     */
    private Long examinerSendStatus;
    /**
     * 报告发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date examinerReportTime;
    /**
     * 检测报告路径
     */
    private String examinerReportPath;

    @Override
    public String toString() {
        return "Examiner{" +
                "examinerId=" + examinerId +
                ", examinerMedical=" + examinerMedical +
                ", examinerName='" + examinerName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", examinerTime=" + examinerTime +
                ", examinerDoctor='" + examinerDoctor + '\'' +
                ", examinerImportStatus=" + examinerImportStatus +
                ", examinerSendStatus=" + examinerSendStatus +
                ", examinerReportTime=" + examinerReportTime +
                ", examinerReportPath='" + examinerReportPath + '\'' +
                '}';
    }
}

