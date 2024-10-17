package com.wr.api.owner.entity.myinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 晁盖
 * @Date: 2023-04-17 11:01:42
 * @Desc: 装修工信息
 */
@Data
public class WorkVo implements Serializable {

    private static final long serialVersionUID = 3439379494621757048L;

    /**
     * 装修工姓名
     */
    private String workerName;
    /**
     * 装修工手机号
     */
    private String workerPhone;
    /**
     * 装修工身份证号
     */
    private String cardNo;
    /**
     * 通行证路径
     */
    private String codeUrl;
}
