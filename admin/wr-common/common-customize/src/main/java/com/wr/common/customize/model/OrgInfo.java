package com.wr.common.customize.model;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-07-22 15:28:19
 * @Desc: 组织区域信息--对应社区
 */
public class OrgInfo implements Serializable {
    private static final long serialVersionUID = -662516377198630257L;

    /**
     * community_id
     */
    private Long clientId;

    /**
     * clientId
     */
    private String orgIndexCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 数据为小区时，值默认为 root000000
     * 数据为其他是值为上级id
     */
    private String parentIndexCode;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getOrgIndexCode() {
        return orgIndexCode;
    }

    public void setOrgIndexCode(String orgIndexCode) {
        this.orgIndexCode = orgIndexCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getParentIndexCode() {
        return parentIndexCode;
    }

    public void setParentIndexCode(String parentIndexCode) {
        this.parentIndexCode = parentIndexCode;
    }

    @Override
    public String toString() {
        return "OrgInfo{" +
                "clientId=" + clientId +
                ", orgIndexCode='" + orgIndexCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", parentIndexCode='" + parentIndexCode + '\'' +
                '}';
    }
}
