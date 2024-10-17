package com.wr.remote.estate.center;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * 车位租售记录
 * @Author: lvzy
 * @Date: 2022-10-14 17:16:48
 * @Desc: 车位租售记录
 */
public class BusCarportLog extends BaseEntity {

    /**
     * 车位记录表ID
     */
    private Long logId;

    /**
     * 车位id，表bus_carport
     */
    private Long carportId;

    /**
     * 购租人姓名
     */
    private String userName;

    /**
     * 购租人手机号
     */
    private String userPhone;

    /**
     * 购租人身份证号
     */
    private String cardNo;

    /**
     * 车牌号
     */
    private String plantNo;

    /**
     * 租售开始时间
     */
    private String rentalStart;

    /**
     * 租售结束时间
     */
    private String rentalEnd;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
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

    public String getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(String rentalStart) {
        this.rentalStart = rentalStart;
    }

    public String getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(String rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "busCarportLog{" +
                "logId=" + logId +
                ", carportId=" + carportId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", plantNo='" + plantNo + '\'' +
                ", rentalStart='" + rentalStart + '\'' +
                ", rentalEnd='" + rentalEnd + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
