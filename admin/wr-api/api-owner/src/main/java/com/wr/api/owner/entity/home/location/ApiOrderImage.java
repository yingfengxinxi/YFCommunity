package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.io.Serializable;

/**
 * 工单图片表表
 * @Author: lvzy
 * @Date: 2022-12-01 17:26:42
 * @Desc:工单图片表表
 */
@Data
public class ApiOrderImage implements Serializable {

    /**
     * 主键
     */
    private Long imageId;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 图片类型：0=居家维修工单,1=园区报事申请,2灾害报事申请
     */
    private String imageType;

    /**
     * 主表id，根据图片类型对应表
     */
    private Long tableId;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}