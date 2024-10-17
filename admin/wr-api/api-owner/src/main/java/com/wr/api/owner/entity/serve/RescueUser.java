package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 14:35:34
 * @Desc: 社区救助-志愿者关联实体
 */
@Data
public class RescueUser implements Serializable {
    private static final long serialVersionUID = -475641396425006119L;

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

    @Override
    public String toString() {
        return "RescueUser{" +
                "rescueId=" + rescueId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
