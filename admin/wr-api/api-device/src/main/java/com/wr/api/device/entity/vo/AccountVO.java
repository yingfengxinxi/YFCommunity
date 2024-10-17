package com.wr.api.device.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-07 14:01:35
 * @Desc: 账号VO
 */
@Data
public class AccountVO implements Serializable {
    private static final long serialVersionUID = 5072857812589897848L;

    private Long accountId;

    private Long communityId;
    private String communityName;
    private Long buildingId;
    private String buildingName;
    private Long unitId;
    private String unitName;
    private Long roomId;
    private String roomName;

    /** 姓名 */
    private String userName;

    /** 证件号 */
    private String cardNo;

    /** 手机号 */
    private String userPhone;

    /** 用户类型： 1=业主，2=租客*/
    private String userType;
}
