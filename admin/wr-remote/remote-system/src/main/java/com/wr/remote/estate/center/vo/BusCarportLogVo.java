package com.wr.remote.estate.center.vo;

import com.wr.remote.estate.center.BusCarportLog;

/**
 * 车位租售记录扩展类
 * @Author: lvzy
 * @Date: 2022-10-14 17:41:14
 * @Desc: 车位租售记录扩展类
 */
public class BusCarportLogVo extends BusCarportLog {

    /**
     * 车牌号
     */
    private String plateNo;

    /**
     * 业主ID
     */
    private Long ownerId;

    /**
     * 产权人id，表bus_account
     */
    private Long accountId;

    /**
     * 车辆id，表bus_vehicle
     */
    private Long vehicleId;

    /**
     * 编号
     */
    private String carporNo;

    /**
     * 租售人身份证号码
     */
    private String cardNo;

    /**
     * 购租人姓名
     */
    private String accountName;

    /**
     * 购租人手机号
     */
    private String loginPhone;

    /**
     * 车牌号
     */
    private String ownerCarValueId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
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

    public String getOwnerCarValueId() {
        return ownerCarValueId;
    }

    public void setOwnerCarValueId(String ownerCarValueId) {
        this.ownerCarValueId = ownerCarValueId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    @Override
    public String getCardNo() {
        return cardNo;
    }

    @Override
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCarporNo() {
        return carporNo;
    }

    public void setCarporNo(String carporNo) {
        this.carporNo = carporNo;
    }
}
