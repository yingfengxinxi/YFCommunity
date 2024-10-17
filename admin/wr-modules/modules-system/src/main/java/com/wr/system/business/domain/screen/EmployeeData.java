package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-01 10:13:46
 * @Desc:
 */
public class EmployeeData {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 员工总数*/
    private Integer empTotal;

    /** 维修人员*/
    private Integer repairEmp;

    /** 安防人员*/
    private Integer securityEmp;

    /** 物业管理人员*/
    private Integer propertyEmp;

    /** 维修人员比例*/
    private String repairEmpProportion;

    /** 安防人员比例*/
    private String securityEmpProportion;

    /** 物业管理人员比例*/
    private String propertyEmpProportion;

    public Integer getEmpTotal() {
        return empTotal;
    }

    public void setEmpTotal(Integer empTotal) {
        this.empTotal = empTotal;
    }

    public Integer getRepairEmp() {
        return repairEmp;
    }

    public void setRepairEmp(Integer repairEmp) {
        this.repairEmp = repairEmp;
    }

    public Integer getSecurityEmp() {
        return securityEmp;
    }

    public void setSecurityEmp(Integer securityEmp) {
        this.securityEmp = securityEmp;
    }

    public Integer getPropertyEmp() {
        return propertyEmp;
    }

    public void setPropertyEmp(Integer propertyEmp) {
        this.propertyEmp = propertyEmp;
    }

    public String getRepairEmpProportion() {
        return repairEmpProportion;
    }

    public void setRepairEmpProportion(String repairEmpProportion) {
        this.repairEmpProportion = repairEmpProportion;
    }

    public String getSecurityEmpProportion() {
        return securityEmpProportion;
    }

    public void setSecurityEmpProportion(String securityEmpProportion) {
        this.securityEmpProportion = securityEmpProportion;
    }

    public String getPropertyEmpProportion() {
        return propertyEmpProportion;
    }

    public void setPropertyEmpProportion(String propertyEmpProportion) {
        this.propertyEmpProportion = propertyEmpProportion;
    }

    @Override
    public String toString() {
        return "EmployeeData{" +
                "empTotal=" + empTotal +
                ", repairEmp=" + repairEmp +
                ", securityEmp=" + securityEmp +
                ", propertyEmp=" + propertyEmp +
                ", repairEmpProportion='" + repairEmpProportion + '\'' +
                ", securityEmpProportion='" + securityEmpProportion + '\'' +
                ", propertyEmpProportion='" + propertyEmpProportion + '\'' +
                '}';
    }
}
