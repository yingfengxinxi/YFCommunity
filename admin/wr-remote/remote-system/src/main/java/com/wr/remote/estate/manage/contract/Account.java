package com.wr.remote.estate.manage.contract;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-13 09:45:36
 * @Desc: 账号类
 */
@Data
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1550478702834510577L;

    /**
     * 主键
     */
    private Long accountId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 账号（手机号）
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号类型：0=业主，1=租客，2=访客，3=访客（装修工）
     */
    private String accountType;
    /**
     * 用户名
     */
    private String accountName;
    /**
     * 用户名称
     */
    private String accountNick;
    /**
     * 数据来源：0=物业注册，1=手机注册
     */
    private String dataSource;

    /** 是否是志愿者：0=否，1=是 */
    private String volunteer;
    /**
     * 微信号
     */
    private String wechatId;
    /**
     * 微信唯一识别码
     */
    private String unionId;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

}
