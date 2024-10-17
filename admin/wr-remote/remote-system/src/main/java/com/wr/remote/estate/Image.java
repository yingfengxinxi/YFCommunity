package com.wr.remote.estate;

import java.io.Serializable;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 14:42:29
 * @Desc: 图片表实体类
 */
public class Image implements Serializable {

    private static final long serialVersionUID = -5608034490869239856L;

    /** 主键*/
    private Long imageId;

    /** 图片路径*/
    private String imageUrl;

    /** 数据类型：0=物品出门*/
    private String imageType;

    /** 对应表主键id*/
    private Long tableId;

    /** 删除标记：0=未删除，1=已删除*/
    private Integer delFlag;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
