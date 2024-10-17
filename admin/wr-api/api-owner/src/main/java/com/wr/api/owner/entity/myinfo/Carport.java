package com.wr.api.owner.entity.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-15 10:22:53
 * @Desc:
 */
public class Carport {
    private static final long serialVersionUID = -59702934786912032L;

    /** 主键*/
    private Long carportId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 小区名称，表bus_community*/
    private String communityName;

    /** 车位编号*/
    private String carportNo;

    /** 车位属性：0=人防；1=非人防*/
    private String carportNature;

    /** 车位状态：0=开启，1=关闭*/
    private String carportStatus;

    /** 租售类型：0=售；1=年租；2=月租*/
    private String rentalType;

    /** 车位费用*/
    private BigDecimal carportFee;

    /** 租售开始时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalStart;

    /** 租售结束时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalEnd;

    /** 租售时长 */
    private String rentalTime;

    /** 产权人id，表bus_account*/
    private Long accountId;

    /** 联系方式，表bus_account*/
    private Long accountPhone;

    /** 车辆id，表bus_vehicle*/
    private Long vehicleId;

    /** 创建人*/
    private String createBy;

    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改人*/
    private String updateBy;

    /** 修改时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /** 车牌号 */
    private String plateNo;

    public Long getCarportId() {
        return carportId;
    }

    public void setCarportId(Long carportId) {
        this.carportId = carportId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCarportNo() {
        return carportNo;
    }

    public void setCarportNo(String carportNo) {
        this.carportNo = carportNo;
    }

    public String getCarportNature() {
        return carportNature;
    }

    public void setCarportNature(String carportNature) {
        this.carportNature = carportNature;
    }

    public String getCarportStatus() {
        return carportStatus;
    }

    public void setCarportStatus(String carportStatus) {
        this.carportStatus = carportStatus;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public BigDecimal getCarportFee() {
        return carportFee;
    }

    public void setCarportFee(BigDecimal carportFee) {
        this.carportFee = carportFee;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Long getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(Long accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(String rentalTime) {
        this.rentalTime = rentalTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
