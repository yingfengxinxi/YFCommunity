package com.wr.api.owner.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 20:15:34
 * @Desc: 小区切换
 */
@Data
public class ChangeDTO implements Serializable {
    private static final long serialVersionUID = -4930261431668106170L;

    /** 账号对应的人员id */
    private Long userId;

    /** 账号类型：0=业主，1=租客，2=访客，3=访客（装修工） */
    private String accountType;

    /** 地区id */
    private Long areaId;

}
