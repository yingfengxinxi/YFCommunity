package com.wr.remote.estate.property;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:00
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("资产管理")
@TableName(value = "xccj_community.bus_property")
public class Property extends BaseEntity {

    private String id;
    //物业id
    private Long estateId;
    //小区id
    private Long communityId;
    //资产类型0=固定资产1=消耗资产
    private String type;
    //物品编号
    private String no;
    //类型id资产类型xccj_community.bus_property_type
    private String assetType;
    //物品名称
    private String name;
    //型号规格
    private String specifications;
    //物品数量
    private Integer number;
    //单价
    private BigDecimal price;
    //厂商
    private String manufacturer;
    //简介
    private String briefIntroduction;
    //采购人
    private String purchaser;
    @TableLogic
    private String delFlag;
}
