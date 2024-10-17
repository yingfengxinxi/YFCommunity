package com.wr.remote.estate.center.vo;

import com.wr.remote.estate.center.BusCarport;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车位管理扩展类
 * @Author: lvzy
 * @Date: 2022-10-14 13:53:04
 * @Desc: 车位管理扩展类
 */
@Data
public class BusCarportVo extends BusCarport {

    /**
     * 车辆ID
     */
    private Long vehicleId;

    /**
     * 所属项目  小区，表bus_community
     */
    private String communityName;

    /**
     * 联系方式  bus_account.login_name
     */
    private Long loginPhone;

    /**
     * 转出-产权人姓名
     */
    private String accountName;

    /**
     * 产权人姓名展示
     */
    private String accountNames;

    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 租售方式
     * rental_type 判断0为售, 其他为租
     */
    private String rentalMode;
    private String rentalTypeValue;

    /**
     * 租售时间间隔
     */
    private String dateInterval;

    /**
     * 租售类型 名称
     */
    private String rentalTypeName;

    /**
     * 租售人身份证号码
     */
    private String cardNo;
    //租售状态：0待租售；1已租赁；2已出售；3待续费
    private String allStatus;

    /**
     * 车位费用 (售)
     */
    private String carportFeeSell;

    /**
     * 车位费用 (月)
     */
    private String carportFeeMonth;

    /**
     * 车位费用 (年)
     */
    private String carportFeeYear;

    //车位编号ID
    private String carportNoId;

    //车位支付状态
    private String orderStatus;

    //订单编号
    private String orderNo;

    @Override
    public Long getVehicleId() {
        return vehicleId;
    }

    @Override
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(Long loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNames() {
        return accountNames;
    }

    public void setAccountNames(String accountNames) {
        this.accountNames = accountNames;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getRentalMode() {
        return rentalMode;
    }

    public void setRentalMode(String rentalMode) {
        this.rentalMode = rentalMode;
    }

    public String getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(String dateInterval) {
        this.dateInterval = dateInterval;
    }

    public String getRentalTypeName() {
        return rentalTypeName;
    }

    public void setRentalTypeName(String rentalTypeName) {
        this.rentalTypeName = rentalTypeName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCarportFeeSell() {
        return carportFeeSell;
    }

    public void setCarportFeeSell(String carportFeeSell) {
        this.carportFeeSell = carportFeeSell;
    }

    public String getCarportFeeMonth() {
        return carportFeeMonth;
    }

    public void setCarportFeeMonth(String carportFeeMonth) {
        this.carportFeeMonth = carportFeeMonth;
    }

    public String getCarportFeeYear() {
        return carportFeeYear;
    }

    public void setCarportFeeYear(String carportFeeYear) {
        this.carportFeeYear = carportFeeYear;
    }

    @Override
    public String toString() {
        return "BusCarportVo{" +
                "vehicleId=" + vehicleId +
                ", communityName='" + communityName + '\'' +
                ", loginPhone=" + loginPhone +
                ", accountName='" + accountName + '\'' +
                ", accountNames='" + accountNames + '\'' +
                ", plateNo='" + plateNo + '\'' +
                ", rentalMode='" + rentalMode + '\'' +
                ", dateInterval='" + dateInterval + '\'' +
                ", rentalTypeName='" + rentalTypeName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", carportFeeSell='" + carportFeeSell + '\'' +
                ", carportFeeMonth='" + carportFeeMonth + '\'' +
                ", carportFeeYear='" + carportFeeYear + '\'' +
                '}';
    }
}
