package com.wr.api.estate.entity.maillist;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: contactEntity
 * @History:
 */
public class ContactEntity extends BaseEntity {

    private Long estateId;
    private Long contactId;
    private Long communityId;
    private String contactName;
    private String contactPhone;
    private String companyName;
    private String address;


    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "contactEntity{" +
                "estateId=" + estateId +
                ", contactId=" + contactId +
                ", communityId=" + communityId +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
