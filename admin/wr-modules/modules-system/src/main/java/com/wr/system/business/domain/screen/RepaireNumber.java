package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @Author: LiuBei
 * @Date: 2022-01-14 14:33:51
 * @Desc:
 */
public class RepaireNumber implements Serializable {
    private static final long serialVersionUID = -6304166772692348243L;
    /**星期一*/
    private int monday;
    /**星期二*/
    private int tuesday;
    /**星期三*/
    private int wednesday;
    /**星期四*/
    private int thursday;
    /**星期五*/
    private int friday;
    /**星期六*/
    private int saturday;
    /**星期天*/
    private int sunday;
    /**本周总数*/
    private int countNumber;

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    @Override
    public String toString() {
        return "RepaireNumber{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", countNumber=" + countNumber +
                '}';
    }
}
