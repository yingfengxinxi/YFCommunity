package com.wr.system.business.domain.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 社区人员结构
 */
public class CommunityPersonnel {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 业主所占比例*/
    private String ownerProportion;

    /** 租客所占比例*/
    private String tenantProportion;

    /** 业主总数*/
    private Integer ownerTotal;

    /** 租客总数*/
    private Integer tenantTotal;


    public String getOwnerProportion() {
        return ownerProportion;
    }

    public void setOwnerProportion(String ownerProportion) {
        this.ownerProportion = ownerProportion;
    }

    public String getTenantProportion() {
        return tenantProportion;
    }

    public void setTenantProportion(String tenantProportion) {
        this.tenantProportion = tenantProportion;
    }

    public Integer getOwnerTotal() {
        return ownerTotal;
    }

    public void setOwnerTotal(Integer ownerTotal) {
        this.ownerTotal = ownerTotal;
    }

    public Integer getTenantTotal() {
        return tenantTotal;
    }

    public void setTenantTotal(Integer tenantTotal) {
        this.tenantTotal = tenantTotal;
    }

    @Override
    public String toString() {
        return "CommunityPersonnel{" +
                "ownerProportion='" + ownerProportion + '\'' +
                ", tenantProportion='" + tenantProportion + '\'' +
                ", ownerTotal=" + ownerTotal +
                ", tenantTotal=" + tenantTotal +
                '}';
    }
}
