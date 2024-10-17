package com.wr.api.owner.entity.vo;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-11 09:53:12
 * @Desc:
 */
public class MyinfoVo {
    /**房产套数 */
    private String roomCount;
    /**车辆数 */
    private String carCount;
    /**车位数 */
    private String parkCount;
    /**业主名称 */
    private String ownerName;
    /**业主手机号 */
    private String ownerPhone;
    /**业主身份证号 */
    private String cardNo;

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getCarCount() {
        return carCount;
    }

    public void setCarCount(String carCount) {
        this.carCount = carCount;
    }

    public String getParkCount() {
        return parkCount;
    }

    public void setParkCount(String parkCount) {
        this.parkCount = parkCount;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
