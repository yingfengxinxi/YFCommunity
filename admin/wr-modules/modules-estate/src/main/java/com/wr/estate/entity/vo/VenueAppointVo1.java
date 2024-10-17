package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/28
 * @Description:
 * @FileName: VenueAppointVo1
 */
public class VenueAppointVo1 {
    private Long appointId;

    private Long venueId;
    private String userName;
    @JsonFormat(pattern = " HH:mm")
    private Date planStart;
    @JsonFormat(pattern = " HH:mm")
    private Date planEnd;

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Long getAppointId() {
        return appointId;
    }

    public void setAppointId(Long appointId) {
        this.appointId = appointId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(Date planEnd) {
        this.planEnd = planEnd;
    }
}
