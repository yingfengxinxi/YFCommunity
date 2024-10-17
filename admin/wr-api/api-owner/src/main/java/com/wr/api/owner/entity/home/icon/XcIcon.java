package com.wr.api.owner.entity.home.icon;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * icon设置对象 xc_icon
 *
 * @author ruoyi
 * @date 2022-12-09
 */
@Data
public class XcIcon extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * icon名称
     */
    @Excel(name = "icon名称")
    private String name;

    /**
     * icon地址
     */
    @Excel(name = "icon地址")
    private String url;

    /**
     * 跳转地址
     */
    @Excel(name = "跳转地址")
    private String goTo;

    /**
     * 跳转类型 0-h5链接 1-小程序 2-本系统内部页面
     */
    @Excel(name = "跳转链接类型（0.内部 2.H5 3.图片）")
    @ApiModelProperty(value = "跳转链接类型（0.内部 2.H5 3.图片）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goType;

    /**
     * 小程序appid go_type为1的时候使用
     */
    @Excel(name = "小程序appid go_type为1的时候使用")
    private String miniAppId;

    /**
     * 状态 1-启用 0-禁用
     */
    @Excel(name = "状态 1-启用 0-禁用")
    private Long status;

    /**
     * 小程序名称
     */
    @Excel(name = "小程序名称")
    private String miniAppName;

    /**
     * 是否热点0-否，1-是
     */
    @Excel(name = "是否热点0-否，1-是")
    private Long isHot;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 所属分类
     */
    @Excel(name = "所属分类")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer classify;


    /**
     * 新闻分类展示状态  仅goType=6时使用
     * 0-关闭  1-开启
     */
    private Integer viewStatus;

    /**
     * 主题编码
     */
    private String themeCode;

    /**
     * 第三方应用的appId
     */
    private String appId;


    /**
     * 置顶标识 0-不置顶 1-置顶
     */
    private Integer topStatus;

}
