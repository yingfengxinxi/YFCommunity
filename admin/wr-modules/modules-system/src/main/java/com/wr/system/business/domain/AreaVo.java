package com.wr.system.business.domain;

import com.wr.common.core.business.annotation.ChildrenKey;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 区域管理 Vo 实体类
 *
 * @author DIANWEI
 * @date 2022/9/21 15:05
 */
@Data
public class AreaVo extends BaseEntity {
    private static final long serialVersionUID = -5723455997680880132L;
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
     * 是否有小区
     */
    private String num;

    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
    /**
     * 子部门
     */
    @ChildrenKey
    private List<AreaVo> children = Lists.newArrayList();

    private String viewFlag;
}
