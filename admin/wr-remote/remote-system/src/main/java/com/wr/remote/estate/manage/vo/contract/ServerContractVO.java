package com.wr.remote.estate.manage.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.estate.manage.contract.ServerContract;

import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-17 16:13:29
 * @Desc: 服务合同视图类
 */
public class ServerContractVO extends ServerContract {

    /**
     * 甲方负责人（法人代表）
     */
    private String legal;

    /**
     * 甲方联系方式 （服务热线）
     */
    private String hotline;

    /**
     * 甲方物业
     */
    private String estateName;

    /**
     * 签约人名
     */
    private String nickName;

    /**
     * 签约人联系方式
     */
    private String phonenumber;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    public ServerContractVO() {
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
