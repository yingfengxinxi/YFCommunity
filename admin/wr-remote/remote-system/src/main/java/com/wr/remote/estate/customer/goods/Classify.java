package com.wr.remote.estate.customer.goods;

import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 13:34:26
 * @Desc: 物品分类表实体类
 */
public class Classify extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -33191304624231592L;

    /** 主键*/
    private Long classifyId;

    /** 物业id，表bus_estate*/
    private Long estateId;

    /** 分类名称*/
    private String classifyName;

    /** 状态：0=启用，1=停用*/
    private String classifyStatus;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "classifyId=" + classifyId +
                ", estateId=" + estateId +
                ", classifyName='" + classifyName + '\'' +
                ", classifyStatus='" + classifyStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
