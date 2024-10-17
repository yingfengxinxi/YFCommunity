package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @author: DIANWEI
 * @create: 2021/12/31
 * @Description:
 * @FileName: Visit
 * @History:
 * @自定义内容： 按月份查询每天访客次数
 */
public class MonthVisit implements Serializable {
    private static final long serialVersionUID = 1L;
    /*天*/
    private String day;
    /*人数*/
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
        return "MonthVisit{" +
                "day='" + day + '\'' +
                ", num=" + num +
                '}';
    }
}
