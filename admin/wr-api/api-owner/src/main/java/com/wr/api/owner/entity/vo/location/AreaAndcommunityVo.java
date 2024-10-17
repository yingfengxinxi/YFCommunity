package com.wr.api.owner.entity.vo.location;

import com.wr.api.owner.entity.home.location.Area;
import com.wr.common.core.business.annotation.ChildrenKey;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.remote.domain.Community;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:13:07
 * @Desc: 社区和区域的联合表
 */
@Data
public class AreaAndcommunityVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 地区ID
     */
    private Long areaId;
    /**
     * 地区名称
     */
    private String areaName;

    /** 子菜单 */
    private List<AreaAndcommunityVo> children;
}
