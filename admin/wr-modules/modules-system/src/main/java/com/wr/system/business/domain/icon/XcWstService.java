package com.wr.system.business.domain.icon;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * icon服务对象 xc_wst_service
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Data
public class XcWstService extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务图片地址
     */
    private String url;

    /**
     * 跳转地址
     */
    private String goTo;

    /**
     * 跳转类型 0-h5链接 1-小程序 2-本系统内部页面
     */
    private Long goType;

    /**
     * 状态 1-启用 0-禁用
     */
    private Long status;

    /**
     * 是否热门分类，0-否，1-是
     */
    private Long isHot;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 所属分类
     */
    private Long classify;

    /**
     * 所属分类名称
     */
    private String classifyName;
}
