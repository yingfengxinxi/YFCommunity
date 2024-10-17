package com.wr.estate.entity.vo;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/28
 * @Description:
 * @FileName: VenueAppointVo
 * @History:
 */
public class VenueAppointVo {

    private Long venueId;

    private String venueName;

    private String venueStatus;

    private String address;

    List<VenueAppointVo1> user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueStatus() {
        return venueStatus;
    }

    public void setVenueStatus(String venueStatus) {
        this.venueStatus = venueStatus;
    }

    public List<VenueAppointVo1> getUser() {
        return user;
    }

    public void setUser(List<VenueAppointVo1> user) {
        this.user = user;
    }
}
