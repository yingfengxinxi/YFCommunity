package com.wr.remote.estate.base;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WuKong
 * @Date: 2022-10-12 13:42:03
 * @Desc: 园区报修类型对象
 */
public class BusParkRepair extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6730745706558241599L;
    /** 主键 */
    private Long repairId;
    /** 物业id */
    @Excel(name = "父级物业ID")
    private Long estateId;
    /** 维修工种名称 */
    @Excel(name = "维修工种名称")
    private String repairName;
    /** 状态：0=启用，1=停用 */
    @Excel(name = "状态：0=启用，1=停用")
    private String repairStatus;
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

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "BusParkRepair{" +
                "repairId=" + repairId +
                ", estateId=" + estateId +
                ", repairName='" + repairName + '\'' +
                ", repairStatus='" + repairStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
