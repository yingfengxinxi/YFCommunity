package com.wr.remote.estate.assess.vo;

import com.wr.remote.estate.assess.Assess;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 17:27:19
 * @Desc: 考核记录视图类
 */
public class AssessVO extends Assess {

    private static final long serialVersionUID = 7092720513117426646L;

    /**
     * 物业id，表xccj_community.bus_estate
     */
    private List<Long> estateIds;

    /**
     * 开始时间结束时间
     */
    private String[] startEndTime;

    /**
     * 考核物业数量
     */
    private Integer propertyCount;

    /**
     * 考核进度(未开始，进行中，已结束)
     */
    private String schedule;

    /**
     * 考核方法名称
     */
    private String methodName;

    public List<Long> getEstateIds() {
        return estateIds;
    }

    public void setEstateIds(List<Long> estateIds) {
        this.estateIds = estateIds;
    }

    public String[] getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String[] startEndTime) {
        this.startEndTime = startEndTime;
    }

    public Integer getPropertyCount() {
        return propertyCount;
    }

    public void setPropertyCount(Integer propertyCount) {
        this.propertyCount = propertyCount;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
