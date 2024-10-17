package com.wr.remote.govern.manage.vo;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-01 14:21:36
 * @Desc: 人员轨迹详情视图类
 */
public class UserDetailLogVO extends UserLogVO{

    /**
     * 当日进出记录
     */
    private List<UserLogVO> userLogVOs;


    public UserDetailLogVO() {
    }

    public List<UserLogVO> getUserLogVOs() {
        return userLogVOs;
    }

    public void setUserLogVOs(List<UserLogVO> userLogVOs) {
        this.userLogVOs = userLogVOs;
    }

}
