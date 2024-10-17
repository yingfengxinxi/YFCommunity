package com.wr.api.estate.entity.vo.order;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-22 15:15:11
 * @Desc:
 */
@Data
public class OrderStaffVO {

    /**
     * 人员id
     */
    private Long staffId;

    /**
     * 人员姓名
     */
    private String staffName;

    /**
     * 人员手机号
     */
    private String staffPhone;

}
