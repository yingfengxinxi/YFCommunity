package com.wr.system.business.domain.icon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by 78787 on 2023/3/25 15:01
 **/
@Data
@ApiModel("H5首页热门应用及热门分类")
@Accessors(chain = true)
public class WstIconHome {

    @ApiModelProperty("热门分类")
    private List<XcIconType> typeList;


    @ApiModelProperty("热门服务")
    private List<XcWstService> serverList;

    public WstIconHome(List<XcIconType> typeList, List<XcWstService> serverList) {
        this.typeList = typeList;
        this.serverList = serverList;
    }
}
