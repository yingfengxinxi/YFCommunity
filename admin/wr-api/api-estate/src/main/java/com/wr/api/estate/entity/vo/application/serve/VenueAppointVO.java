package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.business.annotation.SensitiveWord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-25 10:27:20
 * @Desc:
 */
@Data
public class VenueAppointVO {

    private String userName;
    /**
     * 预约人手机号
     */
    private String userPhone;
    /**
     * 预约人身份证号
     */
    private String cardNo;
    /**
     * 活动类型
     */
    private String actName;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 场馆名称
     */
    private String venueName;
    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private String planStart;
    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private String planEnd;

    /**
     * 每小时费用
     */
    private BigDecimal hourFee;
    /**
     * 押金
     */
    private BigDecimal deposit;
}
