package com.wr.remote.estate.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WuKong
 * @Date: 2022-10-12 13:39:58
 * @Desc: 灾害类型管理对象
 */
public class BusDisaster extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8506936388381584248L;
    /**
     * 主键
     */
    private Long disasterId;
    /**
     * 物业id
     */
    @Excel(name = "父级物业ID")
    private Long estateId;
    /**
     * 维修工种名称
     */
    @Excel(name = "维修工种名称")
    private String disasterName;
    /**
     * 状态：0=启用，1=停用
     */
    @Excel(name = "状态：0=启用，1=停用")
    private String disasterStatus;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    private Date beginTime;
    private Date endTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BusDisaster{" +
                "disasterId=" + disasterId +
                ", estateId=" + estateId +
                ", disasterName='" + disasterName + '\'' +
                ", disasterStatus='" + disasterStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public Long getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(Long disasterId) {
        this.disasterId = disasterId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getDisasterName() {
        return disasterName;
    }

    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }

    public String getDisasterStatus() {
        return disasterStatus;
    }

    public void setDisasterStatus(String disasterStatus) {
        this.disasterStatus = disasterStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
