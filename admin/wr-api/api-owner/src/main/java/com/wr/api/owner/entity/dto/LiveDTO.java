package com.wr.api.owner.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:40:46
 * @Desc: 生活缴费DTO
 */
@Data
public class LiveDTO implements Serializable {
    private static final long serialVersionUID = 3639729444922408739L;

    /** 物业id */
    private Long estateId;

    /** 小区id */
    private Long communityId;

    private Long buildingId;

    private Long unitId;

    /** 当前所住房间id */
    private Long roomId;
    //业主ID
    private Long ownerId;
    private Integer pageNum;
    private Integer pageSize;

}
