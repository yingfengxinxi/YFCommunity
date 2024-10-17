package com.wr.api.owner.entity.home.icon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * icon服务对象 xc_wst_service
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Data
public class XcWstServiceType {


    /**
     * 所属分类
     */
    @ApiModelProperty("所属分类")
    private String classifyName;

    @ApiModelProperty("分类Id")
    private String classifyId;

    @ApiModelProperty("应用")
    private List<XcWstService> list;

}
