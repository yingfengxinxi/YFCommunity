package com.wr.remote.estate.housekeep.vo;


import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author 荀彧
 * @description 管家管理列表VO
 * @date 2022-10-13
 */
public class HouseKeepListVO extends BaseEntity {

    // 管家管理表主键
    private Long majordomoId;
    // 小区ID
    private Long communityId;
    // 岗位ID
    private Long postId;
    /**
     * 楼栋id
     */
    private Long buildingId;
    // 楼栋名
    private String buildingName;

    private Long staffId;
    // 员工姓名
    private String staffName;

    // 性别
    private String gender;

    // 员工手机号
    private String staffPhone;

    // 年龄
    private Integer age;

    // 岗位
    private String postName;

    public HouseKeepListVO() {
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public Long getMajordomoId() {
        return majordomoId;
    }

    public void setMajordomoId(Long majordomoId) {
        this.majordomoId = majordomoId;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }


}
