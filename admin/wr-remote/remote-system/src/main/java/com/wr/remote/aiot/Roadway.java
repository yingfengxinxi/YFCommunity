package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:05:56
 * @Desc: 出入口车道
 */
@Data
public class Roadway implements Serializable {
    private static final long serialVersionUID = 6552719384269569267L;

    /** 主键 */
    private Integer roadwayId;

    /** 车道唯一标识 */
    private String indexCode;

    /** 车道名称 */
    private String roadwayName;

    /** 车道类型:0=入口,1=出口 */
    private Integer roadwayType;

    /** 出入口唯一标识 */
    private String entranceIndexCode;

    /** 创建时间 */
    private String createTime;

    /** 更新时间 */
    private String updateTime;

    /** 设备二维码 */
    private String deviceQr;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;
    /**
     * 车道下设备
     */
    List<Barrier> barrierList;

}
