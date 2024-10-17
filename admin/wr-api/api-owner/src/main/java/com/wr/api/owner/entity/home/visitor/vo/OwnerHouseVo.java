package com.wr.api.owner.entity.home.visitor.vo;

import com.wr.api.owner.entity.home.visitor.OwnerHouse;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022-11-17 17:57:32
 * @Desc:
 */
public class OwnerHouseVo implements Serializable {
    private static final long serialVersionUID = 3910992046919949336L;


    /**
     * 业主id
     */
    private Long  ownerId;

    /**
     * 业主姓名
     */
    private String  ownerName;
    /**
     * 业主手机号
     * */
    private String ownerPhone;

    /**
     * 业主身份证
     */
    private String cardNo;

    /**
     * 存放用户地址
     */
    private List<OwnerHouse> ownerHouseList;


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
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

    public List<OwnerHouse> getOwnerHouseList() {
        return ownerHouseList;
    }

    public void setOwnerHouseList(List<OwnerHouse> ownerHouseList) {
        this.ownerHouseList = ownerHouseList;
    }


    @Override
    public String toString() {
        return "OwnerHouseVo{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", ownerHouseList=" + ownerHouseList +
                '}';
    }
}
