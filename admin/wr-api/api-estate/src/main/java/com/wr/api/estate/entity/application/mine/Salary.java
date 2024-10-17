package com.wr.api.estate.entity.application.mine;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 员工薪酬对象 salary
 *
 * @author DIANWEI
 * @date 2022-11-16
 */
@Data
public class Salary extends BaseEntity {
    /**
     * 主键
     */
    private Long salaryId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 员工id，表bus_staff
     */

    private Long staffId;

    /**
     * 薪资月份（yyyy-MM）
     */

    private String yearMonth;

    /**
     * 满勤天数（天）
     */

    private Long fullDay;

    /**
     * 出勤天数（天）
     */

    private Long attendDay;

    /**
     * 满勤奖
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal fullAward;

    /**
     * 工龄奖
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ageAward;

    /**
     * 应发工资
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal grossSalary;

    /**
     * 社保费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal socialFee;
    /**
     * 迟到扣除
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal lateFee;
    /**
     * 个人所得税
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal taxFee;
    /**
     * 实发工资
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal actuallySalary;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
