package com.wr.estate.entity.vo;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/7 11:19
 */
@Data
public class RoomImportVo {

    @Excel(name = "楼栋", prompt = "必填")
    private String buildingName;

    @Excel(name = "单元", prompt = "必填")
    private String unitName;


    @Excel(name = "房号", prompt = "必填")
    private String roomName;
    /**
     * 房屋属性，房屋性质：1=安置房，2=商品房，3=公租房
     */
    @Excel(name = "房屋属性", readConverterExp = "0=商品房,1=商铺,2=成套住宅,3=别墅", combo = "商品房,商铺,成套住宅,别墅", prompt = "必填")
    private String roomType;

    @Excel(name = "房屋面积", prompt = "必填")
    private Double totalArea;

    @Excel(name = "房屋坐落", prompt = "必填")
    private String houseAddress;
}
