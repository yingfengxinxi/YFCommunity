package com.wr.api.owner.entity.home.visitor.vo;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-12-01 10:19:59
 * @Desc:
 */
@Data
public class RegisterVisitorVO {

    /**
     * 小区Id
     */
    private Long communityId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 密码
     */
    private String password;
    /**
     * 证件类型：见数据字典 sys_certificate
     */
    private String cardType;

}
