package com.wr.remote.estate.property;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/17 13:18
 * 资产补货
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("资产管理-补货记录表")
@TableName(value = "xccj_community.bus_property_procurement_record")
public class PropertyProcurementRecord extends BaseEntity {

    private String id;
    //资产类型0=固定资产1=消耗资产
    private String type;
    //单价
    private BigDecimal price;
    //数量
    private Integer number;
    //资产管理id
    private String propertyId;
    //备注
    private String remarks;
    //厂商
    private String manufacturer;
    //采购人
    private String purchaser;

    @TableLogic
    private String delFlag;
}
