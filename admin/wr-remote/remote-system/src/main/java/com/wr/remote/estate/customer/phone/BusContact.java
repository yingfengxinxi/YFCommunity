package com.wr.remote.estate.phone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @description 常用电话实体类
 * @author 荀彧
 * @date 2022-10-14
 */
@ApiModel("常用电话")
public class BusContact extends BaseEntity {

    /**
     * 主键
     */
    private Long contactId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 联系人名称
     */
    @Excel(name = "联系人员", prompt = "联系人员",sort = 2)
    private String contactName;

    /**
     * 联系人电话
     */
    @Excel(name = "联系方式", prompt = "联系方式",sort = 1)
    private String contactPhone;

    /**
     * 机构名称
     */
    @Excel(name = "所属机构", prompt = "所属机构",sort = 3)
    private String companyName;

    /**
     * 机构地址
     */
    @Excel(name = "机构地址", prompt = "机构地址",sort = 4)
    private String address;

    /** 更新者 */
    @Excel(name = "操作人", prompt = "操作人",sort = 6)
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后操作时间", prompt = "最后操作时间",sort = 5,dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public BusContact() {}

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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
