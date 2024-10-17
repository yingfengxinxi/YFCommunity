package com.wr.remote.estate.prevent;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("防控类型")
@TableName(value = "xccj_govern.bus_prevent_type")
public class BusPreventType extends BaseEntity {

    private String id;

    //类型名称
    private String name;
    //背景颜色
    private String backgroupColor;

    //顺序
    private int sort;
}
