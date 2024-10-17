package com.wr.api.estate.entity.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 15:42:16
 * @Desc: 物业缴费实体类
 */
@Data
public class PropertyLog implements Serializable {

    private static final long serialVersionUID = -3073875946660804894L;

    /**
     * 主键
     */
    private Long logId;

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
     * 住宅面积
     */
    private BigDecimal roomArea;

    /**
     * 收费类型：0=按住宅面积收费，1=统一收费
     */
    private String feeType;

    /**
     * 计费标准
     */
    private BigDecimal feeAmount;

    /**
     * 已缴费用
     */
    private BigDecimal payAmount;

    /**
     * 缴费适用年限
     */
    private Integer payYear;

    /**
     * 缴费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
