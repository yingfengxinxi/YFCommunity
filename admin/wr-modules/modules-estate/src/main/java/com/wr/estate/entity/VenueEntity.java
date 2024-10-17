package com.wr.estate.entity;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueEntity
 * @History:
 */
public class VenueEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long venueId;

    /** 小区id */
    private Long communityId;

    /** 场馆名称 */
    @Excel(name = "场馆名称")
    private String venueName;

    /** 场馆地址 */
    @Excel(name = "场馆地址")
    private String address;

    /** 押金 */
    @Excel(name = "押金")
    private BigDecimal deposit;

    /** 每小时费用 */
    @Excel(name = "每小时费用")
    private BigDecimal hourFee;

    /** 管理员id*/
    @Excel(name = "管理员id")
    private Long staffId;

    /** 场馆图片 */
    @Excel(name = "场馆图片")
    private String venueImage;

    /** 状态：0=可用，1=关闭 */
    @Excel(name = "状态：0=可用，1=关闭")
    private String venueStatus;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;


    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getHourFee() {
        return hourFee;
    }

    public void setHourFee(BigDecimal hourFee) {
        this.hourFee = hourFee;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getVenueImage() {
        return venueImage;
    }

    public void setVenueImage(String venueImage) {
        this.venueImage = venueImage;
    }

    public String getVenueStatus() {
        return venueStatus;
    }

    public void setVenueStatus(String venueStatus) {
        this.venueStatus = venueStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
