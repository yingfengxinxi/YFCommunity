package com.wr.api.owner.entity.home.rescue;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-15 10:16:54
 * @Desc:  社区救助-志愿者关联表
 */
public class ApiRescueUser implements Serializable {
    private static final long serialVersionUID = 926028802482074719L;


    /**
     * 社区救助id，表xccj_govern.bus_rescue
     */
    private Long rescueId;


    /**
     * 人员姓名
     */
    private String userName;


    /**
     * 手机号
     */
    private String userPhone;


    public Long getRescueId() {
        return rescueId;
    }

    public void setRescueId(Long rescueId) {
        this.rescueId = rescueId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "RescueUser{" +
                "rescueId=" + rescueId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
