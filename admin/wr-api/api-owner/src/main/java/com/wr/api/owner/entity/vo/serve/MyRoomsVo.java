package com.wr.api.owner.entity.vo.serve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuShu
 * @Date: 2022-12-06 11:20:13
 * @Desc: 我得房产
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyRoomsVo {

    private Long accountId;
    /**
     * 主键
     */
    private Long decorateId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;
    /**
     * 单元id，表bus_unit
     */
    private Long unitId;
    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;
    /**
     * 业主id，表bus_owner
     */
    private Long ownerId;

    /**
     * 房间号
     */
    private String roomName;

    /**
     * 小区名
     */
    private String communityName;

    /**
     * 单元名
     */
    private String unitName;

    /**
     * 楼栋名
     */
    private String buildingName;

    private String roomStatus;

}
