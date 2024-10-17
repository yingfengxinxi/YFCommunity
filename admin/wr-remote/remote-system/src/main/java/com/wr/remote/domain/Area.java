package com.wr.remote.domain;

import com.wr.common.core.business.annotation.ChildrenKey;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  区域管理 实体类
 * @author DIANWEI
 * @date 2022/9/21 14:47
 */
@Data
public class Area extends BaseEntity {
    /**
     * 地区ID
     */
    @PrimaryKey
    private Long areaId;
    /**
     * 父级ID
     */
    @ParentKey
    private Long parentId;
    /**
     * 祖籍列表
     */
    private String ancestors;
    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;

    /** 子菜单 */
    @ChildrenKey
    private List<Area> children = new ArrayList<>();
}
