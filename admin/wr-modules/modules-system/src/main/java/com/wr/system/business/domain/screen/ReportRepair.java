package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-10-15 16:36:07
 * @Desc:  报事报修
 */
public class ReportRepair {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 报事报修*/
    private Integer reportRepair;

    /** 保洁*/
    private Integer clean;

    /** 工程维修*/
    private Integer project;

    /** 投诉建议*/
    private Integer complaint;

    /**其他*/
    private Integer other;

    public Integer getReportRepair() {
        return reportRepair;
    }

    public void setReportRepair(Integer reportRepair) {
        this.reportRepair = reportRepair;
    }

    public Integer getClean() {
        return clean;
    }

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "ReportRepair{" +
                "reportRepair=" + reportRepair +
                ", clean=" + clean +
                ", project=" + project +
                ", complaint=" + complaint +
                ", other=" + other +
                '}';
    }
}
