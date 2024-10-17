package com.wr.api.estate.entity.application.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-22 15:46:01
 * @Desc: 志愿者信息
 */
@Data
public class RescueUser implements Serializable {

    private static final long serialVersionUID = -560499281147042918L;

    /**
     * 人员姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;
}
