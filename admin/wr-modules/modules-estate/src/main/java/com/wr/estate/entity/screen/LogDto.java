package com.wr.estate.entity.screen;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-12-10 11:51:51
 * @Desc:
 */
public class LogDto implements Serializable {

    private String tableName;


    private Date beginTime;

    private Date endTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
