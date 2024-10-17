package com.wr.remote.estate.property;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:00
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("资产管理类别")
@TableName(value = "xccj_community.bus_property_type")
public class PropertyType extends BaseEntity {

    private String id;
    //名称
    private String name;
    //类型0=固定1=消耗
    private String propertyType;
    //排序
    private Integer sort;
    //小区id
    private Long estateId;

    @TableLogic
    private String delFlag;
}
