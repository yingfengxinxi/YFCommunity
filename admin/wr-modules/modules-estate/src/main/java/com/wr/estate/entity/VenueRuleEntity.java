package com.wr.estate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueRule
 * @History:
 */
public class VenueRuleEntity extends BaseEntity {

    /** 主键 */
    private Long ruleId;

    /** 所属物业id */
    @Excel(name = "所属物业id，")
    private Long estateId;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private String startTime;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private String endTime;

    /** 余与下场间隔时间 */
    @Excel(name = "余与下场间隔时间")
    private Long gapHour;

    /** 是否生成工单：0=是，1=否 */
    @Excel(name = "是否生成工单：0=是，1=否")
    private String createOrder;

    /** 预约声明 */
    @Excel(name = "预约声明")
    private String statement;

    /** 使用规则 */
    @Excel(name = "使用规则")
    private String useRule;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    public void setRuleId(Long ruleId)
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId()
    {
        return ruleId;
    }
    public void setEstateId(Long estateId)
    {
        this.estateId = estateId;
    }

    public Long getEstateId()
    {
        return estateId;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getStartTime()
    {
        return startTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getEndTime()
    {
        return endTime;
    }
    public void setGapHour(Long gapHour)
    {
        this.gapHour = gapHour;
    }

    public Long getGapHour()
    {
        return gapHour;
    }
    public void setCreateOrder(String createOrder)
    {
        this.createOrder = createOrder;
    }

    public String getCreateOrder()
    {
        return createOrder;
    }
    public void setStatement(String statement)
    {
        this.statement = statement;
    }

    public String getStatement()
    {
        return statement;
    }
    public void setUseRule(String useRule)
    {
        this.useRule = useRule;
    }

    public String getUseRule()
    {
        return useRule;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

}
