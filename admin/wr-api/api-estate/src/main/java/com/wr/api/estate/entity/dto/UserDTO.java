package com.wr.api.estate.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 17:19:58
 * @Desc: 登录用户密码相关
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 3902756950716240344L;

    private String loginName;

    private String idCardNo;

    private String newPass;

    private String oldPass;
}
