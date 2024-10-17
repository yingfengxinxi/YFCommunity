package com.wr.remote.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-08-22 16:34:39
 * @Desc: 字段属性实体
 */
public class TableColumn implements Serializable {
    private static final long serialVersionUID = 5239705616494873946L;
    /** 表名 */
    private String tableName;
    /** 主键对应的字段 */
    private String idField;
    /** 删除标记对应的字段 */
    private String delFlag;
    /** 要校验的字段 */
    private String filed;
    /** 要校验的字段值 */
    private String fieldValue;

    public TableColumn() {
    }

    public TableColumn(String tableName, String idField, String delFlag) {
        this.tableName = tableName;
        this.idField = idField;
        this.delFlag = delFlag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableName", tableName)
                .append("idField", idField)
                .append("delFlag", delFlag)
                .append("filed", filed)
                .append("fieldValue", fieldValue)
                .toString();
    }
}
