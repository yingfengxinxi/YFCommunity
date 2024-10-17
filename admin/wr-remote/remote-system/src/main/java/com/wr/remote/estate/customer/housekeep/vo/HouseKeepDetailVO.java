package com.wr.remote.estate.housekeep.vo;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @description 管家管理修改VO
 * @author 荀彧
 * @date 2022-10-13
 */
public class HouseKeepDetailVO extends BaseEntity {

    // 管理管家主键
    private Long majordomoId;

    // user_id
    private Long userId;

    // 员工名
    private String staffName;

    // 员工手机
    private String staffPhone;

    // 楼栋名
    private String buildingName;

    // 岗位ID
    private Long postId;

    public HouseKeepDetailVO() {
    }

    public Long getMajordomoId() {
        return majordomoId;
    }

    public void setMajordomoId(Long majordomoId) {
        this.majordomoId = majordomoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
