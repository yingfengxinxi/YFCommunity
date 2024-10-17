package com.wr.remote.estate.center.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:37:31
 * @Desc:
 */
@Data
@ToString
public class RoomUpdateVO implements Serializable {

    private static final long serialVersionUID = -5850086717279105616L;
    /**
     * 房屋id
     */
    private Long roomId;

    /**
     * 小区id
     */
    private Long communityId;

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
    //宗教信仰
    private String religion;
    /**
     * 房屋坐落
     */
    private String houseAddress;

    /**
     * 坐在层
     */
    private String floor;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 图片地址
     */
    private String roomUrl;

    /**
     * 房间状态
     */
    private String roomStatus;

    /**
     * 交房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date deliveryTime;
    //房产证明
    private String licenseUrl;
    //业主id
    private Long ownerId;


    //业主信息集合
    private List<OwnerVo> ownerList;

    //车位/车辆信息
    private List<SpaceVehicleVo> spaceVehicleList;
}
