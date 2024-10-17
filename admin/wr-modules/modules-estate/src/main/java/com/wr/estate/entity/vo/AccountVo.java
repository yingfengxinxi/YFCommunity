package com.wr.estate.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/18 16:22
 */
@Data
public class AccountVo extends BaseEntity {

    private String loginName;
    private String accountNick;
    private String accountName;
    private String accountType;
    private String accountId;
    private String dataSource;
    private String avatar;
    private String volunteer;
    private String wechatId;
    private String cardNo;
    private String cardType;
    private String sex;
    private String isPropertyCouncil;
    private Long communityId;
    private Long roleId;
}
