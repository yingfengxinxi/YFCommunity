package com.wr.common.customize.model;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-05-24 10:49:59
 * @Desc: 
 */
public class ApiUser implements Serializable {
    private static final long serialVersionUID = 768788313972112861L;

    /**登录名*/
    private String loginName;
    /**登录密码*/
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "AppUser{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
