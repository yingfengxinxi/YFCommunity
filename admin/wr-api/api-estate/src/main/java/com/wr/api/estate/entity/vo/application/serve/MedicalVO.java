package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class MedicalVO {

    /**
     * 医疗标识
     */
    private Long medicalId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 检测机构
     */
    private String medicalOrg;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;
}
