package com.wr.remote.estate.center.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:37:31
 * @Desc:
 */
@Data
@ToString
public class RoomUpdateBatchVO {

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 房间号
     */
    private List<String> roomNumbers;

    /**
     * 楼栋id
     */
    private List<Long> buildingIds;

    /**
     * 单元ids
     */
    private List<Long> unitIds;

    /**
     * 房屋性质
     */
    private Integer roomType;

    /**
     * 房屋结构
     */
    private Integer structure;

    /**
     * 房间总面积
     */
    private Double totalArea;

    /**
     * 公摊面积
     */
    private Double shareArea;

    /**
     * 套内面积
     */
    private Double roomArea;

    /**
     * roomPattern
     */
    private String roomPattern;

    /**
     * 更新人
     */
    private String updateBy;

    private String roomUrl;
}
