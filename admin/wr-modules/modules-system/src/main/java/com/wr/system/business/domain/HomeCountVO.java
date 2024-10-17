package com.wr.system.business.domain;

import lombok.Data;

/**
 * 首页总数
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 14:38:08
 * @Desc:
 */
@Data
public class HomeCountVO {

    /**
     * 物业总数
     */
    private Integer estateCount;

    /**
     * 人员总数
     */
    private Integer staffCount;

    /**
     * 外来人员总数
     */
    private Integer foreignStaffCount;
}
