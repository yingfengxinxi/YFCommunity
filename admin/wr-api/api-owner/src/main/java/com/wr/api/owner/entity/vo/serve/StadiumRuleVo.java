package com.wr.api.owner.entity.vo.serve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuShu
 * @Date: 2022-12-08 15:18:21
 * @Desc: 场馆规则
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumRuleVo {
    /**
     * 主键
     */
    private Long ruleId;

    /**
     * 预约声明
     */
    private String statement;

    /**
     * 规则
     */
    private String useRule;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 与下场间隔时间
     */
    private Integer gapHour;

}
