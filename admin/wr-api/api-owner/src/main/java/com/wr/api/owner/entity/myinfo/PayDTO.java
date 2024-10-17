package com.wr.api.owner.entity.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-16 11:54:31
 * @Desc:
 */
public class PayDTO implements Serializable {
    private static final long serialVersionUID = -7271298042882633628L;

    private Long communityId;

    private Long carportId;

    private Long accountId;

    private Long vehicleId;

    private String userName;

    /** 支付方式：H5 JSAPI APP*/
    private String payWay;

    private String userPhone;

    private String userType;

    private String cardNo;

    private String plantNo;

    private String roadwayCode;

    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalEnd;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalStart;

    private Long wechatId;

    private Long alipayId;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getWechatId() {
        return wechatId;
    }

    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }

    public Long getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(Long alipayId) {
        this.alipayId = alipayId;
    }

    public Long getCarportId() {
        return carportId;
    }

    public void setCarportId(Long carportId) {
        this.carportId = carportId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(String plantNo) {
        this.plantNo = plantNo;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public String getRoadwayCode() {
        return roadwayCode;
    }

    public void setRoadwayCode(String roadwayCode) {
        this.roadwayCode = roadwayCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
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

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }
}
