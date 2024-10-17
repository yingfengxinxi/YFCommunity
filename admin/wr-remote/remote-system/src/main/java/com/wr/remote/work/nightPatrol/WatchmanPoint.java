package com.wr.remote.work.nightPatrol;

import com.wr.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 巡更工单-巡更点对象 bus_watchman_point
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public class WatchmanPoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 巡更工单id，表xccj_work.bus_watchman
     */
    private Long watchmanId;

    /**
     * 巡更点id，表xccj_work.bus_point
     */
    private Long pointId;

    /**
     * 巡更结果：0=待巡更，1=正常，2=异常
     */
    private String watchmanResult;

    /**
     * 结果说明
     */
    private String resultInfo;

    public void setWatchmanId(Long watchmanId) {
        this.watchmanId = watchmanId;
    }

    public Long getWatchmanId() {
        return watchmanId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setWatchmanResult(String watchmanResult) {
        this.watchmanResult = watchmanResult;
    }

    public String getWatchmanResult() {
        return watchmanResult;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("watchmanId", getWatchmanId())
                .append("pointId", getPointId())
                .append("watchmanResult", getWatchmanResult())
                .append("resultInfo", getResultInfo())
                .toString();
    }
}
