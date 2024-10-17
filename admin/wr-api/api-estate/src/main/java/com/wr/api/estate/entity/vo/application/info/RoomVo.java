package com.wr.api.estate.entity.vo.application.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 09:53:06
 * @Desc:
 */
@Data
public class RoomVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4747849307930299245L;
    /**
     * 主键
     */
    private Long roomId;

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
     * 房号
     */
    private String roomName;
    /**
     * 房屋性质：1=安置房，2=商品房，3=公租房
     */
    private String roomType;
    /**
     * 房屋结构：1=框架结构,2=钢结构,3=钢筋混泥土结构,4=混合结构,5=砖木结构,6=其他结构
     */
    private String structure;
    /**
     * 房间总面积
     */
    private Double totalArea;
    /**
     * 套内面积
     */
    private Double roomArea;
    /**
     * 公摊面积
     */
    private Double shareArea;
    /**
     * 房间格局
     */
    private String roomPattern;
    /**
     * 户型图
     */
    private String roomUrl;
    /**
     * 0=未售，1=已售，2=入住，3=装修，4=已拿房，5=整租，6=合租，7=群租
     */
    private String roomStatus;
    /**
     * 产权人id，表bus_owner
     */
    private Long ownerId;
    /**
     * 交房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;
    /**
     * 居住人数
     */
    private Long liveNum;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * (0=未售，1=已售，2=入住，3=装修，4=已拿房，5=整租，6=合租，7=群租)总数
     */
    private Integer zeroCount;
    private Integer oneCount;
    private Integer twoCount;
    private Integer threeCount;
    private Integer fourCount;
    private Integer fiveCount;
    private Integer sixCount;
    private Integer sevenCount;

    /**
     * 楼栋id，表bus_building
     */
    private String buildingName;

    /**
     * 人员类型
     */
    private String personType;
}
