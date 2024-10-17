package com.wr.system.business.domain.screen;

import java.io.Serializable;

public class UserTableLog implements Serializable {

    private static final long serialVersionUID = 3219999247727856776L;

    private Long communityId;
    private String data;
    private String tableName;
    private String tablesName;
    private String userType;
    private String discernTime;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablesName() {
        return tablesName;
    }

    public void setTablesName(String tablesName) {
        this.tablesName = tablesName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDiscernTime() {
        return discernTime;
    }

    public void setDiscernTime(String discernTime) {
        this.discernTime = discernTime;
    }

    @Override
    public String toString() {
        return "UserTableLog{" +
                "communityId=" + communityId +
                ", data='" + data + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tablesName='" + tablesName + '\'' +
                ", userType='" + userType + '\'' +
                ", discernTime='" + discernTime + '\'' +
                '}';
    }
}
