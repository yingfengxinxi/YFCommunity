package com.wr.api.owner.entity.home.icon;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * icon分类对象 xc_icon_type
 *
 * @author ruoyi
 * @date 2023-03-25
 */
@Data
public class XcIconType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 1-正常 0-禁用
     */
    @Excel(name = "1-正常 0-禁用")
    private Long status;

    /**
     * 1- 热门 0-普通
     */
    private Integer isHot;

    /**
     * 排序
     */
    private Integer sort;


    private String url;
}
