package com.wr.estate.entity.vo;

/**
 * 下拉框对象
 * @Author: lvzy
 * @Date: 2022-10-12 14:38:56
 * @Desc: 下拉框对象
 */
public class DropDownBoxVo {

    /**
     * 隐藏的ID
     */
    private String value;

    /**
     * 显示的内容
     */
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
