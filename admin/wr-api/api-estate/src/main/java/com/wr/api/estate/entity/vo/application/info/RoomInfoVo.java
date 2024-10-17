package com.wr.api.estate.entity.vo.application.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/23 17:37
 */
@Data
public class RoomInfoVo {

    //小区名称
    private String communityName;

    //房屋性质：1=安置房，2=商品房，3=公租房
    private String roomType;
    //楼栋
    private String buildingName;
    //单元楼
    private String unitName;
    //面积
    private String totalArea;
    //房号
    private String roomName;
    //楼层
    private String floor;
    //坐落地址
    private String houseAddress;
    private String selectRoom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;
}
