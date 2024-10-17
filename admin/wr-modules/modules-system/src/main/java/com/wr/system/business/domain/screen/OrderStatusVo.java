package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @Author: LiuBei
 * @Date: 2022-01-14 17:26:56
 * @Desc:
 */
public class OrderStatusVo implements Serializable {
    private static final long serialVersionUID = -8667754415257917968L;
    /**今日已处理*/
    private int dealCount;
    /**今日未处理*/
    private int notDealCount;
    /**总数据*/
    private int totalCount;

    public int getDealCount() {
        return dealCount;
    }

    public void setDealCount(int dealCount) {
        this.dealCount = dealCount;
    }

    public int getNotDealCount() {
        return notDealCount;
    }

    public void setNotDealCount(int notDealCount) {
        this.notDealCount = notDealCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "OrderStatusVo{" +
                "dealCount=" + dealCount +
                ", notDealCount=" + notDealCount +
                ", totalCount=" + totalCount +
                '}';
    }
}
