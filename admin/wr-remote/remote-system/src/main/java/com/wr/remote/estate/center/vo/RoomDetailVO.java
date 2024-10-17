package com.wr.remote.estate.center.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:37:31
 * @Desc:
 */
@Data
public class RoomDetailVO implements Serializable {

    private static final long serialVersionUID = 3710837195018767200L;
    /**
     * 房屋id
     */
    private Long roomId;

    /**
     * 单元id
     */
    private Long unitId;

    /**
     * 楼栋id
     */
    private Long buildingId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 项目名称
     */
    private String communityName;

    /**
     * 总层数
     */
    private Integer totalFloor;
    //宗教信仰
    private String religion;

    /**
     * 所在楼层
     */
    private Integer currentFloor;

    /**
     * 所属楼栋
     */
    private String buildingName;

    /**
     * 所属单元
     */
    private String unitName;

    /**
     * 房号
     */
    private String roomName;
    /**
     * 业主id
     */
    private String ownerId;

    /**
     * 房屋编号
     */
    private String roomNumber;
    //房屋属性
    private String roomAttribute;

    /**
     * 房屋状态
     */
    private Integer roomStatus;

    /**
     * 交房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;

    /**
     * 收房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date confirmTime;

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
     * 房屋格局
     */
    private String roomPattern;

    /**
     * 布局图片
     */
    private String roomUrl;

    /**
     * 证书图片
     */
    private String licenseUrl;
    //房屋坐落
    private String houseAddress;
    //所在层
    private String floor;

    //业主信息集合
    private List<OwnerVo> ownerList;

    //车位/车辆信息
    private List<SpaceVehicleVo> spaceVehicleList;

}
