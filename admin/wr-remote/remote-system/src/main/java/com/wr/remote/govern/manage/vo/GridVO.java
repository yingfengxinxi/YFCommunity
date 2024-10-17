package com.wr.remote.govern.manage.vo;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.govern.manage.Grid;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 09:20:10
 * @Desc: 网格管理视图类
 */
public class GridVO extends Grid {

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 管理员名称
     */
    private String staffName;


    /**
     * 管理员联系方式
     */
    private String staffPhone;

    /**
     * 管理员名称
     */
    private Long[] communityIds;


    public GridVO() {

    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Long[] getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(Long[] communityIds) {
        this.communityIds = communityIds;
    }
}
