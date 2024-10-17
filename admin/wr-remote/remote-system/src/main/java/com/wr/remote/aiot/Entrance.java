package com.wr.remote.aiot;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 14:19:10
 * @Desc: 停车场出入口
 */
@Data
public class Entrance implements Serializable {
    private static final long serialVersionUID = -5010097354903470316L;

    /** 主键 */
    private Integer entranceId;

    /** 出入口唯一标识 */
    private String indexCode;

    /** 出入口名称 */
    private String entranceName;

    /** 停车场唯一标识 */
    private String parkIndexCode;

    /** 车道数 */
    private Integer roadNum;

    /** 创建时间 */
    private String createTime;

    /** 更新时间 */
    private String updateTime;

    /** 设备二维码 */
    private String deviceQr;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    /** 车道数据 */
    List<Roadway> roadwayList;
}
