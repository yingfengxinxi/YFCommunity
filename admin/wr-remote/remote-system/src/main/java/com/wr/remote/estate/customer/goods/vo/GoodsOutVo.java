package com.wr.remote.estate.customer.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.estate.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 13:38:06
 * @Desc:
 */
public class GoodsOutVo implements Serializable {

    private static final long serialVersionUID = 6740807372814581139L;

    /** id*/
    private Long outId;

    private Long communityId;

    /** 出门标题*/
    private String outTitle;

    /** 提交人*/
    private String accountName;

    /** 手机号*/
    private String loginName;

    private Long passageId;

    /** 出门地点*/
    private String passageName;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /** 出门时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    private Long staffId;

    /** 操作人*/
    private String staffName;

    /** 出门状态*/
    private String outStatus;

    private List<Image> images;

    /** 出门说明*/
    private String remark;

    public String getRemark() {
        return remark;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getOutTitle() {
        return outTitle;
    }

    public void setOutTitle(String outTitle) {
        this.outTitle = outTitle;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassageName() {
        return passageName;
    }

    public void setPassageName(String passageName) {
        this.passageName = passageName;
    }

    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getPassageId() {
        return passageId;
    }

    public void setPassageId(Long passageId) {
        this.passageId = passageId;
    }
}
