package com.wr.system.business.domain.screen;

/**
 * @Author: BaoYu
 * @Date: 2021-12-31 17:12:09
 * @Desc:
 */
public class UserVehicle {
    private String day;
    private int num;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "UserVehicle{" +
                "day='" + day + '\'' +
                ", num=" + num +
                '}';
    }
}
