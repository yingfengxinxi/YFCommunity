package com.wr.api.owner.entity.vo.home;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:56:07
 * @Desc: 物业费规则
 */
@Data
public class PropertyFeeVO implements Serializable {
    private static final long serialVersionUID = -2966739629517196224L;

    /** 小区id */
    private Long communityId;

    /** 收费状态：0=启用，1=停用 */
    private String feeStatus;

    /** 收费类型：0=按住宅面积收费，1=统一收费 */
    private String feeType;

    /** 费用 */
    private BigDecimal feeAmount;
}
