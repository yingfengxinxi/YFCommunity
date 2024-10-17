package com.wr.system.business.domain.screen;

import lombok.Data;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/17
 * 人员vo
 */
@Data
public class StaffVo {
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 维修人员人数
     */
    private Integer wxStaffNumber;
    /**
     * 安防人员人数
     */
    private Integer afStaffNumber;
    /**
     * 保洁人员人数
     */
    private Integer wyglStaffNumber;
    /**
     * 物业人员占比
     */
    private Double wxryzb;
    /**
     * 安防人员占比
     */
    private Double afryzb;
    /**
     * 保洁人员占比
     */
    private Double wyglryzb;
    /**
     * 人数总和
     */
    private Integer sum;
}
