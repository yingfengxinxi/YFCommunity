package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 09:13:16
 * @Desc: 出租屋
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalVo {
    /** 主键*/
    private Long rentId;

    /** 小区id，表xccj_community.bus_community*/
    private Long communityId;

    /** 发布人账号id，表xccj_community.bus_account*/
    private Long accountId;
    //物业id
    private Long ownerId;

    private String accountName;

    /** 联系电话*/
    private String contactPhone;

    /** 联系人姓名*/
    private String contactName;

    /** 户型*/
    private String doorModel;

    /** 所在楼层*/
    private Integer rentFloor;

    /** 房间类型：0=次卧，1=主卧*/
    private String roomType;

    /** 朝向*/
    private String roomToward;

    /** 房间面积*/
    private BigDecimal roomArea;


    /** 出租类型：0=合租，1=整租*/
    private String rentType;

    /** 租金*/
    private BigDecimal rentPrice;

    /** 装修情况*/
    private String furnish;

    /** 房屋配置（多选，英文逗号拼接）：0=冰箱，1=电视，2=洗衣机，3=空调，4=宽带，5=沙发，6=床，7=暖气，8=衣柜，9=独立卫生间，10=独立阳台，11=智能门锁，12=桌椅，13=微波炉，14=抽油烟机，15=燃气灶*/
    private String roomConfig;

    /** 房屋介绍*/
    private String roomInfo;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 浏览次数*/
    private Integer readNum;

    private String address;

    private String roomImg;

    /** 状态：0=出租中，1=已出租，2=已下架*/
    private String rentStatus;

    private String roomName;

    private Long buildingId;

    private Long unitId;
    /**
    *所属楼栋
    */
    private String ownBuilding;
    /**
     *房屋属性
     */
    @Excel(name = "房屋属性", readConverterExp = "0=商品房,1=商铺,2=成套住宅,3=别墅", combo = "商品房,商铺,成套住宅,别墅", prompt = "必填")
    private String ownAttribute;
    /**
     *所属单元
     */
    private String ownUnit;
    /**
     *房号
     */
    private String roomNumber;
    /**
     *与户主关系
     */
    private String relationship;
    /**
     *房产证明
     */
    private String realEstate;
    /**
     *身份证-正
     */
    private String frontIdCard;
    /**
     *身份证-反
     */
    private String backIdCard;
    private String communityName;
    private String buildingName;
    private String unitName;


}
