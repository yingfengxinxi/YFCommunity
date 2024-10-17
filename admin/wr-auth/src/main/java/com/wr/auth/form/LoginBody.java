package com.wr.auth.form;

/**
 * 用户登录对象
 * 
 * @author wr
 */
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /** 用户类型：SYS_USER=通用端用户,ESTATE_USER=物业端用户,AI_IOT_USER=AIoT物联网端用户,GOVERN_USER=社区治理端用户 */
    private String userType;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
