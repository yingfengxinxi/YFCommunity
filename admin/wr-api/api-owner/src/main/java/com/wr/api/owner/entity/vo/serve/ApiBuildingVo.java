package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * 楼栋关联
 * @Author: lvzy
 * @Date: 2022-11-14 17:24:05
 * @Desc: 楼栋关联
 */
@Data
public class ApiBuildingVo implements Serializable {

    private static final long serialVersionUID = 6046400622073532801L;

    /**
     * 小区ID
     */
    private Long communityId;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 单元ID
     */
    private Long unitId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 账户手机号
     */
    private Long accountId;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String ownerPhone;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 单元名称
     */
    private String unitName;

    /**
     * 楼栋名称
     */
    private String buildingName;


}
