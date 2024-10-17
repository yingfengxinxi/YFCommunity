package com.wr.remote.estate.property;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/17 13:18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("资产管理-出库记录表")
@TableName(value = "xccj_community.bus_property_record")
public class PropertyRecord extends BaseEntity {

    private String id;
    //姓名
    private String name;
    //资产类型0=固定资产1=消耗资产
    private String type;

    //手机号
    private String phone;

    //物品使用数量
    private Integer quantityUsed;
    //资产管理id
    private String propertyId;
    //备注
    private String remarks;
    //物品是否归还0=否1=是
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;

    @TableLogic
    private String delFlag;
}
