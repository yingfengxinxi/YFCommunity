package com.wr.estate.entity.screen;


/**
 * @Author: GuanYu
 * @Date: 2021-09-27 20:08:34
 * @Desc: 物业端大屏物业员工数据
 */
public class CommunityStaff {

    private static final long serialVersionUID = -8949534726788014138L;

    /** 员工总数*/

    private Integer staffTotal;

    /** 维修人员数*/
    private Integer staffRepair;

    /** 安防人员数*/
    private Integer staffSecurity;

    /** 物业场馆人员*/
    private Integer staffProperty;

    /** 维修人员比例*/
    private String repairProportion;

    /** 安防人员比例*/
    private String securityProportion;

    /** 物业管理人员比例*/
    private String propertyProportion;

    public Integer getStaffTotal() {
        return staffTotal;
    }

    public void setStaffTotal(Integer staffTotal) {
        this.staffTotal = staffTotal;
    }

    public Integer getStaffRepair() {
        return staffRepair;
    }

    public void setStaffRepair(Integer staffRepair) {
        this.staffRepair = staffRepair;
    }

    public Integer getStaffSecurity() {
        return staffSecurity;
    }

    public void setStaffSecurity(Integer staffSecurity) {
        this.staffSecurity = staffSecurity;
    }

    public Integer getStaffProperty() {
        return staffProperty;
    }

    public void setStaffProperty(Integer staffProperty) {
        this.staffProperty = staffProperty;
    }

    public String getRepairProportion() {
        return repairProportion;
    }

    public void setRepairProportion(String repairProportion) {
        this.repairProportion = repairProportion;
    }

    public String getSecurityProportion() {
        return securityProportion;
    }

    public void setSecurityProportion(String securityProportion) {
        this.securityProportion = securityProportion;
    }

    public String getPropertyProportion() {
        return propertyProportion;
    }

    public void setPropertyProportion(String propertyProportion) {
        this.propertyProportion = propertyProportion;
    }

    @Override
    public String toString() {
        return "CommunityStaff{" +
                "staffTotal=" + staffTotal +
                ", staffRepair=" + staffRepair +
                ", staffSecurity=" + staffSecurity +
                ", staffProperty=" + staffProperty +
                ", repairProportion='" + repairProportion + '\'' +
                ", securityProportion='" + securityProportion + '\'' +
                ", propertyProportion='" + propertyProportion + '\'' +
                '}';
    }
}
