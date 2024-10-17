package com.wr.api.owner.entity.home.visitor;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-17 19:52:14
 * @Desc: 房屋
 */
public class ApiHouse implements Serializable {

    private static final long serialVersionUID = 1297961765275052714L;

    /**
     * 用于拼接业主完整地址
     */
    private String address;

    /**
     * 业主roomid
     */
    private Long roomId;

    /**
     * 社区id
     */
    private Long communityId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
