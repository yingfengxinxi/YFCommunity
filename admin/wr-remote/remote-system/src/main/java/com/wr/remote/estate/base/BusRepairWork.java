package com.wr.remote.estate.base;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WuKong
 * @Date: 2022-10-12 13:37:30
 * @Desc: 维修工种管理对象
 */
public class BusRepairWork extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8784239147242216669L;
    /** 主键 */
    private Long workId;
    /** 物业id */
    @Excel(name = "父级物业ID")
    private Long estateId;
    /** 维修工种名称 */
    @Excel(name = "维修工种名称")
    private String workName;
    /** 状态：0=启用，1=停用 */
    @Excel(name = "状态：0=启用，1=停用")
    private String workStatus;
    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;
    private Date beginTime;
    private Date endTime;

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
        return "BusRepairWork{" +
                "workId=" + workId +
                ", estateId=" + estateId +
                ", workName='" + workName + '\'' +
                ", workStatus='" + workStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
