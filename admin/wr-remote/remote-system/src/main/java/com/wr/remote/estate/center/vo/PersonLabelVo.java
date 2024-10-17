package com.wr.remote.estate.center.vo;


import com.wr.remote.estate.center.PersonLabel;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 09:13:59
 * @Desc:
 */
public class PersonLabelVo extends PersonLabel {

    private static final long serialVersionUID = -7018129787103770705L;

    private String name;
    private String gender;
    private String phone;
    private String cardNo;
    private String buildingName;
    private String unitName;
    private Long ownerId;
    private Long tenantId;
    private Long nameId;

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return "PersonLabelVo{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", ownerId=" + ownerId +
                ", tenantId=" + tenantId +
                ", nameId=" + nameId +
                '}';
    }
}
