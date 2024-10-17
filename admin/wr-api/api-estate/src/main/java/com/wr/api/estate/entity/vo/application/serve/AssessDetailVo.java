package com.wr.api.estate.entity.vo.application.serve;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-12-03 13:57:41
 * @Desc:
 */
@Data
public class AssessDetailVo {
    /**
     * 物业名称
     */
    private String estateName;
    /**
     * 业主投票
     */
    private BigDecimal ownerVote;
    /**
     * 员工投票
     */
    private BigDecimal staffVote;
    /**
     * 园区报事
     */
    private BigDecimal parkReport;
    /**
     * 居家维修
     */
    private BigDecimal maintenance;
    /**
     * 灾害报事
     */
    private BigDecimal disasterReport;
    /**
     * 巡检工单
     */
    private BigDecimal inspectionOrder;
    /**
     * 巡查工单
     */
    private BigDecimal patrolOrder;
    /**
     * 巡更工单
     */
    private BigDecimal nightOrder;
    /**
     * 设备维护
     */
    private BigDecimal equipment;
    /**
     * 线下考核
     */
    private BigDecimal assessment;
    /**
     * 综合得分
     */
    private BigDecimal score;
    /**
     * 考核排名
     */
    private Integer ranking;
    /**
     * 考核人员
     */
    private String person;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 是否是线下考核(0是1否)
     */
    private String isOffline;
    /**
     * 考核id
     */
    private Long assessId;
    /**
     * 线下考核满分
     */
    private BigDecimal totalScore;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
