package com.wr.system.business.domain.screen;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: GuanYu
 * @Date: 2021-12-31 17:12:51
 * @Desc: 物业收费
 */
public class AmountFee implements Serializable {

    private static final long serialVersionUID = -8639740512998326450L;

    /** 收费总额*/
    private BigDecimal totalAmount;

    /** 停车收费总额*/
    private BigDecimal parkAmount;

    /** 比例*/
    private String proportion;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getParkAmount() {
        return parkAmount;
    }

    public void setParkAmount(BigDecimal parkAmount) {
        this.parkAmount = parkAmount;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    @Override
    public String toString() {
        return "AmountFee{" +
                "totalAmount=" + totalAmount +
                ", parkAmount=" + parkAmount +
                ", proportion='" + proportion + '\'' +
                '}';
    }
}
