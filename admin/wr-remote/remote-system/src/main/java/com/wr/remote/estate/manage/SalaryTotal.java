package com.wr.remote.estate.manage;


import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 16:41:54
 * @Desc:
 */
public class SalaryTotal extends BaseEntity {

    private static final long serialVersionUID = 8764506308615203416L;

    /** 薪资总称 */
    @Excel(name = "薪资总称")
    private String salaryName;


    /** 薪资时间 */
    @Excel(name = "薪资时间")
    private String salaryTime;

    /** 出勤人数 */
    @Excel(name = "出勤人数")
    private String attendance;

    /** 薪资费用支出 */
    @Excel(name = "薪资费用支出")
    private String salaryExpenses;

    /** 满勤天数 */
    @Excel(name = "满勤天数")
    private String days;

    /** 出勤天数 */
    @Excel(name = "出勤天数")
    private String workDays;

    /** 发放工龄奖 */
    @Excel(name = "发放工龄奖")
    private String salarySeniority;

    /** 发放满勤奖 */
    @Excel(name = "发放满勤奖")
    private String fullSalary;

    /** 代扣代缴社保费用 */
    @Excel(name = "代扣代缴社保费用")
    private String guarantee;

    public String getFullSalary() {
        return fullSalary;
    }

    public void setFullSalary(String fullSalary) {
        this.fullSalary = fullSalary;
    }

    /** 迟到扣除 */
    @Excel(name = "迟到扣除")
    private String salaryLate;

    /** 个人所得税 */
    @Excel(name = "个人所得税")
    private String realitySalary;

    /** 实发总工资 */
    @Excel(name = "实发总工资")
    private String wages;

    public String getSalaryName() {
        return salaryName;
    }

    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName;
    }

    public String getSalaryTime() {
        return salaryTime;
    }

    public void setSalaryTime(String salaryTime) {
        this.salaryTime = salaryTime;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getSalaryExpenses() {
        return salaryExpenses;
    }

    public void setSalaryExpenses(String salaryExpenses) {
        this.salaryExpenses = salaryExpenses;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getWorkDays() {
        return workDays;
    }

    public void setWorkDays(String workDays) {
        this.workDays = workDays;
    }

    public String getSalarySeniority() {
        return salarySeniority;
    }

    public void setSalarySeniority(String salarySeniority) {
        this.salarySeniority = salarySeniority;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getSalaryLate() {
        return salaryLate;
    }

    public void setSalaryLate(String salaryLate) {
        this.salaryLate = salaryLate;
    }

    public String getRealitySalary() {
        return realitySalary;
    }

    public void setRealitySalary(String realitySalary) {
        this.realitySalary = realitySalary;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    @Override
    public String toString() {
        return "SalaryTotal{" +
                "salaryName='" + salaryName + '\'' +
                ", salaryTime='" + salaryTime + '\'' +
                ", attendance='" + attendance + '\'' +
                ", salaryExpenses='" + salaryExpenses + '\'' +
                ", days='" + days + '\'' +
                ", workDays='" + workDays + '\'' +
                ", salarySeniority='" + salarySeniority + '\'' +
                ", fullSalary='" + fullSalary + '\'' +
                ", guarantee='" + guarantee + '\'' +
                ", salaryLate='" + salaryLate + '\'' +
                ", realitySalary='" + realitySalary + '\'' +
                ", wages='" + wages + '\'' +
                '}';
    }
}
