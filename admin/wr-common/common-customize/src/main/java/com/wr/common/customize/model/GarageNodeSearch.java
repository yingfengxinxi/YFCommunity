package com.wr.common.customize.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 停车库节点信息查询参数
 * @author lx970
 * @date 2024/4/10 10:12
 */
@Data
@ApiModel("停车库节点信息查询参数")
public class GarageNodeSearch implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "社区ID",required = true)
    @NotNull(message = "社区ID不可为空")
    private Long communityId;
    @ApiModelProperty("父节点编号")
    private String parentIndexCode;
    @ApiModelProperty("父节点类型")
    private String parentResourceType;
    @ApiModelProperty("资源类型，类型之间为或的关系")
    private List<String> resourceTypes;
    @ApiModelProperty("节点名称,模糊匹配")
    private String name;
    @ApiModelProperty("查询表达式")
    private List<Map<String,Object>> expressions;
    @ApiModelProperty("排序字段,注意：排序字段必须是查询条件，否则返回参数错误")
    private String orderBy;
    @ApiModelProperty("降序升序,降序：desc 升序：asc")
    private String orderType;
    @ApiModelProperty("分页大小,0<pageSize≤1000")
    private Integer pageSize;
    @ApiModelProperty("当前页码,pageNo≥1")
    private Integer pageNo;
}
