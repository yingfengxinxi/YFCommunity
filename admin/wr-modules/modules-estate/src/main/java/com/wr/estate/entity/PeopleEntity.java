package com.wr.estate.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/12
 * @Description:
 * @FileName: PeopleEntity
 * @History:
 */
public class PeopleEntity extends BaseEntity {

    private long ownerId;
    private String ownerName;
    private String ownerPhone;
    private long gender;
    private String cardNo;
    private String cardType;
    private String cardFont;
    private String cardBack;
    private String faceUrl;
    private long ownerRelation;

    private long oldageT;

    private String oldDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Override
    public String toString() {
        return "PeopleEntity{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", gender=" + gender +
                ", cardNo='" + cardNo + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardFont='" + cardFont + '\'' +
                ", cardBack='" + cardBack + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                ", ownerRelation=" + ownerRelation +
                ", oldageT=" + oldageT +
                ", oldDate='" + oldDate + '\'' +
                ", date=" + date +
                '}';
    }

    public long getOldageT() {
        return oldageT;
    }

    public void setOldageT(long oldageT) {
        this.oldageT = oldageT;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public long getOwnerRelation() {
        return ownerRelation;
    }

    public void setOwnerRelation(long ownerRelation) {
        this.ownerRelation = ownerRelation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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

    public long getGender() {
        return gender;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardFont() {
        return cardFont;
    }

    public void setCardFont(String cardFont) {
        this.cardFont = cardFont;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
}
