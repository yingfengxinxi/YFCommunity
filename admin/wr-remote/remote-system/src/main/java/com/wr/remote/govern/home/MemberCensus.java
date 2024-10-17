package com.wr.remote.govern.home;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @Author: lusu
 * @Date: 2022-12-03 08:58:43
 * @Desc: 党员统计
 */

@Data
public class MemberCensus extends Census {
    private static final long serialVersionUID = -4523863263715694729L;
    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 党员人数
     */
    private BigDecimal memberNum;


}
