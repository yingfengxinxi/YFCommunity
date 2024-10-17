package com.wr.system.business.domain.screen;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LiuBei
 * @Date: 2022-01-07 19:05:26
 * @Desc:
 */
public class DeviceVo implements Serializable {
    private static final long serialVersionUID = -755843052863454609L;
    /**设备名称*/
    private String name;
    /**项目地址*/
    private String address;
    /**时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DeviceVo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
