package com.wr.remote.estate.center.vo;

import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/1/28 18:40
 */
@Data
public class UnitInfoVo {

    private Long unitId;
    private Long communityId;
    private String communityName;
    private Long buildingId;
    private String buildingName;
    private String unitName;
}
