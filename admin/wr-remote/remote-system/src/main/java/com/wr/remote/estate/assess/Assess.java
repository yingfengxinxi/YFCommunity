package com.wr.remote.estate.assess;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 17:26:27
 * @Desc: 考核记录
 */
public class Assess extends BaseEntity {

    private static final long serialVersionUID = 3085906096043216379L;

    /**
     * 主键id
     */
    private Long assessId;
    /**
     * 考核方法名称
     */
    private String assessName;
    /**
     * 方法id，表xccj_office.bus_assess_method
     */
    private Long methodId;
    /**
     * 是否公示：0=否，1=是
     */
    private String publicStatus;
    /**
     * 状态：0=启用，1=停用
     */
    private String assessEnable;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 删除标记0：未删除1已删除
     */
    private String delFlag;

    public Assess() {
    }

    public Long getAssessId() {
        return assessId;
    }

    public void setAssessId(Long assessId) {
        this.assessId = assessId;
    }

    public String getAssessName() {
        return assessName;
    }

    public void setAssessName(String assessName) {
        this.assessName = assessName;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public String getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(String publicStatus) {
        this.publicStatus = publicStatus;
    }

    public String getAssessEnable() {
        return assessEnable;
    }

    public void setAssessEnable(String assessEnable) {
        this.assessEnable = assessEnable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}

