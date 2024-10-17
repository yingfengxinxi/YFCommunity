package com.wr.remote.estate.manage;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * @Author: lvzy
 * @Date: 2022-10-11 16:41:54
 * @Desc:
 */
public class Salary extends BaseEntity {

    private static final long serialVersionUID = 8764506308615203416L;

    /** 主键 */
    private Long salaryId;

    /** 小区id，表bus_community */
    private Long communityId;

    /** 员工id，表bus_staff */
    private Long staffId;

    /** 员工姓名，表bus_staff */
    @Excel(name = "员工姓名")
    private String staffName;

    /** 员工姓名，表bus_staff */
    @Excel(name = "身份证号码")
    private String cardNo;

    /** 手机号，表bus_staff */
    @Excel(name = "手机号")
    private String staffPhone;

    /** 满勤天数(天) */
    @Excel(name = "满勤天数(天)")
    private Long fullDay;

    /** 出勤天数(天) */
    @Excel(name = "出勤天数(天)")
    private Long attendDay;

    /** 满勤奖 */
    @Excel(name = "满勤奖")
    private BigDecimal fullAward;

    /** 工龄奖 */
    @Excel(name = "工龄奖")
    private BigDecimal ageAward;

    /** 应发工资 */
    @Excel(name = "应发工资")
    private BigDecimal grossSalary;

    /** 社保费用 */
    @Excel(name = "社保费用")
    private BigDecimal socialFee;

    /** 迟到扣除 */
    @Excel(name = "迟到扣除")
    private BigDecimal lateFee;

    /** 个人所得税 */
    @Excel(name = "个人所得税")
    private BigDecimal taxFee;

    /** 实发工资 */
    @Excel(name = "实发工资")
    private BigDecimal actuallySalary;

    /** 删除标记0：未删除1已删除 */
    private String delFlag;

    /**薪资月份*/
    @Excel(name = "薪资月份(yyyy-MM)",dateFormat="yyyy-MM")
    private String yearMonth;

    private Long loginId;

    /**
     * 物业id
     */
    private Long estateId;

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Long getFullDay() {
        return fullDay;
    }

    public void setFullDay(Long fullDay) {
        this.fullDay = fullDay;
    }

    public Long getAttendDay() {
        return attendDay;
    }

    public void setAttendDay(Long attendDay) {
        this.attendDay = attendDay;
    }

    public BigDecimal getFullAward() {
        return fullAward;
    }

    public void setFullAward(BigDecimal fullAward) {
        this.fullAward = fullAward;
    }

    public BigDecimal getAgeAward() {
        return ageAward;
    }

    public void setAgeAward(BigDecimal ageAward) {
        this.ageAward = ageAward;
    }

    public BigDecimal getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public BigDecimal getSocialFee() {
        return socialFee;
    }

    public void setSocialFee(BigDecimal socialFee) {
        this.socialFee = socialFee;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public BigDecimal getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(BigDecimal taxFee) {
        this.taxFee = taxFee;
    }

    public BigDecimal getActuallySalary() {
        return actuallySalary;
    }

    public void setActuallySalary(BigDecimal actuallySalary) {
        this.actuallySalary = actuallySalary;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }
}
