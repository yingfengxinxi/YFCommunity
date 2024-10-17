package com.wr.system.business.domain.screen;

/**
 * @Author: Ma Zhong
 * @Date: 2021-08-12 11:18:23
 * @Desc: 年度维修设备统计
 */
public class AnnualMaintenanceEquipmentVo {
    /**
     * 月份
     */
    private String dateMonth;

    /**
     * 这个月de维修次数
     */
    private String num;

    public AnnualMaintenanceEquipmentVo(String dateMonth, String num) {
        this.dateMonth = dateMonth;
        this.num = num;
    }

    public AnnualMaintenanceEquipmentVo() {
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
