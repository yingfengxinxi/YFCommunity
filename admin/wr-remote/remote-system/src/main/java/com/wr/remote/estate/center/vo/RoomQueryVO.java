package com.wr.remote.estate.center.vo;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:37:31
 * @Desc:
 */
@Data
public class RoomQueryVO {

    /**
     * id
     */
    private Long id;

    /**
     * 户主姓名
     */
    private String ownerName;
    private String voteId;

    /**
     * 房屋状态
     */
    private String roomStatus;
    //装修状态
    private String renovationAuditStatus;

    /**
     * 层级查询，1级小区，2级楼栋，3级单元
     */
    private Integer level;


    private Integer flag;
}
