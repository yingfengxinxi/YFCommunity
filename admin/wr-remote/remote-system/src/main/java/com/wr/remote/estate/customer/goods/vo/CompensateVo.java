package com.wr.remote.estate.customer.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 11:26:47
 * @Desc: 损坏赔偿Vo
 */
public class CompensateVo implements Serializable {

    private static final long serialVersionUID = 4442963575988681935L;

    /** 物品编号*/
    private String goodsNo;

    /** 物品名称*/
    private String goodsName;

    /** 物品价格*/
    private BigDecimal goodsPrice;

    /** 赔偿人*/
    private String accountName;

    /** 赔偿方式：0=现金，1=物品*/
    private String compensateWay;

    /** 赔偿时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date compensateTime;

    private Long communityId;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompensateWay() {
        return compensateWay;
    }

    public void setCompensateWay(String compensateWay) {
        this.compensateWay = compensateWay;
    }

    public Date getCompensateTime() {
        return compensateTime;
    }

    public void setCompensateTime(Date compensateTime) {
        this.compensateTime = compensateTime;
    }
}
