package com.wr.api.owner.entity.home.rescue.vo;

import com.wr.api.owner.entity.home.rescue.ApiRescue;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022-11-15 10:47:34
 * @Desc: 求助vo类
 */
public class ApiRescueVo extends ApiRescue {

    private static final long serialVersionUID = 8400479238471324806L;
    /**
     * 根据时间判断当前求助信息的状态： 0未开始  1进行中 2已结束
     */
    private Long rescueStatus;

    /**
     * 当前救助者：志愿者
     */
    private List rescueUserList;

    /**
     * 发布人姓名
     */
    private String releaseName;

    /**
     *志愿者个数
     */
    private Long userNum;

    /**
     * 志愿者手机号
     */
    private String userPhone;




    /**
     *    0 未参与 未开始（救助大厅内的数据状态为0），  1 已参与（我的救助历史时，状态为1）
     */
    private String myRescue;



    public String getMyRescue() {
        return myRescue;
    }

    public void setMyRescue(String myRescue) {
        this.myRescue = myRescue;
    }



    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public Long getRescueStatus() {
        return rescueStatus;
    }

    public void setRescueStatus(Long rescueStatus) {
        this.rescueStatus = rescueStatus;
    }

    public List getRescueUserList() {
        return rescueUserList;
    }

    public void setRescueUserList(List rescueUserList) {
        this.rescueUserList = rescueUserList;
    }

    @Override
    public String toString() {
        return "RescueVo{" +
                "rescueStatus='" + rescueStatus + '\'' +
                ", rescueUserList=" + rescueUserList +
                ", releaseName='" + releaseName + '\'' +
                '}';
    }
}
