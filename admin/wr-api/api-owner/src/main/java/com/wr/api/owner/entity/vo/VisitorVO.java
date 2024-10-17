package com.wr.api.owner.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 20:00:26
 * @Desc: 访客信息
 */
@Data
public class VisitorVO extends BaseEntity {
    private static final long serialVersionUID = 441895019810058341L;

    private Long visitorId;

    /** 小区id */
    private Long communityId;

    /** 账号id */
    private Long accountId;

    /** 访客姓名 */
    private String visitorName;

    /** 手机号 */
    private String visitorPhone;

    /** 证件类型：见数据字典 sys_certificate */
    private String cardType;

    /** 证件号码 */
    private String cardNo;

    /** 拜访次数 */
    private Integer visitorNum;

}

