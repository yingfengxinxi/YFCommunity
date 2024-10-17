package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-15 16:56:53
 * @Desc:
 */
public class Carportlog {
    private static final long serialVersionUID = 729568269741632601L;

    /** 主键*/
    private Long logId;

    /** 车位id，表bus_carport*/
    private Long carportId;

    /** 购租人姓名*/
    private String userName;

    /** 购租人手机号*/
    private String userPhone;

    /** 购租人身份证号*/
    private String cardNo;

    /** 车牌号*/
    private String plantNo;

    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 租售开始时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalStart;

    /** 租售结束时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rentalEnd;

    /** 删除标记：0=未删除，1=已删除*/
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
