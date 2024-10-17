package com.wr.remote.govern.manage;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 09:20:10
 * @Desc: 网格管理实体类
 */
public class Grid extends BaseEntity {

    /**
     * 主键
     */
    private Long gridId;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 管理员id，表xccj_community.bus_staff
     */
    private Long staffId;

    /**
     * 区域名称
     */
    private String gridName;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 区域位置描述
     */
    private String remark;


    public Grid() {

    }

    public Long getGridId() {
        return gridId;
    }

    public void setGridId(Long gridId) {
        this.gridId = gridId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
