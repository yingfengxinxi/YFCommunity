package com.wr.remote.estate.center.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:20:46
 * @Desc:
 */
@Data
public class RoomVO {

    /**
     * id
     */
    private Long id;

    private Long communityId;
    private Long buildingId;
    private Long unitId;
    private Long roomId;

    /**
     * 产权人id
     */
    private Long ownerId;

    /**
     * 所属楼栋
     */
    private String buildingName;

    /**
     * 单元
     */
    private String unitName;

    /**
     * 房号
     */
    private String roomName;
    //房屋装修进度
    private String renovationAuditStatus;
    //装修表id
    private String decorateId;

    /**
     * 房屋状态，0=未售，1=已售，2=入住，3=装修，4=已拿房，5=整租，6=合租，7=群租
     */
    private String roomStatus;

    /**
     * 总面积
     */
    private String totalArea;

    /**
     * 产权人姓名
     */
    private String ownerName;

    /**
     * 居住人数
     */
    private Integer liveNum;

    /**
     * 产权人手机号
     */
    private String ownerPhone;

    /**
     * 房屋属性，房屋性质：1=安置房，2=商品房，3=公租房
     */
    @Excel(name = "房屋属性", readConverterExp = "0=商品房,1=商铺,2=成套住宅,3=别墅", combo = "商品房,商铺,成套住宅,别墅", prompt = "必填")
    private String roomType;

    /**
     * 最后操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String delFlag;

}
