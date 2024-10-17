package com.wr.estate.entity.vo;

import com.wr.estate.entity.VenueEntity;
import org.bouncycastle.cms.PasswordRecipientId;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueVo
 * @History:
 */
public class VenueVo extends VenueEntity {
    private Long staffId;
    private Long userId;
    private String staffName;
    private String staffPhone;
    private String gender;
    private String venueStatusName;


    public String getVenueStatusName() {
        return venueStatusName;
    }

    public void setVenueStatusName(String venueStatusName) {
        this.venueStatusName = venueStatusName;
    }

    @Override
    public Long getStaffId() {
        return staffId;
    }

    @Override
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "VenueVo{" +
                "staffId=" + staffId +
                ", userId=" + userId +
                ", staffName='" + staffName + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", gender='" + gender + '\'' +
                ", venueStatusName='" + venueStatusName + '\'' +
                '}';
    }
}
